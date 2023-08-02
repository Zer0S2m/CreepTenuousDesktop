package com.zer0s2m.creeptenuous.desktop.app

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.zer0s2m.creeptenuous.desktop.ui.navigation.graphs.CollectScreenDashboard
import com.zer0s2m.creeptenuous.desktop.ui.navigation.graphs.CollectScreenLoginUser
import com.zer0s2m.creeptenuous.desktop.ui.navigation.graphs.CollectScreenProfileUser
import com.zer0s2m.creeptenuous.desktop.ui.navigation.graphs.CollectScreenSettingsSystem
import com.zer0s2m.creeptenuous.desktop.ui.theme.darkColors
import com.zer0s2m.creeptenuous.desktop.core.navigation.runtime.rememberNavigationController
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveCommon
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveUser
import com.zer0s2m.creeptenuous.desktop.core.reactive.collectLoader
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import kotlinx.coroutines.launch

@Composable
@Preview
fun App() {
    val navigationController by rememberNavigationController(Screen.DASHBOARD_SCREEN.name)
    val coroutineScope = rememberCoroutineScope()

    MaterialTheme(
        colors = darkColors()
    ) {
        Surface {
            CollectScreenLoginUser(navigationController)
            CollectScreenSettingsSystem(navigationController)
            CollectScreenDashboard(navigationController)
            CollectScreenProfileUser(navigationController)
        }
    }

    coroutineScope.launch {
        collectLoader(
            classes = listOf(
                ReactiveUser,
                ReactiveUser.UserSettings,
                ReactiveUser.GrantedRights,
                ReactiveCommon
            )
        )
    }
}
