package screens.user

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import enums.Screen
import screens.ProfileUser

/**
 * Rendering part of the user profile screen [Screen.PROFILE_GRANTED_RIGHTS_SCREEN]
 */
@Composable
fun ProfileUser.ProfileGrantedRights.render() {
    Text("Viewing granted rights", color = Color.Black)
}
