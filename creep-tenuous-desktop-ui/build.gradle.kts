plugins {
    id("org.jetbrains.compose")
}

dependencies {
    implementation(compose.desktop.currentOs)

    implementation(project(":creep-tenuous-desktop-core"))
    implementation(project(":creep-tenuous-desktop-reactive:creep-tenuous-desktop-reactive-models"))

    implementation(project(":creep-tenuous-desktop-extended:creep-tenuous-desktop-navigation"))
    implementation(project(":creep-tenuous-desktop-extended:creep-tenuous-desktop-core-navigation"))
    implementation(project(":creep-tenuous-desktop-extended:creep-tenuous-desktop-core-utils"))
}
