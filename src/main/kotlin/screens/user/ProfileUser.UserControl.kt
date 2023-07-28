package screens.user

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import enums.Resources
import enums.Screen
import screens.ProfileUser

/**
 * Rendering part of the user profile screen [Screen.PROFILE_USER_MANAGEMENT_SCREEN]
 */
@Composable
fun ProfileUser.ProfileUserControl.render() {
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
 * Text used by accessibility services to describe what this image represents
 */
@Stable
private val contentDescriptionDelete: String get() = "Delete user icon"

/**
 * Text used by accessibility services to describe what this image represents
 */
@Stable
private val contentDescriptionBlock: String get() = "User lock icon"

/**
 * optional [Modifier] for this [Icon]
 */
@Stable
private val baseModifierIcon: Modifier get() = Modifier
    .size(24.dp)
    .pointerHoverIcon(icon = PointerIcon.Hand)

/**
 * The main card to show the user in the system
 *
 * @param nameUser The text to be displayed.
 * @param textRoleUser The text to be displayed.
 */
@Composable
internal fun ProfileUser.ProfileUserControl.itemUser(
    nameUser: String = "",
    textRoleUser: String = ""
) {
    baseCardForItemCardUser(
        nameUser = nameUser,
        fractionBaseInfoUser = 0.6f
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.75f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Role - $textRoleUser"
                )
            }
            BaseTooltipAreaForItemUser(text = "User blocking") {
                IconButton(
                    onClick = {
                        println("Block user")
                    }
                ) {
                    Icon(
                        painter = painterResource(resourcePath = Resources.ICON_BLOCK.path),
                        contentDescription = contentDescriptionBlock,
                        modifier = baseModifierIcon,
                        tint = Color.Red
                    )
                }
            }

            BaseTooltipAreaForItemUser(text = "Deleting a user") {
                IconButton(
                    onClick = {
                        println("Delete user")
                    }
                ) {
                    Icon(
                        painter = painterResource(resourcePath = Resources.ICON_DELETE.path),
                        contentDescription = contentDescriptionDelete,
                        modifier = baseModifierIcon,
                        tint = Color.Red
                    )
                }
            }
        }
    }
}

/**
 * Quick tooltip
 *
 * @param text The text to be displayed
 * @param content Map internal content
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BaseTooltipAreaForItemUser(
    text: String,
    content: @Composable () -> Unit
) {
    val round: Shape = RoundedCornerShape(6.dp)
    TooltipArea(
        tooltip = {
            Box(
                modifier = Modifier
                    .background(Color.Black, round)
                    .padding(6.dp, 4.dp)
                    .clip(round)
                    .border(BorderStroke(0.dp, Color.Black), round)
            ) {
                Text(
                    text = text,
                    color = Color.White,
                    fontSize = 13.sp
                )
            }
        },
        delayMillis = 700
    ) {
        content()
    }
}
