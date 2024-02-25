package com.zer0s2m.creeptenuous.desktop.reactive.triggers

import com.zer0s2m.creeptenuous.desktop.common.data.DataCreateFileSystemObjectDirectory
import com.zer0s2m.creeptenuous.desktop.common.dto.CreatedDirectory
import com.zer0s2m.creeptenuous.desktop.common.dto.FileObject
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.logging.infoDev
import com.zer0s2m.creeptenuous.desktop.core.logging.logger
import com.zer0s2m.creeptenuous.desktop.core.state.SystemSettings
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveIndependentTrigger
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.slf4j.Logger

/**
 * The trigger is called when a file object of type directory is created.
 */
class ReactiveTriggerReactiveFileObjectCreateDirectory
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
        val fileObject: FileObject? = if (values[0] is FileObject) values[0] as FileObject else null
        val colorId: Int? = if (values[1] is Int) values[1].toString().toInt() else null
        val categoryId: Int? = if (values[2] is Int) values[2].toString().toInt() else null
        val currentLevelManagerDirectory: Int = ContextScreen.get(
            Screen.DASHBOARD_SCREEN, "currentLevelManagerDirectory"
        )
        val currentParentsManagerDirectory: Collection<String> = ContextScreen.get(
            Screen.DASHBOARD_SCREEN, "currentParentsManagerDirectory"
        )
        val currentSystemParentsManagerDirectory: Collection<String> = ContextScreen.get(
            Screen.DASHBOARD_SCREEN, "currentSystemParentsManagerDirectory"
        )

        logger.infoDev(
            "Create file object of type - directory\n" +
                    "DATA: [" +
                    "fileObject:$fileObject, " +
                    "colorId:$colorId, " +
                    "categoryId:$categoryId, " +
                    "currentLevelManagerDirectory:$currentLevelManagerDirectory, " +
                    "currentParentsManagerDirectory:$currentParentsManagerDirectory, " +
                    "currentSystemParentsManagerDirectory:$currentSystemParentsManagerDirectory]"
        )

        if (fileObject != null) {
            val createdDirectory: CreatedDirectory = HttpClient.client.post {
                url("${SystemSettings.host}:${SystemSettings.port}/api/v1/directory/create")
                header("Authorization", "Bearer ${SystemSettings.accessToken}")
                contentType(ContentType.Application.Json)
                setBody(
                    DataCreateFileSystemObjectDirectory(
                        parents = currentParentsManagerDirectory,
                        systemParents = currentSystemParentsManagerDirectory,
                        directoryName = fileObject.realName,
                    )
                )
            }.body()

            if (colorId != null) {
                setColorInDirectory(createdDirectory.systemDirectoryName, colorId)
            }

            if (categoryId != null && categoryId != -1) {
                setCategoryInDirectory(createdDirectory.systemDirectoryName, categoryId)
            }
        }
    }

}
