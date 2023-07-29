package app

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.NavigationRail
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
    val navigationController by rememberNavigationController(Screen.SETTINGS_SYSTEM_SCREEN.name)

    MaterialTheme(
        colors = darkColors()
    ) {
        Surface(
            modifier = Modifier.background(color = MaterialTheme.colors.background)
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                NavigationRail {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CollectScreenLoginUser(navigationController)
                        CollectScreenSettingsSystem(navigationController)
                        CollectScreenDashboard(navigationController)
                        CollectScreenProfileUser(navigationController)
                    }
                }
            }
        }
    }
}
