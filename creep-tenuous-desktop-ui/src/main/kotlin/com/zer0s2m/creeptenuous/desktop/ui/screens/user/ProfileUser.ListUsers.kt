package com.zer0s2m.creeptenuous.desktop.ui.screens.user

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.zer0s2m.creeptenuous.desktop.common.dto.User
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveCommon
import com.zer0s2m.creeptenuous.desktop.ui.screens.ProfileUser

/**
 * Rendering part of the user profile screen [Screen.PROFILE_LIST_USERS_SCREEN]
 */
@Composable
@Suppress("UnusedReceiverParameter")
fun ProfileUser.ProfileListUsers.render() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(ReactiveCommon.systemUsers) { user: User ->
            ItemUser(
                nameUser = user.name,
                loginUser = user.login,
                roleUser = user.role[0].title,
                avatar = user.avatar
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
