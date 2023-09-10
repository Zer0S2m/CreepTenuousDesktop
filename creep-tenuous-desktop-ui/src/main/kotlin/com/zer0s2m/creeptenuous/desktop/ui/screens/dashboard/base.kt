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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zer0s2m.creeptenuous.desktop.common.dto.ManagerFileObject
import com.zer0s2m.creeptenuous.desktop.common.dto.UserCategory
import com.zer0s2m.creeptenuous.desktop.common.enums.Resources
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveFileObject
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveUser
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.CircleCategoryBox
import com.zer0s2m.creeptenuous.desktop.ui.screens.Dashboard
import com.zer0s2m.creeptenuous.desktop.ui.screens.base.BaseModalPopup

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
 */
@Composable
internal fun PopupSetUserCategoryInFileObject(
    expandedState: MutableState<Boolean>
) {
    val expandedStateDropDownMenu: MutableState<Boolean> = remember { mutableStateOf(false) }

    BaseModalPopup(
        stateModal = expandedState
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
            ) {
                Text(
                    text = "Set category in file object",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                )

                SelectUserCategoryForFileObject(
                    expandedState = expandedStateDropDownMenu,
                    actionDropdownItem = {
                        Dashboard.setCategoryIdEditFileObject(categoryId = it)
                    },
                    actionSet = {
                        expandedState.value = false

                        val newManagerFileObject = ManagerFileObject(
                            systemParents = ReactiveFileObject.managerFileSystemObjects.systemParents,
                            level = ReactiveFileObject.managerFileSystemObjects.level,
                            objects = ReactiveFileObject.managerFileSystemObjects.objects
                        )

                        newManagerFileObject.objects.forEachIndexed { index, fileObject ->
                            if (fileObject.systemName == Dashboard.getCurrentFileObjectSetCategory()) {
                                fileObject.categoryId = Dashboard.getCategoryIdEditFileObject()
                                newManagerFileObject.objects[index] = fileObject
                            }
                        }

                        ReactiveFileObject.managerFileSystemObjects = newManagerFileObject
                        Dashboard.setManagerFileObject(newManagerFileObject)
                    }
                )
            }
        }
    }
}

/**
 * Component for selecting a custom category
 *
 * @param expandedState Modal window states
 * @param actionDropdownItem Call an action when an element is clicked [DropdownMenuItem]
 * @param actionSet Will be called when the user clicks the [Button]
 */
@Composable
@OptIn(ExperimentalFoundationApi::class)
internal fun SelectUserCategoryForFileObject(
    expandedState: MutableState<Boolean>,
    actionDropdownItem: (Int) -> Unit,
    actionSet: () -> Unit
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

            Icon(
                painter = painterResource(resourcePath = Resources.ICON_ARROW.path),
                contentDescription = contentDescriptionIconArrow,
                tint = Color.White,
                modifier = Modifier
                    .size(16.dp)
            )
        }

        DropdownMenu(
            expanded = expandedState.value,
            onDismissRequest = {
                expandedState.value = false
            },
            modifier = Modifier
                .width(baseWidthColumnSelectCategory)
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

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(top = 12.dp)
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .pointerHoverIcon(PointerIcon.Hand),
            onClick = actionSet
        ) {
            Text("Set category")
        }
    }
}

/**
 * Width of category selection area from list
 */
@get:ReadOnlyComposable
private val baseWidthColumnSelectCategory: Dp get() = 328.dp

/**
 * Text used by accessibility services to describe what this image represents
 */
@get:ReadOnlyComposable
private val contentDescriptionIconArrow: String get() = "Open categories list"

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
