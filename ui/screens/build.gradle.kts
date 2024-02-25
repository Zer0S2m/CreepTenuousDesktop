plugins {
    alias(libs.plugins.compose)
}

dependencies {
    implementation(compose.desktop.currentOs)

    implementation(project(":core"))
    implementation(project(":extended:core-utils"))
    implementation(project(":ui:components"))

    implementation(project(":reactive:actions"))
    implementation(project(":reactive:models"))
    implementation(project(":extended:navigation"))
    implementation(project(":extended:core-navigation"))
    implementation(project(":extended:core-utils"))
}
