package com.zer0s2m.creeptenuous.desktop.reactive.actions

import com.zer0s2m.creeptenuous.desktop.common.data.DataDownloadDirectory
import com.zer0s2m.creeptenuous.desktop.common.data.DataDownloadFile
import com.zer0s2m.creeptenuous.desktop.common.enums.Config
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.core.actions.Action
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import com.zer0s2m.creeptenuous.desktop.core.env.Environment
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File
import java.nio.file.Path

object ActionDownloadFileOrDirectory : Action {

    /**
     * Call action.
     *
     * @param scope Defines a scope for new coroutines.
     * @param values Additional data.
     */
    override fun call(scope: CoroutineScope, vararg values: Any?) {
        val realName: String = (if (values[0] is String) values[0].toString() else null) ?: return
        val systemName: String = (if (values[1] is String) values[1].toString() else null) ?: return
        val isFile: Boolean = (if (values[2] is Boolean) values[2] as Boolean else null) ?: return
        val isDirectory: Boolean = (if (values[3] is Boolean) values[3] as Boolean else null) ?: return

        val currentParentsManagerDirectory: MutableList<String> = ContextScreen.get(
            Screen.DASHBOARD_SCREEN, "currentParentsManagerDirectory"
        )
        val currentSystemParentsManagerDirectory: MutableList<String> = ContextScreen.get(
            Screen.DASHBOARD_SCREEN, "currentSystemParentsManagerDirectory"
        )

        scope.launch {
            val url: String
            var request: HttpStatement? = null
            val file: File = Path.of(Config.PATH_DIRECTORY_DOWNLOAD_MAIN.path, realName)
                .toFile()

            if (isFile && !isDirectory) {
                url = "/api/v1/file/download"
                request = HttpClient.client.preparePost(url) {
                    header("Authorization", "Bearer ${HttpClient.accessToken}")
                    contentType(ContentType.Application.Json)
                    setBody(
                        DataDownloadFile(
                            parents = currentParentsManagerDirectory,
                            systemParents = currentSystemParentsManagerDirectory,
                            fileName = realName,
                            systemFileName = systemName
                        )
                    )
                }
            } else if (!isFile && isDirectory) {
                url = "/api/v1/directory/download"
                request = HttpClient.client.preparePost(url) {
                    header("Authorization", "Bearer ${HttpClient.accessToken}")
                    contentType(ContentType.Application.Json)
                    setBody(
                        DataDownloadDirectory(
                            parents = currentParentsManagerDirectory,
                            systemParents = currentSystemParentsManagerDirectory,
                            directoryName =  realName,
                            systemDirectoryName = systemName
                        )
                    )
                }
            }

            request?.execute { httpResponse ->
                run {
                    val channel: ByteReadChannel = httpResponse.body()
                    while (!channel.isClosedForRead) {
                        val packet = channel.readRemaining(
                            limit = DEFAULT_BUFFER_SIZE.toLong()
                        )
                        while (!packet.isEmpty) {
                            val bytes = packet.readBytes()
                            file.appendBytes(bytes)
                        }
                    }

                    if (Environment.IS_DEV) {
                        println("A file saved to ${file.path}")
                    }
                }
            }
        }
    }

}