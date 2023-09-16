package com.zer0s2m.creeptenuous.desktop.ui.screens.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zer0s2m.creeptenuous.desktop.common.dto.ManagerFileObject
import com.zer0s2m.creeptenuous.desktop.common.dto.UserCategory
import com.zer0s2m.creeptenuous.desktop.common.utils.colorConvertHexToRgb
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveFileObject
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveUser
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.CircleCategoryBox
import com.zer0s2m.creeptenuous.desktop.ui.screens.Dashboard
import com.zer0s2m.creeptenuous.desktop.ui.screens.base.BaseModalPopup
import com.zer0s2m.creeptenuous.desktop.ui.screens.base.DropdownMenuSelectColor
import com.zer0s2m.creeptenuous.desktop.ui.screens.base.InputSelectColor
import com.zer0s2m.creeptenuous.desktop.ui.screens.base.LayoutDeleteAndOpenInputSelect

/**
 * Base title for file object category
 *
 * @param text The text to be displayed
 * @param size Count objects
 */
@Composable
internal fun TitleCategoryFileObject(text: String, size: Int = 0): Unit = Text(
    text = "$text ($size)",
    fontWeight = FontWeight.SemiBold,
    color = Color.Black,
    modifier = Modifier
        .padding(bottom = 12.dp)
)

/**
 * Modal window for selecting a custom category and subsequent binding to a file object
 *
 * @param expandedState Modal window states
 * @param actionSetCategory Event occurs when setting a custom category to a file object
 */
@Composable
internal fun PopupSetUserCategoryInFileObject(
    expandedState: MutableState<Boolean>,
    actionSetCategory: (ManagerFileObject) -> Unit
) {
    val expandedStateDropDownMenu: MutableState<Boolean> = remember { mutableStateOf(false) }

    BaseModalPopup(
        stateModal = expandedState
    ) {
        Surface(
            contentColor = contentColorFor(MaterialTheme.colors.surface),
            modifier = Modifier
                .width(360.dp)
                .height(188.dp)
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
            ) {
                Text(
                    text = "Set category in file object",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                )

                SelectUserCategoryForFileObject(
                    expandedState = expandedStateDropDownMenu,
                    actionDelete = {
                        Dashboard.setCategoryIdEditFileObject(categoryId = -1)
                    },
                    actionDropdownItem = {
                        Dashboard.setCategoryIdEditFileObject(categoryId = it)
                    }
                )

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(top = 12.dp)
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .pointerHoverIcon(PointerIcon.Hand),
                        onClick = {
                            expandedState.value = false

                            val newManagerFileObject = ManagerFileObject(
                                systemParents = ReactiveFileObject.managerFileSystemObjects.systemParents,
                                level = ReactiveFileObject.managerFileSystemObjects.level,
                                objects = ReactiveFileObject.managerFileSystemObjects.objects
                            )

                            newManagerFileObject.objects.forEachIndexed { index, fileObject ->
                                if (fileObject.systemName == Dashboard.getCurrentFileObjectSetCategory()) {
                                    if (Dashboard.getCategoryIdEditFileObject() == -1) {
                                        fileObject.categoryId = null
                                    } else {
                                        fileObject.categoryId = Dashboard.getCategoryIdEditFileObject()
                                    }
                                    newManagerFileObject.objects[index] = fileObject
                                }
                            }

                            ReactiveFileObject.managerFileSystemObjects = newManagerFileObject
                            actionSetCategory(ReactiveFileObject.managerFileSystemObjects)
                            Dashboard.setManagerFileObject(ReactiveFileObject.managerFileSystemObjects)
                        }
                    ) {
                        Text("Set category")
                    }
                }
            }
        }
    }
}

/**
 * Component for selecting a custom category.
 *
 * @param expandedState Modal window states.
 * @param actionDropdownItem Call an action when an element is clicked [DropdownMenuItem].
 * @param actionDelete Call an action when deleting a category.
 */
@Composable
@OptIn(ExperimentalFoundationApi::class)
internal fun SelectUserCategoryForFileObject(
    expandedState: MutableState<Boolean>,
    actionDropdownItem: (Int) -> Unit,
    actionDelete: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .onClick {
                expandedState.value = true
            }
            .border(0.5.dp, MaterialTheme.colors.secondary, RoundedCornerShape(4.dp))
            .pointerHoverIcon(PointerIcon.Hand)
    ) {
        Row(
            modifier = Modifier
                .pointerHoverIcon(PointerIcon.Hand)
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (Dashboard.getCategoryIdEditFileObject() != -1) {
                val userCategory = ReactiveUser.customCategories.find {
                    it.id == Dashboard.getCategoryIdEditFileObject()
                }
                if (userCategory != null) {
                    RenderLayoutUserCategory(userCategory)
                }
            } else {
                Text(
                    text = "Category...",
                    color = Color(255, 255, 255, 160),
                )
            }

            LayoutDeleteAndOpenInputSelect(
                actionDelete = actionDelete
            )
        }

        DropdownMenu(
            expanded = expandedState.value,
            onDismissRequest = {
                expandedState.value = false
            },
            modifier = Modifier
                .width(baseWidthColumnSelectItem)
        ) {
            ReactiveUser.customCategories.forEach {
                DropdownMenuItem(
                    onClick = {
                        expandedState.value = false
                        it.id?.let { id -> actionDropdownItem(id) }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .pointerHoverIcon(PointerIcon.Hand),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    RenderLayoutUserCategory(userCategory = it)
                }
            }
        }
    }
}

