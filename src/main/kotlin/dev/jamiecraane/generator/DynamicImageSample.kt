package dev.jamiecraane.generator

import androidx.compose.runtime.*
import dev.jamiecraane.generator.compose.Text
import dev.jamiecraane.generator.compose.VStack
import dev.jamiecraane.generator.compose.runImageGenerator
import kotlinx.coroutines.delay

fun main(args: Array<String>) {
    runImageGenerator {
        var count by mutableStateOf(0)

        setContent {
            VStack {
                Text("Hello World $count")
            }
        }

        for (i in 0..10) {
            delay(5000)
            count++
        }
    }
}
