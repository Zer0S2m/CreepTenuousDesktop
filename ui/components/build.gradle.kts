plugins {
    id("org.jetbrains.compose")
}

dependencies {
    implementation(
        compose.desktop.currentOs
    )

    implementation(project(":core"))
    implementation(project(":extended:core-utils"))

    implementation(project(":reactive:models"))
}