/**
 * Width of category selection area from list
 */
@get:ReadOnlyComposable
private val baseWidthColumnSelectItem: Dp get() = 328.dp

/**
 * Component responsible for minimal information about the user category
 *
 * @param userCategory User category information
 */
@Composable
private fun RenderLayoutUserCategory(userCategory: UserCategory) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (userCategory.color != null) {
            CircleCategoryBox(userCategory.color!!, 16.dp)
        }
        Text(
            text = userCategory.title,
            modifier = if (userCategory.color != null) Modifier
                .padding(start = 8.dp) else Modifier
        )
    }
}

/**
 * Modal window for selecting a custom color and subsequent binding to a file object
 *
 * @param expandedState Modal window states
 * @param actionSetColor Event occurs when setting a custom color to a file object
 */
@Composable
internal fun PopupSetUserColorInFileObject(
    expandedState: MutableState<Boolean>,
    actionSetColor: (ManagerFileObject) -> Unit
) {
    BaseModalPopup(
        stateModal = expandedState
    ) {
        Surface(
            contentColor = contentColorFor(MaterialTheme.colors.surface),
            modifier = Modifier
                .width(360.dp)
                .height(188.dp)
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
            ) {
                val expandedStateDropDownMenu: MutableState<Boolean> = remember { mutableStateOf(false) }
                val isSetColor: MutableState<Boolean> = remember { mutableStateOf(false) }
                val currentColor: MutableState<Color?> = remember { mutableStateOf(null) }

                if (Dashboard.getColorEditFileObject() != null
                    && Dashboard.getColorEditFileObject()?.isNotEmpty() == true) {
                    ReactiveUser.userColors.forEach {
                        if (it.color == Dashboard.getColorEditFileObject()) {
                            val converterColor = Dashboard.getColorEditFileObject()
                                ?.let { it1 -> colorConvertHexToRgb(it1) }
                            if (converterColor != null) {
                                currentColor.value = Color(
                                    red = converterColor.red,
                                    green = converterColor.green,
                                    blue = converterColor.blue
                                )
                            }
                            isSetColor.value = true
                        }
                    }
                }

                Text(
                    text = "Set color in file object",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                )

                InputSelectColor(
                    isSetColor = isSetColor,
                    currentColor = currentColor,
                    action = {
                        expandedStateDropDownMenu.value = true
                    },
                    actionDelete = {
                        currentColor.value = null
                        isSetColor.value = false
                        Dashboard.setColorEditFileObject()
                    },
                    content = {
                        DropdownMenuSelectColor(
                            expandedState = expandedStateDropDownMenu,
                            modifier = Modifier
                                .width(baseWidthColumnSelectItem),
                            action = { colorStr, color ->
                                expandedStateDropDownMenu.value = false
                                currentColor.value = color
                                isSetColor.value = true
                                Dashboard.setColorEditFileObject(colorStr)
                            }
                        )
                    }
                )

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(top = 12.dp)
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .pointerHoverIcon(PointerIcon.Hand),
                        onClick = {
                            expandedState.value = false

                            val newManagerFileObject = ManagerFileObject(
                                systemParents = ReactiveFileObject.managerFileSystemObjects.systemParents,
                                level = ReactiveFileObject.managerFileSystemObjects.level,
                                objects = ReactiveFileObject.managerFileSystemObjects.objects
                            )

                            newManagerFileObject.objects.forEachIndexed { index, fileObject ->
                                if (fileObject.systemName == Dashboard.getCurrentFileObjectSetCategory()) {
                                    fileObject.color = Dashboard.getColorEditFileObject()
                                    newManagerFileObject.objects[index] = fileObject
                                }
                            }

                            ReactiveFileObject.managerFileSystemObjects = newManagerFileObject
                            actionSetColor(ReactiveFileObject.managerFileSystemObjects)
                            Dashboard.setManagerFileObject(ReactiveFileObject.managerFileSystemObjects)
                        }
                    ) {
                        Text("Set color")
                    }
                }
            }
        }
    }
}