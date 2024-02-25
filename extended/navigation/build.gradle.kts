plugins {
    alias(libs.plugins.compose)
}

dependencies {
    implementation(compose.desktop.currentOs)
}
