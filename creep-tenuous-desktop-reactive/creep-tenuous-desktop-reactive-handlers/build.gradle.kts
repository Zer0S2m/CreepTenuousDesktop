dependencies {
    implementation("io.ktor:ktor-client-core:${project.ext.get("ktorVersion")}")

    implementation(project(":creep-tenuous-desktop-reactive:creep-tenuous-desktop-reactive-triggers"))
}
