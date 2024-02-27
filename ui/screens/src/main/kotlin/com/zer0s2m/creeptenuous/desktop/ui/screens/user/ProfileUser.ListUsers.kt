package com.zer0s2m.creeptenuous.desktop.ui.screens.user

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.zer0s2m.creeptenuous.desktop.common.dto.User
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveCommon
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveUser
import com.zer0s2m.creeptenuous.desktop.ui.components.CardUserCommon
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
            if (ReactiveUser.profileSettings!!.login != user.login) {
                CardUserCommon(
                    nameUser = user.name,
                    loginUser = user.login,
                    roleUser = user.role[0].title,
                    avatar = user.avatar
                )
            }
        }
    }
}
