package com.zer0s2m.creeptenuous.desktop.reactive.handlers

import com.zer0s2m.creeptenuous.desktop.common.dto.UserProfileSettings
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveHandler
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveHandlerKtor
import com.zer0s2m.creeptenuous.desktop.core.state.SystemSettings
import io.ktor.client.call.*
import io.ktor.client.request.*

/**
 * Reactive handler for getting data about user parameters
 */
object HandlerReactiveUserProfileSettings :
    ReactiveHandler<UserProfileSettings>,
    ReactiveHandlerKtor by HandlerReactiveUser {

    /**
     * Process reactive property
     *
     * @return result
     */
    override suspend fun handler(): UserProfileSettings {
        return HttpClient.client.get("${SystemSettings.host}:${SystemSettings.port}/api/v1/user/profile") {
            header("Authorization", "Bearer ${SystemSettings.accessToken}")
        }.body()
    }

}
