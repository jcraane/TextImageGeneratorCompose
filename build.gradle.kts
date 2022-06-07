import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.compose.compose

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    gradlePluginPortal()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.runtime)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
