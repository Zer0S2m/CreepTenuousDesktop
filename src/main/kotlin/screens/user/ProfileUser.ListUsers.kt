package screens.user

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import enums.Screen
import screens.ProfileUser

/**
 * Rendering part of the user profile screen [Screen.PROFILE_LIST_USERS_SCREEN]
 */
@Composable
fun ProfileUser.ProfileListUsers.render() {
    Text("List of users", color = Color.Black)
}
