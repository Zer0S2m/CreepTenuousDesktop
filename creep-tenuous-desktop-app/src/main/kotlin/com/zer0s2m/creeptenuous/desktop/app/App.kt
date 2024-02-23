package com.zer0s2m.creeptenuous.desktop.app

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.zer0s2m.creeptenuous.desktop.common.dto.ConfigState
import com.zer0s2m.creeptenuous.desktop.common.dto.JwtTokens
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.common.utils.createDownloadFolder
import com.zer0s2m.creeptenuous.desktop.common.utils.loadStorageConfigStateDesktop
import com.zer0s2m.creeptenuous.desktop.core.auth.AuthorizationHandler
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLoader
import com.zer0s2m.creeptenuous.desktop.navigation.runtime.rememberNavigationController
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveCommon
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveFileObject
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveUser
import com.zer0s2m.creeptenuous.desktop.ui.screens.Dashboard
import com.zer0s2m.creeptenuous.desktop.ui.screens.ProfileUser
import com.zer0s2m.creeptenuous.desktop.ui.screens.graphs.CollectScreenDashboard
import com.zer0s2m.creeptenuous.desktop.ui.screens.graphs.CollectScreenLoginUser
import com.zer0s2m.creeptenuous.desktop.ui.screens.graphs.CollectScreenProfileUser
import com.zer0s2m.creeptenuous.desktop.ui.screens.graphs.CollectScreenSettingsSystem
import com.zer0s2m.creeptenuous.desktop.ui.screens.theme.darkColors
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Application launch
 *
 * Classes and their methods for dependency injection:
 * 1) [Dashboard.setManagerFileObject]
 * 2) [Dashboard.setUserProfile]
 * 3) [Dashboard.setCommentsInFileObject]
 * 4) [Dashboard.setManagerFileObjectIsLoad]
 * 5) [Dashboard.setItemsBreadCrumbs]
 * 6) [Dashboard.setTitleSwitchPanelDashboard]
 * 7) [ProfileUser.setAppliedScreenFromTransitionFromPast]
 * 8) [ProfileUser.setAppliedSectionFromTransitionFromPast]
 * 9) [ProfileUser.ProfileGrantedRights.setUserProfileGrantedRightsFileObjects]
 */
@Composable
@Preview
fun App() {
    val coroutineScope = rememberCoroutineScope()

    createDownloadFolder()

    var screen: String = Screen.DASHBOARD_SCREEN.name
    val configState: ConfigState = loadStorageConfigStateDesktop()
    var jwtTokens: JwtTokens?

    if (configState.login.isEmpty() && configState.password.isEmpty()) {
        screen = Screen.LOGIN_SCREEN.name
        ReactiveLoader.setIsBlockLoad(true)
    } else {
        if (configState.accessToken == null && configState.refreshToken == null) {
            runBlocking {
                jwtTokens = AuthorizationHandler.login(configState.login, configState.password)
                HttpClient.accessToken = jwtTokens!!.accessToken
                HttpClient.refreshToken = jwtTokens!!.refreshToken
            }
        }
    }

    val navigationController by rememberNavigationController(screen)

    coroutineScope.launch {
        ReactiveLoader.collectLoader(
            classes = listOf(
                ReactiveUser,
                ReactiveUser.UserSettings,
                ReactiveUser.GrantedRights,
                ReactiveUser.AssignedRights,
                ReactiveCommon,
                ReactiveFileObject
            ),
            injectionClasses = listOf(
                Dashboard::class,
                ProfileUser.ProfileGrantedRights::class
            ),
            injectionIndependentClasses = listOf(
                Dashboard::class,
                ProfileUser::class
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
