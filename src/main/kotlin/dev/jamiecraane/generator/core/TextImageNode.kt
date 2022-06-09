package dev.jamiecraane.generator.core

import java.awt.Font
import java.awt.Graphics2D
import java.awt.RenderingHints
import kotlin.math.max

sealed class TextImageNode {
    var width = 0
    var height = 0

    var x = 0
    var y = 0

    protected var graphics2D: Graphics2D = TextImageCanvas(1, 1).graphics

    abstract fun measure()

    abstract fun layout()

    abstract fun renderTo(canvas: TextImageCanvas)

    fun render(): TextImageCanvas {
        measure()
        layout()
        val canvas = TextImageCanvas(width, height)
        this.graphics2D = canvas.graphics // We need graphics to measure fonts in the measure functions
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        renderTo(canvas)
        return canvas
    }
}

/**
 * Represents text to be rendered in text image.
 */
class TextNode(initialValue: String = "") : TextImageNode() {
    // Fixed font for now
    private val font = Font("SansSerif", Font.PLAIN, 12)

    private var sizeInvalidated = true
    var value: String = initialValue
        set(value) {
            field = value
            sizeInvalidated = true
        }

    override fun measure() {
        if (sizeInvalidated) {
            val fontMetrics = graphics2D.getFontMetrics(font)
            width = fontMetrics.stringWidth(value)
            height = fontMetrics.ascent - fontMetrics.descent
            sizeInvalidated = false
        }
    }

    override fun layout() {
        val fontMetrics = graphics2D.getFontMetrics(font)
        y = y + fontMetrics.ascent - fontMetrics.descent
    }

    override fun renderTo(canvas: TextImageCanvas) {
        canvas.writeText(value, x, y)
    }
}

/**
 * Enables to stack nodes vertically.
 */
class VerticalLayoutNode() : TextImageNode() {
    val children = mutableListOf<TextImageNode>()

    override fun measure() {
        var width = 0
        var height = 0

        children.forEach {
            it.measure()
            width = max(width, it.width)
            height += it.height
        }

        this.width = width
        this.height = height
    }

    override fun layout() {
        var childY = 0
        children.forEach {
            it.x = 0
            it.y = childY
            it.layout()
            childY += it.height
        }
    }

    override fun renderTo(canvas: TextImageCanvas) {
        children.forEach {
            it.renderTo(canvas)
        }
    }
}
