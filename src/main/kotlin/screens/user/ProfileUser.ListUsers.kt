package screens.user

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        itemUser(nameUser = "Name user 1", textRoleUser = "USER")
        itemUser(nameUser = "Name user 2", textRoleUser = "USER")
        itemUser(nameUser = "Name user 3", textRoleUser = "ADMIN")
    }
}

/**
 * The main card to show the user in the system
 *
 * @param nameUser The text to be displayed.
 * @param textRoleUser The text to be displayed.
 */
@Composable
internal fun ProfileUser.ProfileListUsers.itemUser(
    nameUser: String = "",
    textRoleUser: String = ""
) {
    BaseCardForItemCardUser(
        nameUser = nameUser
    ) {
        Text(
            text = "Role - $textRoleUser"
        )
    }
}
