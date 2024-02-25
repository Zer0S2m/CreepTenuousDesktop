dependencies {
    implementation(libs.kotlin.reflect)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.logging.jvm)
    implementation(libs.ktor.serialization.kotlinx.json)

    implementation(libs.slf4j.api)
    implementation(libs.slf4j.simple)
}
