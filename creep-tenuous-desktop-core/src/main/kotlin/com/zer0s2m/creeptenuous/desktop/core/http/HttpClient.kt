package com.zer0s2m.creeptenuous.desktop.core.http

import com.zer0s2m.creeptenuous.desktop.core.env.Environment
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

/**
 * Basic settings for http client
 */
object HttpClient {

    var accessToken: String = ""

    var refreshToken: String = ""

    val client: HttpClient = HttpClient(CIO) {
        if (Environment.IS_DEV) install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }

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
            url("http://localhost:8080")
        }
        developmentMode = true
    }

}