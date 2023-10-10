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
import com.zer0s2m.creeptenuous.desktop.common.dto.CommentFileObject
import com.zer0s2m.creeptenuous.desktop.common.dto.FileObject
import com.zer0s2m.creeptenuous.desktop.common.dto.ManagerFileObject
import com.zer0s2m.creeptenuous.desktop.common.dto.UserCategory
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.common.utils.colorConvertHexToRgb
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLoader
import com.zer0s2m.creeptenuous.desktop.core.validation.NotEmptyValidator
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveFileObject
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveUser
import com.zer0s2m.creeptenuous.desktop.ui.components.TextFieldAdvanced
import com.zer0s2m.creeptenuous.desktop.ui.components.base.BaseFormState
import com.zer0s2m.creeptenuous.desktop.ui.components.forms.Form
import com.zer0s2m.creeptenuous.desktop.ui.components.forms.FormState
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.CircleCategoryBox
import com.zer0s2m.creeptenuous.desktop.ui.screens.Dashboard
import com.zer0s2m.creeptenuous.desktop.ui.screens.base.BaseModalPopup
import com.zer0s2m.creeptenuous.desktop.ui.screens.base.DropdownMenuSelectColor
import com.zer0s2m.creeptenuous.desktop.ui.screens.base.InputSelectColor
import com.zer0s2m.creeptenuous.desktop.ui.screens.base.LayoutDeleteAndOpenInputSelect
import java.util.*

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
    color = Color.Black
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

                val categoryId: Int = ContextScreen.get(Screen.DASHBOARD_SCREEN, "categoryIdEditFileObject")
                val categoryIdState: MutableState<Int> = mutableStateOf(categoryId)

                SelectUserCategoryForFileObject(
                    expandedState = expandedStateDropDownMenu,
                    actionDelete = {
                        ContextScreen.set(Screen.DASHBOARD_SCREEN, "categoryIdEditFileObject", -1)
                        categoryIdState.value = -1
                    },
                    actionDropdownItem = {
                        ContextScreen.set(Screen.DASHBOARD_SCREEN, "categoryIdEditFileObject", it)
                        categoryIdState.value = it
                    },
                    categoryId = categoryIdState
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
                                if (fileObject.systemName == ContextScreen.get(
                                        Screen.DASHBOARD_SCREEN, "currentFileObjectSetProperty")) {
                                    val categoryIdNew: Int = ContextScreen
                                        .get(Screen.DASHBOARD_SCREEN, "categoryIdEditFileObject")
                                    if (categoryIdNew == -1) {
                                        fileObject.categoryId = null
                                    } else {
                                        fileObject.categoryId = categoryIdNew
                                    }
                                    newManagerFileObject.objects[index] = fileObject

                                    ReactiveLoader.executionIndependentTrigger(
                                        "managerFileSystemObjects",
                                        "setCategoryInFileObject",
                                        fileObject.systemName,
                                        fileObject.categoryId
                                    )
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
 * @param categoryId Current selected user category.
 */
@Composable
@OptIn(ExperimentalFoundationApi::class)
internal fun SelectUserCategoryForFileObject(
    expandedState: MutableState<Boolean>,
    actionDropdownItem: (Int) -> Unit,
    actionDelete: () -> Unit = {},
    categoryId: MutableState<Int>
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
            if (categoryId.value != -1) {
                val userCategory = ReactiveUser.customCategories.find {
                    it.id == categoryId.value
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

                val colorEditFileObject: String? = ContextScreen.get(Screen.DASHBOARD_SCREEN, "colorEditFileObject")
                if (!colorEditFileObject.isNullOrEmpty()) {
                    ReactiveUser.userColors.forEach {
                        if (it.color == colorEditFileObject) {
                            val converterColor = colorConvertHexToRgb(colorEditFileObject)
                            currentColor.value = Color(
                                red = converterColor.red,
                                green = converterColor.green,
                                blue = converterColor.blue
                            )
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
                        ContextScreen.set(Screen.DASHBOARD_SCREEN, "colorEditFileObject", null)
                    },
                    content = {
                        DropdownMenuSelectColor(
                            expandedState = expandedStateDropDownMenu,
                            modifier = Modifier
                                .width(baseWidthColumnSelectItem),
                            action = { colorStr, color, colorId ->
                                expandedStateDropDownMenu.value = false
                                currentColor.value = color
                                isSetColor.value = true
                                ContextScreen.set(Screen.DASHBOARD_SCREEN, "colorEditFileObject", colorStr)
                                ContextScreen.set(Screen.DASHBOARD_SCREEN, "colorIdEditFileObject", colorId)
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
                                if (fileObject.systemName == ContextScreen.get(
                                        Screen.DASHBOARD_SCREEN, "currentFileObjectSetProperty")) {
                                    fileObject.color = ContextScreen
                                        .get(Screen.DASHBOARD_SCREEN, "colorEditFileObject")
                                    newManagerFileObject.objects[index] = fileObject

                                    ReactiveLoader.executionIndependentTrigger(
                                        "managerFileSystemObjects",
                                        "setColorInFileObject",
                                        fileObject.systemName,
                                        ContextScreen.get(Screen.DASHBOARD_SCREEN, "colorIdEditFileObject")
                                    )
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

/**
 * Modal window for creating a file object of type - directory
 *
 * @param expandedState Modal window states
 */
@Composable
internal fun PopupCreateFileObjectTypeDirectory(
    expandedState: MutableState<Boolean>,
) {
    BaseModalPopup(
        stateModal = expandedState
    ) {
        Surface(
            contentColor = contentColorFor(MaterialTheme.colors.surface),
            modifier = Modifier
                .width(360.dp)
                .height(330.dp)
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
                val stateFormCreateDirectory: BaseFormState = FormState()

                val expandedStateSelectColor: MutableState<Boolean> = mutableStateOf(false)
                val expandedStateSelectCategory: MutableState<Boolean> = mutableStateOf(false)
                val isSetColor: MutableState<Boolean> = mutableStateOf(false)
                val currentColor: MutableState<Color?> = mutableStateOf(Color(0, 0, 0))
                val categoryId: MutableState<Int> = mutableStateOf(-1)
                val titleNewDirectory: MutableState<String> = mutableStateOf("")
                val colorStrState: MutableState<String?> = mutableStateOf(null)
                val colorIdState: MutableState<Int?> = mutableStateOf(null)

                Text(
                    text = "Create directory",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                )

                // TODO: Make form state for all kinds of components
                Form(
                    state = stateFormCreateDirectory,
                    fields = listOf(
                        TextFieldAdvanced(
                            textField = titleNewDirectory.value,
                            nameField = "title",
                            labelField = "Enter directory title",
                            validators = listOf(
                                NotEmptyValidator()
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    )
                )
                Spacer(
                    modifier = Modifier
                        .padding(top = 16.dp)
                )
                SelectUserCategoryForFileObject(
                    expandedState = expandedStateSelectCategory,
                    actionDropdownItem = {
                        categoryId.value = it
                    },
                    actionDelete = {
                        categoryId.value = -1
                    },
                    categoryId = categoryId
                )
                Spacer(
                    modifier = Modifier
                        .padding(top = 16.dp)
                )
                InputSelectColor(
                    isSetColor = isSetColor,
                    currentColor = currentColor,
                    action = {
                        expandedStateSelectColor.value = true
                    },
                    actionDelete = {
                        isSetColor.value = false
                        currentColor.value = Color(0, 0, 0)
                        colorStrState.value = null
                        colorIdState.value = null
                    },
                    content = {
                        DropdownMenuSelectColor(
                            expandedState = expandedStateSelectColor,
                            modifier = Modifier
                                .width(baseWidthColumnSelectColor),
                            action = { colorStr, color, colorId ->
                                expandedStateSelectColor.value = false
                                isSetColor.value = true
                                currentColor.value = color
                                colorStrState.value = colorStr
                                colorIdState.value = colorId
                            }
                        )
                    }
                )
                Spacer(
                    modifier = Modifier
                        .padding(top = 12.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .pointerHoverIcon(PointerIcon.Hand),
                        onClick = {
                            if (stateFormCreateDirectory.validateForm()) {
                                expandedState.value = false

                                val dataForm = stateFormCreateDirectory.getData()
                                val newFileObjectDirectory = FileObject(
                                    realName = dataForm["title"].toString().trim(),
                                    systemName = UUID.randomUUID().toString(),
                                    isDirectory = true,
                                    isFile = false,
                                    color = colorStrState.value,
                                    categoryId = if (categoryId.value != -1) categoryId.value else null
                                )

                                ReactiveLoader.executionIndependentTrigger(
                                    "managerFileSystemObjects",
                                    "createFileObjectOfTypeDirectory",
                                    newFileObjectDirectory,
                                    colorIdState.value
                                )
                                ReactiveFileObject.managerFileSystemObjects.objects.add(
                                    newFileObjectDirectory
                                )
                                Dashboard.setManagerFileObject(ReactiveFileObject.managerFileSystemObjects)
                            }
                        }
                    ) {
                        Text("Create directory")
                    }
                }
            }
        }
    }
}

/**
 * Width of color selection area from list
 */
@get:ReadOnlyComposable
private val baseWidthColumnSelectColor: Dp get() = 328.dp

/**
 * Modal window for rename a file object.
 *
 * @param expandedState Modal window states.
 * @param actionRename The action is triggered when the file object name changes.
 */
@Composable
internal fun PopupRenameFileObject(
    expandedState: MutableState<Boolean>,
    actionRename: (ManagerFileObject) -> Unit
) {
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
                val stateFormRenameFileObject: BaseFormState = FormState()
                val currentFileObjectSystemName: String = ContextScreen.get(
                    Screen.DASHBOARD_SCREEN, "currentFileObjectSetProperty")
                val currentFileObject: FileObject? = ReactiveFileObject.managerFileSystemObjects.objects.find {
                    it.systemName == currentFileObjectSystemName
                }

                val titleFileObject: MutableState<String> = mutableStateOf("")
                var isFile = false
                currentFileObject?.let {
                    titleFileObject.value = currentFileObject.realName
                    isFile = currentFileObject.isFile
                }

                Text(
                    text = "Rename - ${if (isFile) "File" else "Directory"}",
                    fontSize = 20.sp
                )
                Spacer(
                    modifier = Modifier
                        .padding(top = 16.dp)
                )
                Form(
                    state = stateFormRenameFileObject,
                    fields = listOf(
                        TextFieldAdvanced(
                            textField = titleFileObject.value,
                            nameField = "title",
                            labelField = "Enter title",
                            validators = listOf(
                                NotEmptyValidator()
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    )
                )
                Spacer(
                    modifier = Modifier
                        .padding(top = 12.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .pointerHoverIcon(PointerIcon.Hand),
                        onClick = {
                            if (stateFormRenameFileObject.validateForm()) {
                                currentFileObject?.let {
                                    val dataForm = stateFormRenameFileObject.getData()
                                    val newTitle: String = dataForm["title"].toString().trim()

                                    ReactiveFileObject.managerFileSystemObjects.objects.forEach {
                                        if (it.systemName == currentFileObjectSystemName) {
                                            it.realName = newTitle
                                        }
                                    }

                                    ReactiveLoader.executionIndependentTrigger(
                                        "managerFileSystemObjects",
                                        "renameFileObject",
                                        currentFileObjectSystemName,
                                        newTitle
                                    )

                                    actionRename(ReactiveFileObject.managerFileSystemObjects)

                                    Dashboard.setManagerFileObject(ReactiveFileObject.managerFileSystemObjects)
                                }

                                expandedState.value = false
                            }
                        }
                    ) {
                        Text("Rename")
                    }
                }
            }
        }
    }
}

/**
 * Modal window for creating or editing a comment for a file object.
 *
 * @param expandedState Modal window states.
 * @param onDismissRequest Executes when the user clicks outside the popup.
 * @param actionSave The action is triggered when the comment of a file object is changed.
 */
@Composable
internal fun PopupInteractionCommentFileObject(
    expandedState: MutableState<Boolean>,
    onDismissRequest: () -> Unit = {},
    actionSave: (comment: CommentFileObject) -> Unit
) {
    BaseModalPopup(
        stateModal = expandedState,
        onDismissRequest = onDismissRequest
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
                val stateForm = FormState()
                val comment: CommentFileObject = ContextScreen.get(
                    Screen.DASHBOARD_SCREEN,
                    "currentFileObjectForInteractive"
                )

                Text(
                    text = "Comment",
                    fontSize = 20.sp
                )
                Spacer(
                    modifier = Modifier
                        .padding(top = 16.dp)
                )
                Form(
                    state = stateForm,
                    fields = listOf(
                        TextFieldAdvanced(
                            textField = comment.comment,
                            nameField = "text",
                            labelField = "Enter text",
                            validators = listOf(
                                NotEmptyValidator()
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    )
                )
                Spacer(
                    modifier = Modifier
                        .padding(top = 12.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .pointerHoverIcon(PointerIcon.Hand),
                        onClick = {
                            if (stateForm.validateForm()) {
                                expandedState.value = false

                                val dataForm = stateForm.getData()

                                comment.comment = dataForm["text"].toString().trim()
                                actionSave(comment)
                            }
                        }
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}
