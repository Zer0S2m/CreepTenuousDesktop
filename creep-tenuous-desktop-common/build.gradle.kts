plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "1.8.0"
    id("org.jetbrains.compose") version "1.4.1"
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation(compose.desktop.currentOs)
}

group = "com.zer0s2m.creeptenuous.desktop.common"
version = "0.0.1-SNAPSHOT"
