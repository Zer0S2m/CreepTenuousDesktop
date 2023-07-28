package screens.user

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import enums.Screen
import screens.ProfileUser

/**
 * Rendering part of the user profile screen [Screen.PROFILE_FILE_OBJECT_DISTRIBUTION]
 */
@Composable
fun ProfileUser.ProfileFileObjectDistribution.render() {
    Text("File object distribution settings", color = Color.Black)
}
