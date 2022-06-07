package dev.jamiecraane.generator.compose

import androidx.compose.runtime.AbstractApplier
import dev.jamiecraane.generator.core.TextImageNode
import dev.jamiecraane.generator.core.VerticalLayoutNode

class TextImageNodeApplier(root: VerticalLayoutNode): AbstractApplier<TextImageNode>(root) {
    override fun insertBottomUp(index: Int, instance: TextImageNode) {
        val verticalLayoutNode = current as VerticalLayoutNode
        verticalLayoutNode.children.add(index, instance)
    }

    override fun insertTopDown(index: Int, instance: TextImageNode) {
        // Ignored
    }

    override fun move(from: Int, to: Int, count: Int) {
        val verticalLayoutNode = current as VerticalLayoutNode
        verticalLayoutNode.children.move(from, to, count)
    }

    override fun onClear() {
        val verticalLayoutNode = current as VerticalLayoutNode
        verticalLayoutNode.children.clear()
    }

    override fun remove(index: Int, count: Int) {
        val vStack = current as VerticalLayoutNode
        vStack.children.remove(index, count)
    }
}
