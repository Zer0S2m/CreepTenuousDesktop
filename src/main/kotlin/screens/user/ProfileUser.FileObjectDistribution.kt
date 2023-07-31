package screens.user

import androidx.compose.foundation.*
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
import androidx.compose.ui.unit.Dp
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
                BaseTitle("Delete them if necessary (has the highest priority)")
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
                BaseTitle("Passing objects to an assigned user")
                SelectUserDropMenu()
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
private fun BaseTitle(text: String) = Text(
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
internal fun ProfileUser.ProfileFileObjectDistribution.SelectUserDropMenu() {
    val selectedUserItem: MutableState<Int> = remember { mutableStateOf(-1) }
    val textState: MutableState<String> = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .width(baseWidthColumnSelectUser)
            .padding(top = 12.dp)
    ) {
        if (selectedUserItem.value != -1) {
            textState.value = selectUseItems[selectedUserItem.value]
            UserLoginTextField(text = textState, selectedUserItem = selectedUserItem)
        } else {
            UserLoginTextField(text = textState, selectedUserItem = selectedUserItem)
        }
    }
}

/**
 * Base field for displaying the user's login when its value is selected
 *
 * @param text the input [String] text to be shown in the text field
 * @param selectedUserItem State of the selected user from the list by his index
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun UserLoginTextField(
    text: MutableState<String>,
    selectedUserItem: MutableState<Int>
) {
    val expandedStates: MutableState<Boolean> = remember { mutableStateOf(false) }

    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    val isHover: MutableState<Boolean> = remember { mutableStateOf(false) }
    val animatedCardColor = setAnimateColorAsStateInSelectUser(
        isHover = isHover,
        backgroundHover = Colors.SECONDARY_VARIANT.color.copy(0.1f)
    )

    setHoverInSelectUser(
        interactionSource = interactionSource,
        isHover = isHover
    )

    Row(
        modifier = Modifier
            .pointerHoverIcon(PointerIcon.Hand)
            .fillMaxWidth()
            .onClick {
                expandedStates.value = true
            }
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
                text = text.value,
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

        DropdownMenuSelectUser(expandedStates = expandedStates, selectedUserItem = selectedUserItem)
    }
}

/**
 * User select dropdown
 *
 * @param expandedStates Whether the menu is currently open and visible to the user
 * @param selectedUserItem State of the selected user from the list by his index
 */
@Composable
private fun DropdownMenuSelectUser(
    expandedStates: MutableState<Boolean>,
    selectedUserItem: MutableState<Int>,
) {
    DropdownMenu(
        expanded = expandedStates.value,
        onDismissRequest = {
            expandedStates.value = false
            println(selectedUserItem)
        },
        modifier = Modifier
            .width(baseWidthColumnSelectUser)
            .padding(0.dp)
            .background(Colors.SECONDARY_VARIANT.color)
    ) {
        selectUseItems.forEachIndexed { index, item ->
            DropdownMenuItemSelectUser(expandedStates, selectedUserItem, index) {
                Text(item, color = Color.White)
            }
        }
    }
}

/**
 * The base element for selecting a user from a drop-down list
 *
 * @param expandedStates Whether the menu is currently open and visible to the user
 * @param selectedUserItem State of the selected user from the list by his index
 * @param index Index of an element from a list [selectUseItems]
 * @param content Internal block content
 */
@Composable
private fun DropdownMenuItemSelectUser(
    expandedStates: MutableState<Boolean>,
    selectedUserItem: MutableState<Int>,
    index: Int,
    content: @Composable () -> Unit
) {
    DropdownMenuItem(
        onClick = {
            selectedUserItem.value = index
            expandedStates.value = false
        },
        modifier = Modifier
            .pointerHoverIcon(PointerIcon.Hand),
        contentPadding = PaddingValues(12.dp)
    ) {
        content()
    }
}

/**
 * Text used by accessibility services to describe what this image represents
 */
@get:ReadOnlyComposable
private val contentDescriptionIconArrow: String get() = "Open user list"

/**
 * Width of user selection area from list
 */
@get:ReadOnlyComposable
private val baseWidthColumnSelectUser: Dp get() = 200.dp

/**
 * User choice list
 */
@get:ReadOnlyComposable
private val selectUseItems: List<String> get() = listOf(
    "User name 1",
    "User name 2",
    "User name 3"
)
