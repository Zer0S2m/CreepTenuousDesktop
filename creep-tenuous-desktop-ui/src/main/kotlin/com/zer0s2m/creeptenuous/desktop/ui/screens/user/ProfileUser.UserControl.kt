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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zer0s2m.creeptenuous.desktop.common.dto.User
import com.zer0s2m.creeptenuous.desktop.common.enums.Resources
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLoader
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveCommon
import com.zer0s2m.creeptenuous.desktop.ui.misc.Colors
import com.zer0s2m.creeptenuous.desktop.ui.screens.ProfileUser
import com.zer0s2m.creeptenuous.desktop.ui.screens.base.BaseModalPopup

/**
 * Rendering part of the user profile screen [Screen.PROFILE_USER_MANAGEMENT_SCREEN]
 */
@Composable
fun ProfileUser.ProfileUserControl.render() {
    val openDialogDeleteUser: MutableState<Boolean> = remember { mutableStateOf(false) }
    val openDialogUnblockUser: MutableState<Boolean> = remember { mutableStateOf(false) }
    val openDialogBlockUser: MutableState<Boolean> = remember { mutableStateOf(false) }
    val currentUser: MutableState<User?> = mutableStateOf(null)
    val currentIndexUser: MutableState<Int> = mutableStateOf(-1)
    val users: MutableState<MutableList<User>> = mutableStateOf(ReactiveCommon.systemUsers.toMutableStateList())

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        users.value.forEachIndexed { index, user ->
            itemUser(
                nameUser = user.name,
                loginUser = user.login,
                roleUser = user.role[0].title,
                user.isBlocked,
                actionDelete = { _ ->
                    openDialogDeleteUser.value = true
                    currentUser.value = user
                    currentIndexUser.value = index
                },
                actionBlock = {
                    openDialogBlockUser.value = true
                    currentUser.value = user
                    currentIndexUser.value = index
                },
                actionUnblock = {
                    openDialogUnblockUser.value = true
                    currentUser.value = user
                    currentIndexUser.value = index
                }
            )
        }
    }

    AlertDialogDeleteOrUnblockUser(
        openDialog = openDialogDeleteUser,
        textButtonInAction = "Delete",
        titleDialog = "Are you sure you want to delete the user?",
        action = {
            openDialogDeleteUser.value = false
            currentUser.value?.let {
                users.value.remove(currentUser.value)
                ReactiveCommon.systemUsers.removeReactive(it)
            }
        }
    )
    AlertDialogDeleteOrUnblockUser(
        openDialog = openDialogUnblockUser,
        textButtonInAction = "Unblock",
        titleDialog = "Are you sure you want to unblock the user?",
        action = {
            openDialogUnblockUser.value = false
            currentUser.value?.let {
                if (currentIndexUser.value != -1) {
                    currentUser.value!!.isBlocked = false
                    ReactiveLoader.executionIndependentTrigger(
                        "systemUsers",
                        "unblockSystemUser",
                        currentUser.value
                    )
                    ReactiveCommon.systemUsers[currentIndexUser.value] = currentUser.value!!
                    users.value = ReactiveCommon.systemUsers
                }
            }
        }
    )
    ModalBlockUser(
        expandedState = openDialogBlockUser
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
 * Dialog box to confirm user deletion or unblocking.
 *
 * @param openDialog Opening dialog state.
 * @param action The action is called when the delete or unblock user button is clicked.
 */
@Composable
private fun AlertDialogDeleteOrUnblockUser(
    openDialog: MutableState<Boolean>,
    textButtonInAction: String,
    titleDialog: String,
    action: () -> Unit = {}
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
                    text = titleDialog
                )
                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .pointerHoverIcon(PointerIcon.Hand),
                        onClick = action,
                        colors = ButtonDefaults.textButtonColors(
                            backgroundColor = Color.Red,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = textButtonInAction,
                            textAlign = TextAlign.Center
                        )
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

@Composable
private fun ModalBlockUser(
    expandedState: MutableState<Boolean>
) {
    val heightModal: MutableState<Int> = mutableStateOf(230)

    BaseModalPopup(
        stateModal = expandedState
    ) {
        Surface(
            contentColor = contentColorFor(MaterialTheme.colors.surface),
            modifier = Modifier
                .width(400.dp)
                .height(heightModal.value.dp)
                .shadow(24.dp, RoundedCornerShape(4.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .width(368.dp)
                    .pointerInput({}) {
                        detectTapGestures(onPress = {
                            // Workaround to disable clicks on Surface background
                            // https://github.com/JetBrains/compose-jb/issues/2581
                        })
                    }
            ) {
                Text(
                    text = "Blocking a user",
                    fontSize = 20.sp
                )
                Spacer(
                    modifier = Modifier
                        .padding(top = 12.dp)
                )
                Column {
                    val isActiveBlockCompletely: MutableState<Boolean> = remember { mutableStateOf(true) }
                    val isActiveBlockTemporary: MutableState<Boolean> = remember { mutableStateOf(false) }

                    Row(
                        modifier = Modifier
                            .height(40.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(
                            modifier = Modifier
                                .fillMaxHeight()
                                .pointerHoverIcon(PointerIcon.Hand)
                                .drawBehindIsActive(isActiveBlockCompletely.value)
                                .fillMaxWidth(0.5f),
                            onClick = {
                                isActiveBlockCompletely.value = true
                                isActiveBlockTemporary.value = false
                                heightModal.value = 230
                            }
                        ) {
                            Text(
                                text = "Blocking completely",
                                color = Color.White,
                                fontWeight = FontWeight.Normal
                            )
                        }
                        TextButton(
                            modifier = Modifier
                                .pointerHoverIcon(PointerIcon.Hand)
                                .drawBehindIsActive(isActiveBlockTemporary.value)
                                .fillMaxSize(),
                            onClick = {
                                isActiveBlockCompletely.value = false
                                isActiveBlockTemporary.value = true
                                heightModal.value = 400
                            }
                        ) {
                            Text(
                                text = "Temporary blocking",
                                color = Color.White,
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .padding(top = 20.dp)
                    )
                    if (isActiveBlockCompletely.value && !isActiveBlockTemporary.value) {

                    } else if (!isActiveBlockCompletely.value && isActiveBlockTemporary.value) {
                        Spacer(
                            modifier = Modifier
                                .padding(top = 20.dp)
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .pointerHoverIcon(PointerIcon.Hand),
                            onClick = {

                            },
                            colors = ButtonDefaults.textButtonColors(
                                backgroundColor = Color.Red,
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Block",
                                textAlign = TextAlign.Center
                            )
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
                                expandedState.value = false
                            }
                        ) {
                            Text("Cancel")
                        }
                    }
                }
            }
        }
    }
}

@Stable
private fun Modifier.drawBehindIsActive(
    isActive: Boolean,
    color: Color = Colors.SECONDARY_VARIANT.color
) =
    if (isActive) this.then(this.drawBehind {
        val strokeWidthPx = 3.dp.toPx()
        val verticalOffset = size.height - 2.sp.toPx()
        drawLine(
            color = color,
            strokeWidth = strokeWidthPx,
            start = Offset(0f, verticalOffset),
            end = Offset(size.width, verticalOffset)
        )
    }) else this
