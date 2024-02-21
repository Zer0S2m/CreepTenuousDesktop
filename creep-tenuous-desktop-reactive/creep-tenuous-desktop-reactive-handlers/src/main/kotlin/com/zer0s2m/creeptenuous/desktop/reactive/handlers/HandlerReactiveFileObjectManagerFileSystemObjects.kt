package com.zer0s2m.creeptenuous.desktop.reactive.handlers

import com.zer0s2m.creeptenuous.desktop.common.data.DataManagerDirectory
import com.zer0s2m.creeptenuous.desktop.common.dto.ManagerFileObject
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveHandler
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
        val currentLevelManagerDirectory: Int = ContextScreen.get(
            Screen.DASHBOARD_SCREEN, "currentLevelManagerDirectory")
        val currentParentsManagerDirectory: Collection<String> = ContextScreen.get(
            Screen.DASHBOARD_SCREEN, "currentParentsManagerDirectory")
        val currentSystemParentsManagerDirectory: Collection<String> = ContextScreen.get(
            Screen.DASHBOARD_SCREEN, "currentSystemParentsManagerDirectory")

        return HttpClient.client.post("/api/v1/directory") {
            header("Authorization", "Bearer ${HttpClient.accessToken}")
            contentType(ContentType.Application.Json)
            setBody(DataManagerDirectory(
                level = currentLevelManagerDirectory,
                parents = currentParentsManagerDirectory,
                systemParents = currentSystemParentsManagerDirectory
            ))
        }.body()
    }

}