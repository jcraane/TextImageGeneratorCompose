pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("jvm").version("1.6.10")
        id("org.jetbrains.compose").version("1.1.0")
    }
}

rootProject.name = "TextImageGeneratorCompose"

