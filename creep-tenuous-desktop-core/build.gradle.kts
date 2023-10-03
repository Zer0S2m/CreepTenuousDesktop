dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.0")

    implementation("io.ktor:ktor-client-core:${project.ext.get("ktorVersion")}")
    implementation("io.ktor:ktor-client-cio:${project.ext.get("ktorVersion")}")
    implementation("io.ktor:ktor-client-content-negotiation:${project.ext.get("ktorVersion")}")
    implementation("io.ktor:ktor-serialization-kotlinx-json:${project.ext.get("ktorVersion")}")
    implementation("io.ktor:ktor-client-logging:${project.ext.get("ktorVersion")}")

    implementation("org.slf4j:slf4j-api:2.0.7")
    implementation("org.slf4j:slf4j-simple:2.0.7")
    implementation("io.ktor:ktor-client-logging-jvm:2.3.2")
}
