package screens.user

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import components.misc.Avatar
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
        itemUser(nameUser = "Name user 3", textRoleUser = "USER")
        itemUser(nameUser = "Name user 4", textRoleUser = "ADMIN")
    }
}

/**
 * The main card to show the user in the system
 *
 * @param nameUser The text to be displayed.
 * @param textRoleUser The text to be displayed.
 */
@Composable
internal fun itemUser(
    nameUser: String = "",
    textRoleUser: String = ""
) {
    Card(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.8f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Avatar(
                    modifierIcon = Modifier
                        .size(32.dp)
                        .pointerHoverIcon(icon = PointerIcon.Default)
                        .padding(0.dp)
                ).render()
                Text(
                    text = nameUser,
                    modifier = Modifier
                        .padding(start = 8.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    text = "Role - $textRoleUser"
                )
            }
        }
    }
}
