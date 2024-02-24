package com.zer0s2m.creeptenuous.desktop.reactive.triggers

import com.zer0s2m.creeptenuous.desktop.common.data.DataDeleteFileSystemObjectDirectory
import com.zer0s2m.creeptenuous.desktop.common.data.DataDeleteFileSystemObjectFile
import com.zer0s2m.creeptenuous.desktop.common.dto.ManagerFileObject
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.logging.infoDev
import com.zer0s2m.creeptenuous.desktop.core.logging.logger
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveTrigger
import io.ktor.client.request.*
import io.ktor.http.*
import org.slf4j.Logger

/**
 * Trigger fires when a file object is deleted.
 */
class ReactiveTriggerReactiveFileObjectDeleteFileObject : BaseReactiveTrigger<ManagerFileObject> {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution. Delete a file object.
     *
     * @param oldValue The old value of a property or object.
     * @param newValue The new value of a property or object.
     */
    override suspend fun execution(oldValue: ManagerFileObject, newValue: ManagerFileObject) {
        val deletedFileObject = oldValue.objects.minus(newValue.objects.toSet())
        val currentParentsManagerDirectory: Collection<String> = ContextScreen.get(
            Screen.DASHBOARD_SCREEN, "currentParentsManagerDirectory"
        )
        val currentSystemParentsManagerDirectory: Collection<String> = ContextScreen.get(
            Screen.DASHBOARD_SCREEN, "currentSystemParentsManagerDirectory"
        )

        if (deletedFileObject[0].isFile) {
            logger.infoDev("Delete a file\nDATA: ${deletedFileObject[0]}")

            HttpClient.client.delete {
                url("/api/v1/file/delete")
                header("Authorization", "Bearer ${HttpClient.accessToken}")
                contentType(ContentType.Application.Json)
                setBody(
                    DataDeleteFileSystemObjectFile(
                        parents = currentParentsManagerDirectory,
                        systemParents = currentSystemParentsManagerDirectory,
                        fileName = deletedFileObject[0].realName,
                        systemFileName = deletedFileObject[0].systemName,
                    )
                )
            }
        } else {
            logger.infoDev("Delete a directory\nDATA: ${deletedFileObject[0]}")

            HttpClient.client.delete {
                url("/api/v1/directory/delete")
                header("Authorization", "Bearer ${HttpClient.accessToken}")
                contentType(ContentType.Application.Json)
                setBody(
                    DataDeleteFileSystemObjectDirectory(
                        parents = currentParentsManagerDirectory,
                        systemParents = currentSystemParentsManagerDirectory,
                        directoryName = deletedFileObject[0].realName,
                        systemDirectoryName = deletedFileObject[0].systemName,
                    )
                )
            }
        }
    }

}
