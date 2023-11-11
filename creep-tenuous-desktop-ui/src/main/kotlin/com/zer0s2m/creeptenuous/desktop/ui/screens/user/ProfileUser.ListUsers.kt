package com.zer0s2m.creeptenuous.desktop.ui.screens.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveCommon
import com.zer0s2m.creeptenuous.desktop.ui.screens.ProfileUser

/**
 * Rendering part of the user profile screen [Screen.PROFILE_LIST_USERS_SCREEN]
 */
@Composable
@Suppress("UnusedReceiverParameter")
fun ProfileUser.ProfileListUsers.render() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ReactiveCommon.systemUsers.forEach {
            ItemUser(
                nameUser = it.name,
                loginUser = it.login,
                roleUser = it.role[0].title,
                avatar = it.avatar
            )
        }
    }
}

/**
 * The main card to show the user in the system
 *
 * @param nameUser Username.
 * @param loginUser Login user.
 * @param roleUser Role.
 * @param avatar Avatar for user.
 */
@Composable
private fun ItemUser(
    nameUser: String,
    loginUser: String,
    roleUser: String,
    avatar: String?
) {
    BaseCardForItemCardUser(
        nameUser = nameUser,
        loginUser = loginUser,
        avatar = avatar
    ) {
        Text(
            text = "Role - $roleUser"
        )
    }
}
