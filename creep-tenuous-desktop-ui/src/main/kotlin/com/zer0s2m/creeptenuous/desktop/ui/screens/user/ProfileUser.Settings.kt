package com.zer0s2m.creeptenuous.desktop.ui.screens.user

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.ui.screens.ProfileUser

/**
 * Rendering part of the user profile screen [Screen.PROFILE_SETTINGS_SCREEN]
 */
@Composable
fun ProfileUser.ProfileSettings.render() {
    Text("Settings", color = Color.Black)
}
