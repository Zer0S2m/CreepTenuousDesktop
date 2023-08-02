plugins {
    kotlin("jvm")
    id("org.jetbrains.compose") version "1.4.1"
}

dependencies {
    implementation(compose.desktop.currentOs)

    implementation(project(":creep-tenuous-desktop-common"))
    implementation(project(":creep-tenuous-desktop-core"))
}

group = "com.zer0s2m.creeptenuous.desktop.ui"
version = "0.0.1-SNAPSHOT"
