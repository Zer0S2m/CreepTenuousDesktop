package com.zer0s2m.creeptenuous.desktop.reactive.handlers

import com.zer0s2m.creeptenuous.desktop.common.dto.IssuedRights
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import com.zer0s2m.creeptenuous.desktop.core.env.Environment
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveHandler
import io.ktor.client.call.*
import io.ktor.client.request.*

object HandlerReactiveUserAssignedRights : ReactiveHandler<IssuedRights> {

    /**
     * Process reactive property
     *
     * @return result
     */
    override suspend fun handler(): IssuedRights {
        val currentFileObject: String = ContextScreen.get(Screen.DASHBOARD_SCREEN, "currentFileObject")

        return if (Environment.IS_DEV) {
            val data: List<IssuedRights> = HttpClient.client.get {
                url("/api/v1/user/global/right/assigned")
                parameter("file", currentFileObject)
            }.body()
            data[0]
        } else {
            HttpClient.client.get {
                url("/api/v1/user/global/right/assigned")
                parameter("file", currentFileObject)
            }.body()
        }
    }

}
