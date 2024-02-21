package com.zer0s2m.creeptenuous.desktop.ui.screens.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLoader
import com.zer0s2m.creeptenuous.desktop.ui.components.CardPanelBaseFolderUser
import com.zer0s2m.creeptenuous.desktop.ui.components.SwitchPanelDashboard
import com.zer0s2m.creeptenuous.desktop.ui.misc.float
import com.zer0s2m.creeptenuous.desktop.ui.screens.Dashboard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Content on the left side of the main dashboard
 *
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
            onClickLeft = {
                scope.launch {
                    val currentLevelManagerDirectory: Int = ContextScreen.get(
                        Screen.DASHBOARD_SCREEN, "currentLevelManagerDirectory")

                    if (currentLevelManagerDirectory != 0 && currentLevelManagerDirectory > 0) {
                        val currentParentsManagerDirectory: MutableList<String> = ContextScreen.get(
                            Screen.DASHBOARD_SCREEN, "currentParentsManagerDirectory")
                        val currentSystemParentsManagerDirectory: MutableList<String> = ContextScreen.get(
                            Screen.DASHBOARD_SCREEN, "currentSystemParentsManagerDirectory")

                        currentParentsManagerDirectory.removeLast()
                        currentSystemParentsManagerDirectory.removeLast()

                        ContextScreen.set(
                            Screen.DASHBOARD_SCREEN,
                            mapOf(
                                "currentLevelManagerDirectory" to currentLevelManagerDirectory - 1,
                                "currentParentsManagerDirectory" to currentParentsManagerDirectory,
                                "currentSystemParentsManagerDirectory" to currentSystemParentsManagerDirectory
                            )
                        )

                        ReactiveLoader.resetIsLoad("managerFileSystemObjects")
                        ReactiveLoader.load("managerFileSystemObjects")

                        Dashboard.setItemsBreadCrumbs(getItemsBreadCrumbs(
                            parents = currentParentsManagerDirectory,
                            systemParents = currentSystemParentsManagerDirectory
                        ))
                        if (currentLevelManagerDirectory - 1 == 0) {
                            Dashboard.setTitleSwitchPanelDashboard(title = "Main")
                        }
                    }
                }
            },
            onClickRight = {
                // TODO: Make a custom directory navigation state
            }
        )
            .render()

        Column {
            systemBaseFolderForUser.forEach { (folder, icon) ->
                CardPanelBaseFolderUser(
                    text = folder,
                    isIcon = true,
                    iconPath = icon,
                    action = {
                        println(folder)
                    }
                ).render()
            }
        }
    }
}
