package dev.jamiecraane.generator

import dev.jamiecraane.generator.compose.Text
import dev.jamiecraane.generator.compose.TextImage
import dev.jamiecraane.generator.compose.VStack
import imageexporter.PngImageWriter
import java.io.File

fun main(args: Array<String>) {
    val output = TextImage {
        VStack {
            Text("Hello World")

            for (i in 0..3) {
                Text("Line $i")
            }
        }
    }

    val image = output.first.render()
    val imageWriter = PngImageWriter()
    imageWriter.writeImageToFile(image, File("sample.jpg"))
}
