package com.zer0s2m.creeptenuous.desktop.ui.screens.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.zer0s2m.creeptenuous.desktop.ui.components.CardPanelBaseFolderUser
import com.zer0s2m.creeptenuous.desktop.ui.components.SwitchPanelDashboard
import com.zer0s2m.creeptenuous.desktop.ui.misc.float

/**
 * Content on the left side of the main dashboard
 *
 * @param systemBaseFolderForUser Base directories that are created as soon as a new user appears in the system
 */
@Composable
internal fun RenderLeftContentDashboard(
    systemBaseFolderForUser: Map<String, String>
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(com.zer0s2m.creeptenuous.desktop.common.enums.SizeComponents.LEFT_PANEL_DASHBOARD.float)
    ) {
        SwitchPanelDashboard()
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
