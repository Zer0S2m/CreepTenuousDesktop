package com.zer0s2m.creeptenuous.desktop.reactive.handlers

import com.zer0s2m.creeptenuous.desktop.common.data.DataFileSystemObjectInfo
import com.zer0s2m.creeptenuous.desktop.common.dto.InfoFileObject
import com.zer0s2m.creeptenuous.desktop.common.dto.InfoFileObjects
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveHandler
import com.zer0s2m.creeptenuous.desktop.core.state.SystemSettings
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

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
        val data: InfoFileObjects =
            HttpClient.client.post("${SystemSettings.host}:${SystemSettings.port}/api/v1/file-system-object/info") {
                header("Authorization", "Bearer ${SystemSettings.accessToken}")
                contentType(ContentType.Application.Json)
                setBody(DataFileSystemObjectInfo(listOf(currentFileObject)))
            }.body()

        data.objects.forEach {
            if (it.systemName == currentFileObject) {
                return it
            }
        }

        return null
    }

}