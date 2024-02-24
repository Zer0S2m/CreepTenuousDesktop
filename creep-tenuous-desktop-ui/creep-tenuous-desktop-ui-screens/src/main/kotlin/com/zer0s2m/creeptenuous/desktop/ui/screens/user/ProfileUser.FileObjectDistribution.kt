package com.zer0s2m.creeptenuous.desktop.ui.screens.user

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zer0s2m.creeptenuous.desktop.common.dto.UserSettingsFileObjectDistribution
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLoader
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveCommon
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveUser
import com.zer0s2m.creeptenuous.desktop.ui.components.LayoutDeleteAndOpenInputSelect
import com.zer0s2m.creeptenuous.desktop.ui.components.animations.setAnimateColorAsStateInSelectUser
import com.zer0s2m.creeptenuous.desktop.ui.components.animations.setHoverInSelectUser
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.Colors
import com.zer0s2m.creeptenuous.desktop.ui.screens.ProfileUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Rendering part of the user profile screen [Screen.PROFILE_FILE_OBJECT_DISTRIBUTION]
 */
@Composable
@Suppress("UnusedReceiverParameter")
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
                Switch()
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
private fun Switch() {
    val scope = rememberCoroutineScope()
    val isDeletingFilesWhenDeletingUser: Boolean =
        ReactiveUser.UserSettings.userSettingsFileObjectDistribution.isDeletingFilesWhenDeletingUser!!
    val checkedState = remember { mutableStateOf(isDeletingFilesWhenDeletingUser) }

    Switch(
        checked = checkedState.value,
        modifier = Modifier
            .pointerHoverIcon(icon = PointerIcon.Hand),
        onCheckedChange = {
            checkedState.value = it

            scope.launch {
                ReactiveLoader.setReactiveValue(
                    "userSettingsFileObjectDistribution",
                    "setIsDeleteFileObjects",
                    UserSettingsFileObjectDistribution(
                        it,
                        ReactiveUser.UserSettings.userSettingsFileObjectDistribution.passingFilesToUser
                    )
                )
            }
        }
    )
}

/**
 * Selecting a user to set up a distribution
 */
@Composable
internal fun SelectUserDropMenu() {
    val selectedUserItem: MutableState<String?> = remember {
        mutableStateOf(
            ReactiveUser.UserSettings.userSettingsFileObjectDistribution.passingFilesToUser
        )
    }
    val textState: MutableState<String> = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .width(ProfileFileObjectDistribution.baseWidthColumnSelectUser)
            .padding(top = 12.dp)
    ) {
        textState.value = ReactiveCommon.systemUsers.find {
            it.login == selectedUserItem.value
        }?.name ?: ""
        UserLoginTextField(text = textState, selectedUserItem = selectedUserItem)
    }
}

/**
 * Base field for displaying the user's login when its value is selected
 *
 * @param text the input [String] text to be shown in the text field
 * @param selectedUserItem State of the selected user from the list by his login
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun UserLoginTextField(
    text: MutableState<String>,
    selectedUserItem: MutableState<String?>
) {
    val expandedStates: MutableState<Boolean> = remember { mutableStateOf(false) }
    val scope: CoroutineScope = rememberCoroutineScope()
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

            LayoutDeleteAndOpenInputSelect(
                actionDelete = {
                    scope.launch {
                        ReactiveLoader.setReactiveValue(
                            "userSettingsFileObjectDistribution",
                            "setTransferUserFileObjects",
                            UserSettingsFileObjectDistribution(
                                ReactiveUser.UserSettings.userSettingsFileObjectDistribution.isDeletingFilesWhenDeletingUser,
                                null
                            )
                        )
                    }

                    selectedUserItem.value = null
                }
            )
        }

        DropdownMenuSelectUser(
            expandedStates = expandedStates,
            selectedUserItem = selectedUserItem,
            scope = scope
        )
    }
}

/**
 * User select dropdown
 *
 * @param expandedStates Whether the menu is currently open and visible to the user
 * @param selectedUserItem State of the selected user from the list by his login
 */
@Composable
private fun DropdownMenuSelectUser(
    expandedStates: MutableState<Boolean>,
    selectedUserItem: MutableState<String?>,
    scope: CoroutineScope
) {
    DropdownMenu(
        expanded = expandedStates.value,
        onDismissRequest = {
            expandedStates.value = false
        },
        modifier = Modifier
            .width(ProfileFileObjectDistribution.baseWidthColumnSelectUser)
            .padding(0.dp)
            .background(Colors.SECONDARY_VARIANT.color)
    ) {
        ReactiveCommon.systemUsers.forEach { user ->
            DropdownMenuItem(
                onClick = {
                    scope.launch {
                        ReactiveLoader.setReactiveValue(
                            "userSettingsFileObjectDistribution",
                            "setTransferUserFileObjects",
                            UserSettingsFileObjectDistribution(
                                ReactiveUser.UserSettings.userSettingsFileObjectDistribution.isDeletingFilesWhenDeletingUser,
                                user.login
                            )
                        )
                    }

                    selectedUserItem.value = user.login
                    expandedStates.value = false
                },
                modifier = Modifier
                    .pointerHoverIcon(PointerIcon.Hand),
                contentPadding = PaddingValues(12.dp)
            ) {
                Text(user.name, color = Color.White)
            }
        }
    }
}

private object ProfileFileObjectDistribution {

    /**
     * Text used by accessibility services to describe what this image represents
     */
    @get:ReadOnlyComposable
    val contentDescriptionIconArrow: String get() = "Open user list"

    /**
     * Width of user selection area from list
     */
    @get:ReadOnlyComposable
    val baseWidthColumnSelectUser: Dp get() = 200.dp

}