package screens.user

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.reactive.ReactiveCommon
import enums.Screen
import screens.ProfileUser

/**
 * Rendering part of the user profile screen [Screen.PROFILE_LIST_USERS_SCREEN]
 */
@Composable
fun ProfileUser.ProfileListUsers.render() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ReactiveCommon.systemUsers.forEach {
            itemUser(nameUser = it.name, loginUser = it.login, roleUser = it.role[0].title)
        }
    }
}

/**
 * The main card to show the user in the system
 *
 * @param nameUser Username.
 * @param loginUser Login user..
 * @param roleUser Role.
 */
@Composable
internal fun ProfileUser.ProfileListUsers.itemUser(
    nameUser: String,
    loginUser: String,
    roleUser: String
) {
    BaseCardForItemCardUser(
        nameUser = nameUser, loginUser = loginUser
    ) {
        Text(
            text = "Role - $roleUser"
        )
    }
}
