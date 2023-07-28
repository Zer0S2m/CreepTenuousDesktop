package screens.user

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import screens.ProfileUser
import enums.Screen

/**
 * Rendering part of the user profile screen [Screen.PROFILE_COLORS_SCREEN]
 */
@Composable
fun ProfileUser.ProfileColors.render() {
    Text("Colors", color = Color.Black)
}
