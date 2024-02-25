package com.zer0s2m.creeptenuous.desktop.reactive.triggers

import com.zer0s2m.creeptenuous.desktop.common.data.DataRenameFileObject
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.logging.infoDev
import com.zer0s2m.creeptenuous.desktop.core.logging.logger
import com.zer0s2m.creeptenuous.desktop.core.state.SystemSettings
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveIndependentTrigger
import io.ktor.client.request.*
import io.ktor.http.*
import org.slf4j.Logger

/**
 * The trigger fires when the name of the file object changes.
 */
class ReactiveTriggerReactiveFileObjectRenameFileObject : BaseReactiveIndependentTrigger {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution.
     *
     * @param values Arbitrary number of arguments passed regardless of type
     */
    override suspend fun execution(vararg values: Any?) {
        val systemName: String? = if (values[0] is String) values[0].toString() else null
        val newTitle: String? = if (values[1] is String) values[1].toString() else null
        logger.infoDev("Rename file object\nDATA: [$systemName, $newTitle]")

        HttpClient.client.put {
            url("${SystemSettings.host}:${SystemSettings.port}/api/v1/file-system-object/rename")
            header("Authorization", "Bearer ${SystemSettings.accessToken}")
            contentType(ContentType.Application.Json)
            setBody(DataRenameFileObject(systemName!!, newTitle!!))
        }
    }

}
