package dev.jamiecraane.generator.core

import java.awt.image.BufferedImage

/**
 * Canvas to draw components on.
 */
class TextImageCanvas(
    private val width: Int,
    private val height: Int,
) {
    val image = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    val graphics = image.createGraphics()

    fun writeText(text: String, x: Int, y: Int) {
        graphics.drawString(text, x, y)
    }
}
