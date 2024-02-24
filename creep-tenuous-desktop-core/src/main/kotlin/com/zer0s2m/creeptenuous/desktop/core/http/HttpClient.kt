package com.zer0s2m.creeptenuous.desktop.core.http

import com.zer0s2m.creeptenuous.desktop.core.env.Environment
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import java.net.ConnectException

/**
 * Basic settings for http client
 */
object HttpClient {

    val client: HttpClient = HttpClient(CIO) {
        expectSuccess = false

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

        install(HttpTimeout) {
            requestTimeoutMillis = 3600_000
        }

        developmentMode = true
    }

    /**
     * Check the system for availability.
     *
     * @param host The specified system host.
     * @param port The specified system port.
     */
    suspend fun checkSystemSettings(host: String, port: Int): Boolean {
        return try {
            // TODO: make an API method - check the health of the main system
            client.get("$host:$port/api/v1/user/profile")
            true
        } catch (e: ConnectException) {
            false
        }
    }

}