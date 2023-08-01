package core.http

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

/**
 * Basic settings for http client
 */
object HttpClient {

    val client: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                json = Json {
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                    isLenient = true
                    allowSpecialFloatingPointValues = true
                    allowStructuredMapKeys = true
                    prettyPrint = false
                    useArrayPolymorphism = false
                }
            )
        }
        defaultRequest {
            url("http://localhost:3000")
        }
        developmentMode = true
    }

}