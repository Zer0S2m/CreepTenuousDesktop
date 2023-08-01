package app

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import core.navigation.graphs.CollectScreenDashboard
import core.navigation.graphs.CollectScreenLoginUser
import core.navigation.graphs.CollectScreenProfileUser
import core.navigation.graphs.CollectScreenSettingsSystem
import core.navigation.runtime.rememberNavigationController
import core.reactive.ReactiveCommon
import core.reactive.ReactiveUser
import core.reactive.collectLoader
import enums.Screen
import kotlinx.coroutines.launch
import ui.theme.darkColors

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
                ReactiveUser.UserSettings,
                ReactiveUser.GrantedRights,
                ReactiveCommon
            )
        )
    }
}
