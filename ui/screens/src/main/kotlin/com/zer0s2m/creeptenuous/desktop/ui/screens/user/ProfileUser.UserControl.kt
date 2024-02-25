package com.zer0s2m.creeptenuous.desktop.ui.screens.user

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zer0s2m.creeptenuous.desktop.common.dto.User
import com.zer0s2m.creeptenuous.desktop.common.enums.Resources
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLoader
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveCommon
import com.zer0s2m.creeptenuous.desktop.ui.components.CardUserControl
import com.zer0s2m.creeptenuous.desktop.ui.components.ModalPopup
import com.zer0s2m.creeptenuous.desktop.ui.components.ModalSelectDate
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.Colors
import com.zer0s2m.creeptenuous.desktop.ui.screens.ProfileUser
import kotlinx.coroutines.launch
import java.util.*

/**
 * Rendering part of the user profile screen [Screen.PROFILE_USER_MANAGEMENT_SCREEN]
 */
@Composable
@Suppress("UnusedReceiverParameter")
fun ProfileUser.ProfileUserControl.render() {
    val scope = rememberCoroutineScope()
    val openDialogDeleteUser: MutableState<Boolean> = remember { mutableStateOf(false) }
    val openDialogUnblockUser: MutableState<Boolean> = remember { mutableStateOf(false) }
    val openDialogBlockUser: MutableState<Boolean> = remember { mutableStateOf(false) }
    val openDialogBlockUserSelectDate: MutableState<Boolean> = remember { mutableStateOf(false) }
    val currentUser: MutableState<User?> = mutableStateOf(null)
    val currentIndexUser: MutableState<Int> = mutableStateOf(-1)
    val users: MutableState<MutableList<User>> = mutableStateOf(ReactiveCommon.systemUsers.toMutableStateList())
    val selectDateStartUserBlock: MutableState<Date?> = mutableStateOf(null)
    val selectDateEndUserBlock: MutableState<Date?> = mutableStateOf(null)
    val currentDateUserBlock: MutableState<Date> = mutableStateOf(Date())
    val isErrorValidateDateEndBlockUser: MutableState<Boolean> = mutableStateOf(false)

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
    ) {
        itemsIndexed(users.value) { index: Int, user: User ->
            var isBLocked = false
            if (user.isBlocked || user.isTemporarilyBlocked) {
                isBLocked = true
            }

            // TODO: Bug - fix changing the button to block or unlock the user

            CardUserControl(
                nameUser = user.name,
                loginUser = user.login,
                roleUser = user.role[0].title,
                isBlocked = isBLocked,
                avatar = user.avatar,
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
                scope.launch {
                    ReactiveCommon.systemUsers.removeReactive(it)
                    users.value.remove(currentUser.value)
                }
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
                    scope.launch {
                        currentUser.value!!.isBlocked = false
                        currentUser.value!!.isTemporarilyBlocked = false
                        ReactiveCommon.systemUsers[currentIndexUser.value] = currentUser.value!!
                        users.value = ReactiveCommon.systemUsers

                        ReactiveLoader.executionIndependentTrigger(
                            "systemUsers",
                            "unblockSystemUser",
                            currentUser.value
                        )
                    }

                    ContextScreen.clearScreen(Screen.PROFILE_USER_MANAGEMENT_SCREEN)
                }
            }
        }
    )

    ModalBlockUser(
        expandedState = openDialogBlockUser,
        expandedStateModalSelectDate = openDialogBlockUserSelectDate,
        selectDateStart = selectDateStartUserBlock,
        selectDateEnd = selectDateEndUserBlock,
        currentDateUserBlock = currentDateUserBlock,
        isErrorValidateDateEndBlockUser = isErrorValidateDateEndBlockUser,
        actionBlock = {
            openDialogBlockUser.value = false

            fun final() {
                if (isBlockUserCompletely) {
                    currentUser.value!!.isBlocked = true
                }
                if (isBlockUserTemporary) {
                    currentUser.value!!.isTemporarilyBlocked = true
                }

                if (isBlockUserCompletely || isBlockUserTemporary) {
                    ReactiveCommon.systemUsers[currentIndexUser.value] = currentUser.value!!
                    users.value = ReactiveCommon.systemUsers
                }

                ContextScreen.clearScreen(Screen.PROFILE_USER_MANAGEMENT_SCREEN)
            }

            if (isBlockUserCompletely && !isBlockUserTemporary) {
                scope.launch {
                    ReactiveLoader.executionIndependentTrigger(
                        "systemUsers",
                        "blockSystemUserCompletely",
                        currentUser.value
                    )
                }

                final()
            } else if (!isBlockUserCompletely && isBlockUserTemporary) {
                val existsStartDateBlockUser: Boolean = ContextScreen.containsValueByKey(
                    Screen.PROFILE_USER_MANAGEMENT_SCREEN, "startDateBlockUser"
                )
                val existsEndDateBlockUser: Boolean = ContextScreen.containsValueByKey(
                    Screen.PROFILE_USER_MANAGEMENT_SCREEN, "endDateBlockUser"
                )

                if (!existsStartDateBlockUser && existsEndDateBlockUser) {
                    scope.launch {
                        ReactiveLoader.executionIndependentTrigger(
                            "systemUsers",
                            "blockSystemUserTemporary",
                            null,
                            selectDateEndUserBlock.value,
                            currentUser.value
                        )
                    }

                    final()
                } else if (existsStartDateBlockUser && existsEndDateBlockUser) {
                    scope.launch {
                        ReactiveLoader.executionIndependentTrigger(
                            "systemUsers",
                            "blockSystemUserTemporary",
                            selectDateStartUserBlock.value,
                            selectDateEndUserBlock.value,
                            currentUser.value
                        )
                    }

                    final()
                }
            }
        }
    )
    ModalSelectDate(
        initDate = currentDateUserBlock,
        expandedState = openDialogBlockUserSelectDate,
        onDismissRequest = {
            openDialogBlockUserSelectDate.value = false

            if (!isBlockUserCompletely && isBlockUserTemporary) {
                if (isStartDateBlockUser) {
                    selectDateStartUserBlock.value = null
                    ContextScreen.clearValueByKey(
                        Screen.PROFILE_USER_MANAGEMENT_SCREEN,
                        "startDateBlockUser"
                    )
                } else if (isEndDateBlockUser) {
                    selectDateEndUserBlock.value = null
                    ContextScreen.clearValueByKey(
                        Screen.PROFILE_USER_MANAGEMENT_SCREEN,
                        "endDateBlockUser"
                    )
                }
            }
        },
        onDateSelect = {
            openDialogBlockUserSelectDate.value = false

            if ((isStartDateBlockUser && !isEndDateBlockUser) && isBlockUserTemporary) {
                selectDateStartUserBlock.value = it
                startDateBlockUser = it
            } else if ((!isStartDateBlockUser && isEndDateBlockUser) && isBlockUserTemporary) {
                selectDateEndUserBlock.value = it
                endDateBlockUser = it
                if (isErrorValidateDateEndBlockUser.value) {
                    isErrorValidateDateEndBlockUser.value = false
                }
            }

            currentDateUserBlock.value = it
        }
    )
}

