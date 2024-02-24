package com.zer0s2m.creeptenuous.desktop.reactive.actions

import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.core.actions.Action
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLoader
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File

object ActionUploadFiles : Action {

    /**
     * Call action.
     *
     * @param scope Defines a scope for new coroutines.
     * @param values Additional data.
     */
    @Suppress("UNCHECKED_CAST")
    override fun call(scope: CoroutineScope, vararg values: Any?) {
        val files: List<File> =
            (if (values[0] is List<*>) values[0] as List<File> else null) ?: return

        if (files.isEmpty()) return

        scope.launch {
            ReactiveLoader.resetIsLoad("managerFileSystemObjects")

            val currentParentsManagerDirectory: MutableList<String> = ContextScreen.get(
                Screen.DASHBOARD_SCREEN, "currentParentsManagerDirectory"
            )
            val currentSystemParentsManagerDirectory: MutableList<String> = ContextScreen.get(
                Screen.DASHBOARD_SCREEN, "currentSystemParentsManagerDirectory"
            )

            HttpClient.client.submitFormWithBinaryData(
                url = "/api/v1/file/upload",
                formData = formData {
                    files.forEach { file ->
                        append("files", file.readBytes(), Headers.build {
                            append(HttpHeaders.ContentDisposition, "filename=${file.name}")
                        })
                    }
                }
            ) {
                header("Authorization", "Bearer ${HttpClient.accessToken}")
                parameter("parents", currentParentsManagerDirectory.joinToString(","))
                parameter("systemParents", currentSystemParentsManagerDirectory.joinToString(","))
            }

            ReactiveLoader.load("managerFileSystemObjects")
        }
    }

}