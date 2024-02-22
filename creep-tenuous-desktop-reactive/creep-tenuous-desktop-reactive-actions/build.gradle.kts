dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")

    implementation("io.ktor:ktor-client-core:${project.ext.get("ktorVersion")}")
    implementation("io.ktor:ktor-client-cio:${project.ext.get("ktorVersion")}")
}