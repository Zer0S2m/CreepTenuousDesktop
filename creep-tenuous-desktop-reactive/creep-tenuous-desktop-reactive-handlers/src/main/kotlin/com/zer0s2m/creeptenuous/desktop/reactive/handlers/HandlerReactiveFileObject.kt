package com.zer0s2m.creeptenuous.desktop.reactive.handlers

import com.zer0s2m.creeptenuous.desktop.common.dto.ManagerFileObject
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveHandler
import io.ktor.client.call.*
import io.ktor.client.request.get

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
        return HttpClient.client.get("/api/v1/directory").body()
    }

}