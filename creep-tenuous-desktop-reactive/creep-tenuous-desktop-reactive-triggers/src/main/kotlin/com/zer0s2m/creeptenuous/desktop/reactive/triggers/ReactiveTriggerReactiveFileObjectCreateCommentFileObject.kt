package com.zer0s2m.creeptenuous.desktop.reactive.triggers

import com.zer0s2m.creeptenuous.desktop.common.data.DataCreateCommentFileSystemObject
import com.zer0s2m.creeptenuous.desktop.common.dto.CommentFileObject
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.logging.infoDev
import com.zer0s2m.creeptenuous.desktop.core.logging.logger
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveTrigger
import io.ktor.client.request.*
import io.ktor.http.*
import org.slf4j.Logger

/**
 * A reactive trigger fires when a comment for a file object is created.
 */
class ReactiveTriggerReactiveFileObjectCreateCommentFileObject : BaseReactiveTrigger<CommentFileObject> {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution.
     *
     * @param value The new value of a property or object.
     */
    override suspend fun execution(value: CommentFileObject) {
        logger.infoDev("Create a comment for file object\nDATA: $value")

        HttpClient.client.post {
            url("/api/v1/common/comment/file-system-object")
            header("Authorization", "Bearer ${HttpClient.accessToken}")
            contentType(ContentType.Application.Json)
            setBody(DataCreateCommentFileSystemObject(value.comment, value.fileSystemObject))
        }
    }

}

