package com.zer0s2m.creeptenuous.desktop.reactive.actions

import com.zer0s2m.creeptenuous.desktop.common.dto.FileObject
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.core.actions.Action
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import kotlinx.coroutines.CoroutineScope

object ActionWalkingThroughDirectoriesFolderUserMain : Action {

    /**
     * Call action.
     *
     * @param scope Defines a scope for new coroutines.
     * @param values Additional data.
     */
    override fun call(scope: CoroutineScope, vararg values: Any?) {
        val folder: String = (if (values[0] is String) values[0].toString() else null) ?: return

        var isSet = false
        var systemName: String? = null
        var realName: String? = null

        if (folder == "Videos") {
            if (ContextScreen
                    .containsValueByKey(Screen.DASHBOARD_SCREEN, "systemNameFolderUser_Videos")
            ) {
                systemName = ContextScreen
                    .get(Screen.DASHBOARD_SCREEN, "systemNameFolderUser_Videos")
                realName = "Videos"
                isSet = true
            }
        } else if (folder == "Documents") {
            if (ContextScreen
                    .containsValueByKey(Screen.DASHBOARD_SCREEN, "systemNameFolderUser_Documents")
            ) {
                systemName = ContextScreen
                    .get(Screen.DASHBOARD_SCREEN, "systemNameFolderUser_Documents")
                realName = "Documents"
                isSet = true
            }
        } else if (folder == "Images") {
            if (ContextScreen
                    .containsValueByKey(Screen.DASHBOARD_SCREEN, "systemNameFolderUser_Images")
            ) {
                systemName = ContextScreen
                    .get(Screen.DASHBOARD_SCREEN, "systemNameFolderUser_Images")
                realName = "Images"
                isSet = true
            }
        } else if (folder == "Musics") {
            if (ContextScreen
                    .containsValueByKey(Screen.DASHBOARD_SCREEN, "systemNameFolderUser_Musics")
            ) {
                systemName = ContextScreen
                    .get(Screen.DASHBOARD_SCREEN, "systemNameFolderUser_Musics")
                realName = "Musics"
                isSet = true
            }
        }

        if (isSet) {
            ContextScreen.set(
                Screen.DASHBOARD_SCREEN,
                mapOf(
                    "currentLevelManagerDirectory" to 0,
                    "currentParentsManagerDirectory" to listOf<String>().toMutableList(),
                    "currentSystemParentsManagerDirectory" to listOf<String>().toMutableList()
                )
            )

            ActionWalkingThroughDirectories.call(
                scope = scope,
                FileObject(
                    realName = realName!!,
                    systemName = systemName!!,
                    isFile = false,
                    isDirectory = true
                )
            )
        }
    }

}