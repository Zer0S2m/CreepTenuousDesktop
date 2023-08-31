plugins {
    id("org.jetbrains.compose")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(project(":creep-tenuous-desktop-extended:creep-tenuous-desktop-navigation"))
}
