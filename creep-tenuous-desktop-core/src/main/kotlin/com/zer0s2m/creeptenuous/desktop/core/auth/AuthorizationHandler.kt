package com.zer0s2m.creeptenuous.desktop.core.auth

import com.zer0s2m.creeptenuous.desktop.common.data.DataLoginUser
import com.zer0s2m.creeptenuous.desktop.common.dto.JwtTokens
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.state.SystemSettings
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

object AuthorizationHandler {

    /**
     * Authorize a user in the system.
     *
     * @param login User login.
     * @param password User password.
     */
    suspend fun login(login: String, password: String): JwtTokens {
        val response = HttpClient.client.post(
            "${SystemSettings.host}:${SystemSettings.port}/api/v1/auth/login"
        ) {
            contentType(ContentType.Application.Json)
            setBody(DataLoginUser(login, password))
        }

        return response.body()
    }

}