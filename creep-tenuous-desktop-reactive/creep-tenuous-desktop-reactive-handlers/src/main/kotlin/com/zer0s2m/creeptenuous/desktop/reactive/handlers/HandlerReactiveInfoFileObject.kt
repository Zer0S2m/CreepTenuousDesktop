package com.zer0s2m.creeptenuous.desktop.reactive.handlers

import com.zer0s2m.creeptenuous.desktop.common.dto.InfoFileObject
import com.zer0s2m.creeptenuous.desktop.common.dto.InfoFileObjects
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveHandler
import io.ktor.client.call.*
import io.ktor.client.request.*

object HandlerReactiveInfoFileObject : ReactiveHandler<InfoFileObject?> {

    /**
     * Process reactive property
     *
     * @return result
     */
    override suspend fun handler(): InfoFileObject? {
        if (!ContextScreen.containsValueByKey(Screen.DASHBOARD_SCREEN, "currentFileObject")) {
            return null
        }

        val currentFileObject: String = ContextScreen.get(Screen.DASHBOARD_SCREEN, "currentFileObject")
        val data: InfoFileObjects = HttpClient.client.get("/api/v1/file-system-object/info").body()

        data.objects.forEach {
            if (it.systemName == currentFileObject) {
                return it
            }
        }

        return null
    }

}