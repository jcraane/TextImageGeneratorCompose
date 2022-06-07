package dev.jamiecraane.generator.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.Recomposer
import dev.jamiecraane.generator.core.TextImageNode
import dev.jamiecraane.generator.core.VerticalLayoutNode
import kotlinx.coroutines.Dispatchers

val rootNode = VerticalLayoutNode()
val recomposer = Recomposer(Dispatchers.Main)
val applier = TextImageNodeApplier(rootNode)
val composition = Composition(applier, recomposer)

fun TextImage(
    content: @Composable TextImageNode.() -> Unit
): Pair<TextImageNode, Composition> {
    composition.setContent {
        content(rootNode)
    }

    return applier.root as TextImageNode to composition
}
