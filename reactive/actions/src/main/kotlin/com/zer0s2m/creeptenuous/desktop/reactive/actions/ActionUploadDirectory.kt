package com.zer0s2m.creeptenuous.desktop.reactive.actions

import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.core.actions.Action
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLoader
import com.zer0s2m.creeptenuous.desktop.core.state.SystemSettings
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File

object ActionUploadDirectory  : Action {

    /**
     * Call action.
     *
     * @param scope Defines a scope for new coroutines.
     * @param values Additional data.
     */
    override fun call(scope: CoroutineScope, vararg values: Any?) {
        val file: File = (if (values[0] is File) values[0] as File else null) ?: return

        scope.launch {
            ReactiveLoader.resetIsLoad("managerFileSystemObjects")

            val currentParentsManagerDirectory: MutableList<String> = ContextScreen.get(
                Screen.DASHBOARD_SCREEN, "currentParentsManagerDirectory"
            )
            val currentSystemParentsManagerDirectory: MutableList<String> = ContextScreen.get(
                Screen.DASHBOARD_SCREEN, "currentSystemParentsManagerDirectory"
            )

            HttpClient.client.submitFormWithBinaryData(
                url = "${SystemSettings.host}:${SystemSettings.port}/api/v1/directory/upload",
                formData = formData {
                    append("directory", file.readBytes(), Headers.build {
                        append(HttpHeaders.ContentDisposition, "filename=${file.name}")
                    })
                }
            ) {
                header("Authorization", "Bearer ${SystemSettings.accessToken}")
                parameter("parents", currentParentsManagerDirectory.joinToString(","))
                parameter("systemParents", currentSystemParentsManagerDirectory.joinToString(","))
            }

            ReactiveLoader.load("managerFileSystemObjects")
        }
    }

}