plugins {
    kotlin("jvm")
    id("org.jetbrains.compose") version "1.4.1"
}

dependencies {
    implementation(compose.desktop.currentOs)

    implementation(project(":creep-tenuous-desktop-ui"))
    implementation(project(":creep-tenuous-desktop-core"))
}

group = "com.zer0s2m.creeptenuous.desktop.app"
version = "0.0.1-SNAPSHOT"
