package com.zer0s2m.creeptenuous.desktop.reactive.triggers

import com.zer0s2m.creeptenuous.desktop.common.data.DataControlFileSystemObject
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.logging.infoDev
import com.zer0s2m.creeptenuous.desktop.core.logging.logger
import com.zer0s2m.creeptenuous.desktop.core.state.SystemSettings
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveIndependentTrigger
import io.ktor.client.request.*
import io.ktor.http.*
import org.slf4j.Logger

/**
 * The trigger is called when a file object is set to a custom color.
 */
class ReactiveTriggerReactiveFileObjectSetColorInFileObject
    : ReactiveTriggerCommon(), BaseReactiveIndependentTrigger {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution.
     *
     * @param values Arbitrary number of arguments passed regardless of type
     */
    override suspend fun execution(vararg values: Any?) {
        val systemNameFileObject: String? = if (values[0] is String) values[0].toString() else null
        val colorId: Int? = if (values[1] is Int) values[1].toString().toInt() else null
        logger.infoDev(
            "Set color in file object - directory\n" +
                    "DATA: [systemNameFileObject:$systemNameFileObject, colorId:$colorId]"
        )

        if (colorId != null && systemNameFileObject != null) {
            setColorInDirectory(systemNameFileObject, colorId)
        } else if (colorId == null && systemNameFileObject != null) {
            HttpClient.client.delete {
                url("${SystemSettings.host}:${SystemSettings.port}/api/v1/user/customization/directory/color")
                header("Authorization", "Bearer ${SystemSettings.accessToken}")
                contentType(ContentType.Application.Json)
                setBody(DataControlFileSystemObject(systemNameFileObject))
            }
        }
    }

}
