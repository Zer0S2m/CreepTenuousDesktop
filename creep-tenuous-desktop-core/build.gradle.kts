dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.0")

    implementation("io.ktor:ktor-client-core:${project.ext.get("ktorVersion")}")
    implementation("io.ktor:ktor-client-cio:${project.ext.get("ktorVersion")}")
    implementation("io.ktor:ktor-client-content-negotiation:${project.ext.get("ktorVersion")}")
    implementation("io.ktor:ktor-serialization-kotlinx-json:${project.ext.get("ktorVersion")}")
    implementation("io.ktor:ktor-client-logging:${project.ext.get("ktorVersion")}")
    implementation("io.ktor:ktor-client-logging-jvm:${project.ext.get("ktorVersion")}")

    implementation("org.slf4j:slf4j-api:${project.ext.get("slf4jVersion")}")
    implementation("org.slf4j:slf4j-simple:${project.ext.get("slf4jVersion")}")
}
