package com.zer0s2m.creeptenuous.desktop.ui.screens.user

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zer0s2m.creeptenuous.desktop.common.dto.User
import com.zer0s2m.creeptenuous.desktop.common.enums.Resources
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveCommon
import com.zer0s2m.creeptenuous.desktop.ui.screens.ProfileUser
import com.zer0s2m.creeptenuous.desktop.ui.screens.base.BaseModalPopup

/**
 * Rendering part of the user profile screen [Screen.PROFILE_USER_MANAGEMENT_SCREEN]
 */
@Composable
fun ProfileUser.ProfileUserControl.render() {
    val openDialog: MutableState<Boolean> = remember { mutableStateOf(false) }
    val currentUser: MutableState<User?> = mutableStateOf(null)
    val users: MutableState<MutableList<User>> = mutableStateOf(ReactiveCommon.systemUsers.toMutableStateList())

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        users.value.forEach { user ->
            itemUser(
                nameUser = user.name,
                loginUser = user.login,
                roleUser = user.role[0].title,
                user.isBlocked,
                actionDelete = { _ ->
                    openDialog.value = true
                    currentUser.value = user
                },
                actionBlock = {

                },
                actionUnblock = {

                }
            )
        }
    }

    AlertDialogDeleteUser(
        openDialog = openDialog,
        actionDelete = {
            openDialog.value = false
            currentUser.value?.let {
                users.value.remove(currentUser.value)
                ReactiveCommon.systemUsers.removeReactive(it)
            }
        }
    )
}

/**
 * Text used by accessibility services to describe what this image represents
 */
@get:ReadOnlyComposable
private val contentDescriptionBlock: String get() = "User lock icon"

/**
 * Text used by accessibility services to describe what this image represents
 */
@get:ReadOnlyComposable
private val contentDescriptionUnblock: String get() = "User unlock icon"

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
 * @param nameUser Username.
 * @param loginUser Login user.
 * @param roleUser Role.
 * @param isBlocked Is blocked user.
 * @param actionDelete The action is called when the delete user button is clicked.
 * @param actionBlock The action is called when the block user button is clicked.
 * @param actionUnblock The action is called when the unblock user button is clicked.
 */
@Composable
internal fun ProfileUser.ProfileUserControl.itemUser(
    nameUser: String,
    loginUser: String,
    roleUser: String,
    isBlocked: Boolean,
    actionDelete: (String) -> Unit = {},
    actionBlock: () -> Unit = {},
    actionUnblock: () -> Unit = {},
) {
    BaseCardForItemCardUser(
        nameUser = nameUser,
        loginUser = loginUser,
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
                    text = "Role - $roleUser"
                )
            }
            if (!isBlocked) {
                BaseTooltipAreaForItemUser(text = "User blocking") {
                    IconButton(
                        onClick = actionBlock
                    ) {
                        Icon(
                            painter = painterResource(resourcePath = Resources.ICON_BLOCK.path),
                            contentDescription = contentDescriptionBlock,
                            modifier = baseModifierIcon,
                            tint = Color.Red
                        )
                    }
                }
            } else {
                BaseTooltipAreaForItemUser(text = "User unblocking") {
                    IconButton(
                        onClick = actionUnblock
                    ) {
                        Icon(
                            painter = painterResource(resourcePath = Resources.ICON_UNBLOCK.path),
                            contentDescription = contentDescriptionUnblock,
                            modifier = baseModifierIcon,
                            tint = Color.Green
                        )
                    }
                }
            }

            BaseTooltipAreaForItemUser(text = "Deleting a user") {
                IconButton(
                    onClick = {
                        actionDelete(loginUser)
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

/**
 * Dialog box to confirm user deletion.
 *
 * @param openDialog Opening dialog state.
 * @param actionDelete The action is called when the delete user button is clicked.
 */
@Composable
private fun AlertDialogDeleteUser(
    openDialog: MutableState<Boolean>,
    actionDelete: () -> Unit = {}
) {
    BaseModalPopup(
        stateModal = openDialog
    ) {
        Surface(
            contentColor = contentColorFor(MaterialTheme.colors.surface),
            modifier = Modifier
                .width(360.dp)
                .height(180.dp)
                .shadow(24.dp, RoundedCornerShape(4.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .pointerInput({}) {
                        detectTapGestures(onPress = {
                            // Workaround to disable clicks on Surface background
                            // https://github.com/JetBrains/compose-jb/issues/2581
                        })
                    },
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Are you sure you want to delete the user?"
                )
                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .pointerHoverIcon(PointerIcon.Hand),
                        onClick = actionDelete,
                        colors = ButtonDefaults.textButtonColors(
                            backgroundColor = Color.Red,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Delete")
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .pointerHoverIcon(PointerIcon.Hand),
                        onClick = {
                            openDialog.value = false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}
