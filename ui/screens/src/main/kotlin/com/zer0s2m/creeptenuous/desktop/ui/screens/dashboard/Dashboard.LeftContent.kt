package com.zer0s2m.creeptenuous.desktop.ui.screens.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import com.zer0s2m.creeptenuous.desktop.reactive.actions.ActionWalkingThroughDirectoriesFolderUserMain
import com.zer0s2m.creeptenuous.desktop.ui.components.CardPanelBaseFolderUser
import com.zer0s2m.creeptenuous.desktop.ui.components.SwitchPanelDashboard
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.float
import kotlinx.coroutines.CoroutineScope

/**
 * Content on the left side of the main dashboard
 *
 * @param scope Defines a scope for new coroutines.
 * @param titleSwitchPanelDashboard The text to be displayed (Switch).
 * @param systemBaseFolderForUser Base directories that are created as soon as a new user appears in the system
 */
@Composable
internal fun RenderLeftContentDashboard(
    scope: CoroutineScope,
    titleSwitchPanelDashboard: MutableState<String>,
    systemBaseFolderForUser: Map<String, String>
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(com.zer0s2m.creeptenuous.desktop.common.enums.SizeComponents.LEFT_PANEL_DASHBOARD.float)
    ) {
        SwitchPanelDashboard(
            title = titleSwitchPanelDashboard,
        )

        Column {
            systemBaseFolderForUser.forEach { (folder, icon) ->
                CardPanelBaseFolderUser(
                    text = folder,
                    isIcon = true,
                    iconPath = icon,
                    onClick = {
                        if (ContextScreen.containsValueByKey(
                                Screen.DASHBOARD_SCREEN,
                                "systemNameFolderUser_$folder"
                            )
                        ) {
                            if (ContextScreen.get<String>(
                                    Screen.DASHBOARD_SCREEN,
                                    "systemNameFolderUser_$folder"
                                ).isNotEmpty()
                            ) {
                                ActionWalkingThroughDirectoriesFolderUserMain.call(
                                    scope = scope,
                                    folder
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}
