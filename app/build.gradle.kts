plugins {
    alias(libs.plugins.compose)
}

dependencies {
    implementation(compose.desktop.currentOs)

    implementation(project(":core"))
    implementation(project(":reactive:models"))
    implementation(project(":extended:navigation"))
    implementation(project(":ui:screens"))
}