/**
 * Text used by accessibility services to describe what this image represents
 */
@get:ReadOnlyComposable
private val contentDescriptionUnblock: String get() = "User unlock icon"

/**
 * optional [Modifier] for this [Icon]
 */
@Stable
private val baseModifierIcon: Modifier
    get() = Modifier
        .size(24.dp)
        .pointerHoverIcon(icon = PointerIcon.Hand)



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
    ModalPopup(
        stateModal = openDialog,
        modifierLayout = Modifier
            .width(360.dp)
            .height(180.dp)
    ) {
        Text(text = titleDialog)
        Spacer(modifier = Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.Center) {
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
        Row(horizontalArrangement = Arrangement.Center) {
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

/**
 * Modal window for blocking a user.
 *
 * @param expandedState Modal window states.
 * @param expandedStateModalSelectDate Modal window states [ModalSelectDate].
 * @param selectDateStart Blocking start date [ModalSelectDate].
 * @param selectDateEnd Blocking end date [ModalSelectDate].
 * @param currentDateUserBlock Current selected date. [selectDateStart] or [selectDateEnd].
 */
@Composable
private fun ModalBlockUser(
    expandedState: MutableState<Boolean>,
    expandedStateModalSelectDate: MutableState<Boolean>,
    selectDateStart: MutableState<Date?>,
    selectDateEnd: MutableState<Date?>,
    currentDateUserBlock: MutableState<Date>,
    isErrorValidateDateEndBlockUser: MutableState<Boolean>,
    actionBlock: () -> Unit
) {
    val heightModal: MutableState<Int> = remember { mutableStateOf(225) }
    val isActiveBlockCompletely: MutableState<Boolean> = remember { mutableStateOf(true) }
    val isActiveBlockTemporary: MutableState<Boolean> = remember { mutableStateOf(false) }

    ModalPopup(
        stateModal = expandedState,
        modifierLayout = Modifier
            .width(400.dp)
            .height(heightModal.value.dp)
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
                        heightModal.value = 225
                        isBlockUserCompletely = true
                        isBlockUserTemporary = false
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
                        heightModal.value = 410
                        isBlockUserCompletely = false
                        isBlockUserTemporary = true
                    }
                ) {
                    Text(
                        text = "Temporary blocking",
                        color = Color.White,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
            if (!isActiveBlockCompletely.value && isActiveBlockTemporary.value) {
                Spacer(
                    modifier = Modifier
                        .padding(top = 20.dp)
                )
                TextFieldSelectDate(
                    textDate = if (selectDateStart.value != null) selectDateStart.value.toString()
                    else "Select date...",
                    label = "Blocking start date (if the date is not specified, " +
                            "the current one is taken)",
                    actionOpen = {
                        expandedStateModalSelectDate.value = true

                        ContextScreen.set(
                            Screen.PROFILE_USER_MANAGEMENT_SCREEN,
                            hashMapOf(
                                "isStartDateBlockUser" to true,
                                "isEndDateBlockUser" to false
                            )
                        )

                        if (ContextScreen.containsValueByKey(
                                Screen.PROFILE_USER_MANAGEMENT_SCREEN, "startDateBlockUser"
                            )
                        ) {
                            currentDateUserBlock.value = startDateBlockUser
                        } else {
                            currentDateUserBlock.value = Date()
                        }
                    },
                    isSelected = selectDateStart.value != null
                )
                Spacer(
                    modifier = Modifier
                        .padding(top = 12.dp)
                )
                TextFieldSelectDate(
                    textDate = if (selectDateEnd.value != null) selectDateEnd.value.toString()
                    else "Select date...",
                    label = "Blocking end date",
                    isError = isErrorValidateDateEndBlockUser.value,
                    labelError = "Please indicate the date",
                    actionOpen = {
                        expandedStateModalSelectDate.value = true
                        isStartDateBlockUser = false
                        isEndDateBlockUser = true

                        if (ContextScreen.containsValueByKey(
                                Screen.PROFILE_USER_MANAGEMENT_SCREEN, "endDateBlockUser"
                            )
                        ) {
                            currentDateUserBlock.value = endDateBlockUser
                        } else {
                            currentDateUserBlock.value = Date()
                        }
                    },
                    isSelected = selectDateEnd.value != null
                )
            }
            Spacer(
                modifier = Modifier
                    .padding(top = 20.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .pointerHoverIcon(PointerIcon.Hand),
                    onClick = {
                        fun final() {
                            expandedState.value = false
                            isBlockUserCompletely = isActiveBlockCompletely.value
                            isBlockUserTemporary = isActiveBlockTemporary.value
                            actionBlock()
                        }

                        if (isActiveBlockCompletely.value && !isActiveBlockTemporary.value) {
                            final()
                        } else if (!isActiveBlockCompletely.value && isActiveBlockTemporary.value) {
                            val endDateBlockUser: Boolean = ContextScreen.containsValueByKey(
                                Screen.PROFILE_USER_MANAGEMENT_SCREEN,
                                "endDateBlockUser"
                            )
                            if (!isErrorValidateDateEndBlockUser.value && endDateBlockUser) {
                                final()
                            }

                            if (!endDateBlockUser) {
                                isErrorValidateDateEndBlockUser.value = true
                            }
                        }
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

/**
 * Will the user be permanently blocked.
 */
private var isBlockUserCompletely: Boolean
    get() = ContextScreen.get(
        Screen.PROFILE_USER_MANAGEMENT_SCREEN,
        "isBlockUserCompletely"
    )
    set(value) {
        ContextScreen.set(
            Screen.PROFILE_USER_MANAGEMENT_SCREEN,
            "isBlockUserCompletely",
            value
        )
    }

/**
 * Will the user be blocked for a period of time.
 */
private var isBlockUserTemporary: Boolean
    get() = ContextScreen.get(
        Screen.PROFILE_USER_MANAGEMENT_SCREEN,
        "isBlockUserTemporary"
    )
    set(value) = ContextScreen.set(
        Screen.PROFILE_USER_MANAGEMENT_SCREEN,
        "isBlockUserTemporary",
        value
    )

/**
 * Is the start date for blocking the user selected.
 */
private var isStartDateBlockUser: Boolean
    get() = ContextScreen.get(
        Screen.PROFILE_USER_MANAGEMENT_SCREEN,
        "isStartDateBlockUser"
    )
    set(value) = ContextScreen.set(
        Screen.PROFILE_USER_MANAGEMENT_SCREEN,
        "isStartDateBlockUser",
        value
    )

/**
 * Is the user's end date selected.
 */
private var isEndDateBlockUser: Boolean
    get() = ContextScreen.get(
        Screen.PROFILE_USER_MANAGEMENT_SCREEN,
        "isEndDateBlockUser"
    )
    set(value) = ContextScreen.set(
        Screen.PROFILE_USER_MANAGEMENT_SCREEN,
        "isEndDateBlockUser",
        value
    )

/**
 * Start date of user blocking.
 */
private var startDateBlockUser: Date
    get() = ContextScreen.get(
        Screen.PROFILE_USER_MANAGEMENT_SCREEN,
        "startDateBlockUser"
    )
    set(value) = ContextScreen.set(
        Screen.PROFILE_USER_MANAGEMENT_SCREEN,
        "startDateBlockUser",
        value
    )

/**
 * End date for user blocking.
 */
private var endDateBlockUser: Date
    get() = ContextScreen.get(
        Screen.PROFILE_USER_MANAGEMENT_SCREEN,
        "endDateBlockUser"
    )
    set(value) = ContextScreen.set(
        Screen.PROFILE_USER_MANAGEMENT_SCREEN,
        "endDateBlockUser",
        value
    )

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

/**
 * Text component for date picker.
 *
 * @param textDate Initial text when opening.
 * @param label Small text field for hint.
 * @param isSelected Is the date selected.
 * @param isError Is the date selected.
 * @param actionOpen The action occurs when the date selection window opens.
 */
@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun TextFieldSelectDate(
    textDate: String = "Select date...",
    label: String,
    isSelected: Boolean = false,
    isError: Boolean = false,
    labelError: String = "",
    actionOpen: () -> Unit
) {
    Row(
        modifier = Modifier
            .pointerHoverIcon(PointerIcon.Hand)
            .fillMaxWidth()
            .onClick(onClick = actionOpen)
            .border(
                width = 0.5.dp,
                color = if (!isError) MaterialTheme.colors.secondary else MaterialTheme.colors.error,
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .pointerHoverIcon(PointerIcon.Hand)
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = textDate,
                color = if (isSelected) Color.Unspecified
                else if (isError) MaterialTheme.colors.error.copy(0.60f)
                else Color(255, 255, 255, 160)
            )

            Icon(
                painter = painterResource(resourcePath = Resources.ICON_DATE.path),
                contentDescription = contentDescriptionIconDate,
                tint = MaterialTheme.colors.secondaryVariant,
                modifier = Modifier
                    .size(24.dp)
            )
        }
    }
    Spacer(
        modifier = Modifier
            .padding(top = 4.dp)
    )
    Text(
        text = if (!isError) label else labelError,
        fontSize = 12.sp,
        color = if (!isError) Color(255, 255, 255, 160)
        else MaterialTheme.colors.error,
    )
}

/**
 * Text used by accessibility services to describe what this image represents
 */
@get:ReadOnlyComposable
private val contentDescriptionIconDate: String get() = "Select date"
