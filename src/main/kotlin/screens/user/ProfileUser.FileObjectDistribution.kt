package screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import enums.Colors
import enums.Resources
import enums.Screen
import screens.ProfileUser
import ui.animations.setAnimateColorAsStateInSelectUser
import ui.animations.setHoverInSelectUser

/**
 * Rendering part of the user profile screen [Screen.PROFILE_FILE_OBJECT_DISTRIBUTION]
 */
@Composable
fun ProfileUser.ProfileFileObjectDistribution.render() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                baseTitle("Delete them if necessary (has the highest priority)")
                switch()
            }
        }

        Row(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                baseTitle("Passing objects to an assigned user")
                selectUserDropMenu()
            }
        }
    }
}

/**
 * Base title for a particular setting item
 *
 * @param text The text to be displayed
 */
@Composable
private fun baseTitle(text: String) = Text(
    text = text,
    color = Color.Black,
    fontSize = 16.sp,
    fontWeight = FontWeight.SemiBold
)

/**
 * Distribution setting switch
 */
@Composable
internal fun ProfileUser.ProfileFileObjectDistribution.switch() {
    val checkedState = remember { mutableStateOf(true) }
    Switch(
        checked = checkedState.value,
        modifier = Modifier
            .pointerHoverIcon(icon = PointerIcon.Hand),
        onCheckedChange = { checkedState.value = it }
    )
}

/**
 * Selecting a user to set up a distribution
 */
@Composable
internal fun ProfileUser.ProfileFileObjectDistribution.selectUserDropMenu() {
    Column(
        modifier = Modifier
            .width(200.dp)
            .padding(top = 12.dp)
    ) {
        UserLoginTextField(text = "User name 1")
    }
}

/**
 * Base field for displaying the user's login when its value is selected
 *
 * @param text the input [String] text to be shown in the text field
 */
@Composable
private fun UserLoginTextField(text: String) {
    val textState by remember { mutableStateOf(text) }

    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    val isHover: MutableState<Boolean> = remember { mutableStateOf(false) }
    val animatedCardColor = setAnimateColorAsStateInSelectUser(
        isHover = isHover,
        backgroundHover = Colors.SECONDARY_VARIANT.color
    )

    setHoverInSelectUser(
        interactionSource = interactionSource,
        isHover = isHover
    )

    Row(
        modifier = Modifier
            .pointerHoverIcon(PointerIcon.Hand)
            .fillMaxWidth()
            .border(0.5.dp, MaterialTheme.colors.secondary, RoundedCornerShape(4.dp))
            .hoverable(interactionSource = interactionSource)
            .background(animatedCardColor.value)
    ) {
        Row(
            modifier = Modifier
                .pointerHoverIcon(PointerIcon.Hand)
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = textState,
                color = Colors.TEXT.color
            )

            Icon(
                painter = painterResource(resourcePath = Resources.ICON_ARROW.path),
                contentDescription = contentDescriptionIconArrow,
                tint = MaterialTheme.colors.secondaryVariant,
                modifier = Modifier
                    .size(16.dp)
            )
        }
    }
}

/**
 * ext used by accessibility services to describe what this image represents
 */
@get:ReadOnlyComposable
private val contentDescriptionIconArrow: String get() = "Open user list"
