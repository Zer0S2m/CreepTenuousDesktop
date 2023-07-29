package app

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import core.navigation.graphs.CollectScreenDashboard
import core.navigation.graphs.CollectScreenLoginUser
import core.navigation.graphs.CollectScreenProfileUser
import core.navigation.graphs.CollectScreenSettingsSystem
import core.navigation.runtime.rememberNavigationController
import enums.Screen
import ui.theme.darkColors

@Composable
@Preview
fun App() {
    val navigationController by rememberNavigationController(Screen.DASHBOARD_SCREEN.name)

    MaterialTheme(
        colors = darkColors()
    ) {
        NavigationRail {
            CollectScreenLoginUser(navigationController)
            CollectScreenSettingsSystem(navigationController)
            CollectScreenDashboard(navigationController)
            CollectScreenProfileUser(navigationController)
        }
    }
}
