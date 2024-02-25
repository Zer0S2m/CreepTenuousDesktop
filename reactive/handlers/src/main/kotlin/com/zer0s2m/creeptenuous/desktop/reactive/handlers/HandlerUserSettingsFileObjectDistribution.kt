package com.zer0s2m.creeptenuous.desktop.reactive.handlers

import com.zer0s2m.creeptenuous.desktop.common.dto.UserSettingsFileObjectDistribution
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveHandler
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveHandlerKtor
import com.zer0s2m.creeptenuous.desktop.core.state.SystemSettings
import io.ktor.client.call.*
import io.ktor.client.request.*

/**
 * Lazy handler for getting data about file object distribution settings
 */
object HandlerUserSettingsFileObjectDistribution :
    ReactiveHandler<UserSettingsFileObjectDistribution>,
    ReactiveHandlerKtor by HandlerReactiveUser {

    /**
     * Process reactive property
     *
     * @return result
     */
    override suspend fun handler(): UserSettingsFileObjectDistribution {
        return HttpClient.client.get("${SystemSettings.host}:${SystemSettings.port}/api/v1/user/profile") {
            header("Authorization", "Bearer ${SystemSettings.accessToken}")
        }.body()
    }

}
