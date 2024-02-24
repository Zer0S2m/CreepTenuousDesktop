package com.zer0s2m.creeptenuous.desktop.reactive.handlers

import com.zer0s2m.creeptenuous.desktop.common.data.DataManagerDirectory
import com.zer0s2m.creeptenuous.desktop.common.dto.ManagerFileObject
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveHandler
import com.zer0s2m.creeptenuous.desktop.core.state.SystemSettings
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

/**
 * Reactive handler for getting information about file objects in different nesting spans
 */
object HandlerReactiveFileObjectManagerFileSystemObjects : ReactiveHandler<ManagerFileObject> {

    /**
     * Process reactive property
     *
     * @return result
     */
    override suspend fun handler(): ManagerFileObject {
        var currentLevelManagerDirectory: Int? = ContextScreen.get(
            Screen.DASHBOARD_SCREEN, "currentLevelManagerDirectory"
        )
        var currentParentsManagerDirectory: Collection<String>? = ContextScreen.get(
            Screen.DASHBOARD_SCREEN, "currentParentsManagerDirectory"
        )
        var currentSystemParentsManagerDirectory: Collection<String>? = ContextScreen.get(
            Screen.DASHBOARD_SCREEN, "currentSystemParentsManagerDirectory"
        )

        if (currentLevelManagerDirectory == null) {
            currentLevelManagerDirectory = 0
        }
        if (currentParentsManagerDirectory == null) {
            currentParentsManagerDirectory = mutableListOf()
        }
        if (currentSystemParentsManagerDirectory == null) {
            currentSystemParentsManagerDirectory = mutableListOf()
        }

        return HttpClient.client.post("${SystemSettings.host}:${SystemSettings.port}/api/v1/directory") {
            header("Authorization", "Bearer ${SystemSettings.accessToken}")
            contentType(ContentType.Application.Json)
            setBody(
                DataManagerDirectory(
                    level = currentLevelManagerDirectory,
                    parents = currentParentsManagerDirectory,
                    systemParents = currentSystemParentsManagerDirectory
                )
            )
        }.body()
    }

}