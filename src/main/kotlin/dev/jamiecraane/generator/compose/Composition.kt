package dev.jamiecraane.generator.compose

import androidx.compose.runtime.BroadcastFrameClock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.snapshots.Snapshot
import dev.jamiecraane.generator.core.TextImageNode
import dev.jamiecraane.generator.core.VerticalLayoutNode
import imageexporter.PngImageWriter
import kotlinx.coroutines.*
import java.io.File

fun StaticTextImage(
    content: @Composable TextImageNode.() -> Unit
): Pair<TextImageNode, Composition> {
    val rootNode = VerticalLayoutNode()
    val recomposer = Recomposer(Dispatchers.Main)
    val applier = TextImageNodeApplier(rootNode)
    val composition = Composition(applier, recomposer)

    composition.setContent {
        content(rootNode)
    }

    return applier.root as TextImageNode to composition
}

interface TextImageScope : CoroutineScope {
    fun setContent(content: @Composable () -> Unit)
}

fun runImageGenerator(
    body: suspend TextImageScope.() -> Unit
) = runBlocking {
    // Code copied from: https://github.com/JakeWharton/mosaic
    var hasFrameWaiters = false
    val clock = BroadcastFrameClock {
        hasFrameWaiters = true
    }

    val job = Job(coroutineContext[Job])
    val composeContext = coroutineContext + clock + job

    val rootNode = VerticalLayoutNode()
    val recomposer = Recomposer(composeContext)
    val composition = Composition(TextImageNodeApplier(rootNode), recomposer)

// Start undispatched to ensure we can use suspending things inside the content.
    launch(start = CoroutineStart.UNDISPATCHED, context = composeContext) {
        recomposer.runRecomposeAndApplyChanges()
    }

    var displaySignal: CompletableDeferred<Unit>? = null
    launch(context = composeContext) {
        while (true) {
            if (hasFrameWaiters) {
                hasFrameWaiters = false
                clock.sendFrame(0L) // Frame time value is not used by Compose runtime.

                val image = rootNode.render()
                val imageWriter = PngImageWriter()
                imageWriter.writeImageToFile(image, File("output-image.jpg"))
                displaySignal?.complete(Unit)
            }
            delay(50)
        }
    }

    coroutineScope {
        val scope = object : TextImageScope, CoroutineScope by this {
            override fun setContent(content: @Composable () -> Unit) {
                composition.setContent(content)
                hasFrameWaiters = true
            }
        }

        var snapshotNotificationsPending = false
        val observer: (state: Any) -> Unit = {
            if (!snapshotNotificationsPending) {
                snapshotNotificationsPending = true
                launch {
                    snapshotNotificationsPending = false
                    Snapshot.sendApplyNotifications()
                }
            }
        }
        val snapshotObserverHandle = Snapshot.registerGlobalWriteObserver(observer)
        try {
            scope.body()
        } finally {
            snapshotObserverHandle.dispose()
        }
    }

    yield()
    yield()
    Snapshot.sendApplyNotifications()
    yield()
    yield()

    if (hasFrameWaiters) {
        CompletableDeferred<Unit>().also {
            displaySignal = it
            it.await()
        }
    }

    job.cancel()
    composition.dispose()
}

