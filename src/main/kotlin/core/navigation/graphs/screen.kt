package core.navigation.graphs

import androidx.compose.runtime.Composable
import core.navigation.NavigationController
import core.navigation.NavigationHost
import core.navigation.foundation.composable
import enums.Screen
import screens.LoginUser
import screens.SettingsSystem

@Composable
fun CollectScreenLoginUser(navigationController: NavigationController) {
    NavigationHost(navigationController) {
        composable(Screen.LOGIN_SCREEN.name) {
            LoginUser().LoginUser()
        }
    }.build()
}

@Composable
fun CollectScreenSettingsSystem(navigationController: NavigationController) {
    NavigationHost(navigationController) {
        composable(Screen.SETTINGS_SYSTEM_SCREEN.name) {
            SettingsSystem().SettingsSystem(navigationController)
        }
    }.build()
}
