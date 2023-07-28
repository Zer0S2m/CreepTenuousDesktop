package core.navigation.graphs

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import core.navigation.NavigationController
import core.navigation.NavigationHost
import core.navigation.foundation.composable
import enums.Screen
import screens.*
import screens.ProfileUser
import screens.user.*

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
            Dashboard(navigationController).render()
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
            ProfileUser(navigationController).render(
                scaffoldState = rememberScaffoldState()
            )
        }
    }.build()
}

/**
 * Building a screen and passing it to the navigation host where the context is changed and rendered
 * @param navigationController Controller to change the current state of the screen
 */
@Composable
fun CollectScreenProfileFileObjectDistribution(navigationController: NavigationController) {
    NavigationHost(navigationController) {
        composable(Screen.PROFILE_FILE_OBJECT_DISTRIBUTION.name) {
            ProfileUser.ProfileFileObjectDistribution().render()
        }
    }.build()
}

/**
 * Building a screen and passing it to the navigation host where the context is changed and rendered
 * @param navigationController Controller to change the current state of the screen
 */
@Composable
fun CollectScreenProfileSettings(navigationController: NavigationController) {
    NavigationHost(navigationController) {
        composable(Screen.PROFILE_SETTINGS_SCREEN.name) {
            ProfileUser.ProfileSettings().render()
        }
    }.build()
}

/**
 * Building a screen and passing it to the navigation host where the context is changed and rendered
 * @param navigationController Controller to change the current state of the screen
 */
@Composable
fun CollectScreenProfileGrantedRights(navigationController: NavigationController) {
    NavigationHost(navigationController) {
        composable(Screen.PROFILE_GRANTED_RIGHTS_SCREEN.name) {
            ProfileUser.ProfileGrantedRights().render()
        }
    }.build()
}

/**
 * Building a screen and passing it to the navigation host where the context is changed and rendered
 * @param navigationController Controller to change the current state of the screen
 */
@Composable
fun CollectScreenProfileListUsers(navigationController: NavigationController) {
    NavigationHost(navigationController) {
        composable(Screen.PROFILE_LIST_USERS_SCREEN.name) {
            ProfileUser.ProfileListUsers().render()
        }
    }.build()
}

/**
 * Building a screen and passing it to the navigation host where the context is changed and rendered
 * @param navigationController Controller to change the current state of the screen
 */
@Composable
fun CollectScreenProfileUserControl(navigationController: NavigationController) {
    NavigationHost(navigationController) {
        composable(Screen.PROFILE_USER_MANAGEMENT_SCREEN.name) {
            ProfileUser.ProfileUserControl().render()
        }
    }.build()
}

/**
 * Building a screen and passing it to the navigation host where the context is changed and rendered
 * @param navigationController Controller to change the current state of the screen
 */
@Composable
fun CollectScreenProfileCategories(navigationController: NavigationController) {
    NavigationHost(navigationController) {
        composable(Screen.PROFILE_CATEGORY_SCREEN.name) {
            ProfileUser.ProfileCategories().render()
        }
    }.build()
}

/**
 * Building a screen and passing it to the navigation host where the context is changed and rendered
 * @param navigationController Controller to change the current state of the screen
 */
@Composable
fun CollectScreenProfileColors(navigationController: NavigationController) {
    NavigationHost(navigationController) {
        composable(Screen.PROFILE_COLORS_SCREEN.name) {
            ProfileUser.ProfileColors().render()
        }
    }.build()
}
