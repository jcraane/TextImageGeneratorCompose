package dev.jamiecraane.generator.core

import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage

/**
 * Canvas to draw components on.
 */
class TextImageCanvas(
    width: Int,
    height: Int,
) {
    val image = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    val graphics: Graphics2D = image.createGraphics()

    fun writeText(text: String, x: Int, y: Int) {
        graphics.color = Color.BLACK
        graphics.drawString(text, x, y)
    }
}
