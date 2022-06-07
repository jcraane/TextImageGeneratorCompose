package dev.jamiecraane.generator.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import dev.jamiecraane.generator.core.TextNode
import dev.jamiecraane.generator.core.VerticalLayoutNode

@Composable
fun Text(value: String) {
    ComposeNode<TextNode, TextImageNodeApplier>(
        factory = { TextNode(value) },
        update = {}
    )
}

@Composable
fun VStack(children: @Composable () -> Unit) {
    ComposeNode<VerticalLayoutNode, TextImageNodeApplier>(
        factory = { VerticalLayoutNode() },
        update = {},
        content = children,
    )
}
