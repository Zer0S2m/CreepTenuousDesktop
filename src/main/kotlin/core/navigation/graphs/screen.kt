package core.navigation.graphs

import androidx.compose.runtime.Composable
import core.navigation.NavigationController
import core.navigation.NavigationHost
import core.navigation.foundation.composable
import enums.Screen
import screens.Dashboard
import screens.LoginUser
import screens.ProfileUser
import screens.SettingsSystem

/**
 * Building a screen and passing it to the navigation host where the context is changed and rendered
 * @param navigationController Controller to change the current state of the screen
 */
@Composable
fun CollectScreenLoginUser(navigationController: NavigationController) {
    NavigationHost(navigationController) {
        composable(Screen.LOGIN_SCREEN.name) {
            LoginUser().LoginUser(navigationController)
        }
    }.build()
}

/**
 * Building a screen and passing it to the navigation host where the context is changed and rendered
 * @param navigationController Controller to change the current state of the screen
 */
@Composable
fun CollectScreenSettingsSystem(navigationController: NavigationController) {
    NavigationHost(navigationController) {
        composable(Screen.SETTINGS_SYSTEM_SCREEN.name) {
            SettingsSystem().SettingsSystem(navigationController)
        }
    }.build()
}

/**
 * Building a screen and passing it to the navigation host where the context is changed and rendered
 * @param navigationController Controller to change the current state of the screen
 */
@Composable
fun CollectScreenDashboard(navigationController: NavigationController) {
    NavigationHost(navigationController) {
        composable(Screen.DASHBOARD_SCREEN.name) {
            Dashboard().render()
        }
    }.build()
}

/**
 * Building a screen and passing it to the navigation host where the context is changed and rendered
 * @param navigationController Controller to change the current state of the screen
 */
@Composable
fun CollectScreenProfileUser(navigationController: NavigationController) {
    NavigationHost(navigationController) {
        composable(Screen.PROFILE_SCREEN.name) {
            ProfileUser().render(navigationController)
        }
    }.build()
}
