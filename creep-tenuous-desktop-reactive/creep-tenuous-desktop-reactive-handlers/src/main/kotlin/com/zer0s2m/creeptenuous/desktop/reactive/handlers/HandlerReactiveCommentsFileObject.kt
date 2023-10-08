package com.zer0s2m.creeptenuous.desktop.reactive.handlers

import com.zer0s2m.creeptenuous.desktop.common.dto.CommentFileObject
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveHandler
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveMutableList
import com.zer0s2m.creeptenuous.desktop.core.reactive.toReactiveMutableList
import io.ktor.client.call.*
import io.ktor.client.request.*

/**
 * Reactive handler for loading a comment on a file object.
 */
object HandlerReactiveCommentsFileObject : ReactiveHandler<ReactiveMutableList<CommentFileObject>> {

    /**
     * Process reactive property
     *
     * @return result
     */
    override suspend fun handler(): ReactiveMutableList<CommentFileObject> {
        val currentFileObject: String = ContextScreen.get(
            Screen.DASHBOARD_SCREEN,
            "currentFileObject"
        )

        val data: MutableCollection<CommentFileObject> = HttpClient.client.get {
            url("/api/v1/common/comment/file-system-object")
            parameter("file", currentFileObject)
        }.body()

        return data.toReactiveMutableList()
    }

}
