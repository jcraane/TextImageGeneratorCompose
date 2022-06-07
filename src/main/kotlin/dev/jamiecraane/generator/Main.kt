package dev.jamiecraane.generator

import dev.jamiecraane.generator.compose.Text
import dev.jamiecraane.generator.compose.TextImage
import dev.jamiecraane.generator.compose.VStack
import dev.jamiecraane.generator.core.TextNode
import imageexporter.JpegImageWriter
import imageexporter.PngImageWriter
import java.io.File

fun main(args: Array<String>) {
    val output = TextImage {
        VStack {
            for (i in 0..6) {
                Text("Hello $i")
            }
        }
    }

    val image = output.first.render()
    val imageWriter = PngImageWriter()
    imageWriter.writeImageToFile(image, File("sample.jpg"))
}
