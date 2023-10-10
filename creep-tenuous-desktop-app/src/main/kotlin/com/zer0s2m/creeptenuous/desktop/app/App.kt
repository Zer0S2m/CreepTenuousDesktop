package com.zer0s2m.creeptenuous.desktop.app

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLoader
import com.zer0s2m.creeptenuous.desktop.navigation.runtime.rememberNavigationController
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveCommon
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveFileObject
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveUser
import com.zer0s2m.creeptenuous.desktop.ui.navigation.graphs.CollectScreenDashboard
import com.zer0s2m.creeptenuous.desktop.ui.navigation.graphs.CollectScreenLoginUser
import com.zer0s2m.creeptenuous.desktop.ui.navigation.graphs.CollectScreenProfileUser
import com.zer0s2m.creeptenuous.desktop.ui.navigation.graphs.CollectScreenSettingsSystem
import com.zer0s2m.creeptenuous.desktop.ui.screens.Dashboard
import com.zer0s2m.creeptenuous.desktop.ui.theme.darkColors
import kotlinx.coroutines.launch

/**
 * Application launch
 *
 * Classes and their methods for dependency injection:
 * 1) [Dashboard.setManagerFileObject]
 * 2) [Dashboard.setUserProfile]
 * 3) [Dashboard.setCommentsInFileObject]
 */
@Composable
@Preview
fun App() {
    val navigationController by rememberNavigationController(Screen.DASHBOARD_SCREEN.name)
    val coroutineScope = rememberCoroutineScope()

    coroutineScope.launch {
        ReactiveLoader.collectLoader(
            classes = listOf(
                ReactiveUser,
                ReactiveUser.UserSettings,
                ReactiveUser.GrantedRights,
                ReactiveCommon,
                ReactiveFileObject
            ),
            injectionClasses = hashMapOf(
                Dashboard::class to listOf(
                    "setManagerFileObject",
                    "setUserProfile",
                    "setCommentsInFileObject"
                )
            )
        )
    }

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
}
