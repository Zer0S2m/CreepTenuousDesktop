package com.zer0s2m.creeptenuous.desktop.reactive.handlers

import com.zer0s2m.creeptenuous.desktop.common.dto.GrantedRight
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveHandler
import com.zer0s2m.creeptenuous.desktop.core.state.SystemSettings
import io.ktor.client.call.*
import io.ktor.client.request.*

/**
 * Reactive handler for getting data about granted rights
 */
object HandlerReactiveUserGrantedRights : ReactiveHandler<GrantedRight> {

    /**
     * Process reactive property
     *
     * @return result
     */
    override suspend fun handler(): GrantedRight {
        return HttpClient.client.get("${SystemSettings.host}:${SystemSettings.port}/api/v1/user/global/right/list-all") {
            header("Authorization", "Bearer ${SystemSettings.accessToken}")
        }.body()
    }

}
