package com.zer0s2m.creeptenuous.desktop.ui.screens.dashboard

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.v2.ScrollbarAdapter
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zer0s2m.creeptenuous.desktop.common.dto.CommentFileObject
import com.zer0s2m.creeptenuous.desktop.common.dto.FileObject
import com.zer0s2m.creeptenuous.desktop.common.dto.ManagerFileObject
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.common.utils.colorConvertHexToRgb
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLoader
import com.zer0s2m.creeptenuous.desktop.core.validation.NotEmptyValidator
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveFileObject
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveUser
import com.zer0s2m.creeptenuous.desktop.ui.components.CartCommentForFileObject
import com.zer0s2m.creeptenuous.desktop.ui.components.IconButtonAdd
import com.zer0s2m.creeptenuous.desktop.ui.components.ModalPopup
import com.zer0s2m.creeptenuous.desktop.ui.components.TextFieldAdvanced
import com.zer0s2m.creeptenuous.desktop.ui.components.base.BaseFormState
import com.zer0s2m.creeptenuous.desktop.ui.components.forms.Form
import com.zer0s2m.creeptenuous.desktop.ui.components.forms.FormState
import com.zer0s2m.creeptenuous.desktop.ui.misc.Colors
import com.zer0s2m.creeptenuous.desktop.ui.screens.Dashboard
import com.zer0s2m.creeptenuous.desktop.ui.screens.common.DropdownMenuSelectColor
import com.zer0s2m.creeptenuous.desktop.ui.screens.common.InputSelectColor
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Width of category selection area from list
 */
@get:ReadOnlyComposable
private val baseWidthColumnSelectItem: Dp get() = 328.dp

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
    ModalPopup(
        stateModal = expandedState,
        modifierLayout = Modifier
            .width(360.dp)
            .height(188.dp)
    ) {
        Text(
            text = "Set category in file object",
            fontSize = 20.sp,
            modifier = Modifier
                .padding(bottom = 20.dp)
        )

        val expandedStateDropDownMenu: MutableState<Boolean> = remember { mutableStateOf(false) }
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
            modifier = Modifier.padding(top = 12.dp)
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
                                Screen.DASHBOARD_SCREEN, "currentFileObjectSetProperty"
                            )
                        ) {
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
    ModalPopup(
        stateModal = expandedState,
        modifierLayout = Modifier
            .width(360.dp)
            .height(188.dp)
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
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Column {
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
                }
            )
            DropdownMenuSelectColor(
                expandedState = expandedStateDropDownMenu,
                modifier = Modifier.width(baseWidthColumnSelectItem),
                action = { colorStr, color, colorId ->
                    expandedStateDropDownMenu.value = false
                    currentColor.value = color
                    isSetColor.value = true
                    ContextScreen.set(Screen.DASHBOARD_SCREEN, "colorEditFileObject", colorStr)
                    ContextScreen.set(Screen.DASHBOARD_SCREEN, "colorIdEditFileObject", colorId)
                }
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 12.dp)
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
                                Screen.DASHBOARD_SCREEN, "currentFileObjectSetProperty"
                            )
                        ) {
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

/**
 * Modal window for creating a file object of type - directory
 *
 * @param expandedState Modal window states
 */
@Composable
internal fun PopupCreateFileObjectTypeDirectory(
    expandedState: MutableState<Boolean>,
) {
    ModalPopup(
        stateModal = expandedState,
        modifierLayout = Modifier
            .width(360.dp)
            .height(320.dp)
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
                    modifier = Modifier.fillMaxWidth()
                )
            )
        )
        Spacer(modifier = Modifier.padding(top = 16.dp))
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
        Spacer(modifier = Modifier.padding(top = 16.dp))
        Column {
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
                }
            )
            DropdownMenuSelectColor(
                expandedState = expandedStateSelectColor,
                modifier = Modifier.width(baseWidthColumnSelectItem),
                action = { colorStr, color, colorId ->
                    expandedStateSelectColor.value = false
                    isSetColor.value = true
                    currentColor.value = color
                    colorStrState.value = colorStr
                    colorIdState.value = colorId
                }
            )
        }
        Spacer(modifier = Modifier.padding(top = 12.dp))
        Row(horizontalArrangement = Arrangement.Center) {
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
    ModalPopup(
        stateModal = expandedState,
        modifierLayout = Modifier
            .width(360.dp)
            .height(180.dp)
    ) {
        val stateFormRenameFileObject: BaseFormState = FormState()
        val currentFileObjectSystemName: String = ContextScreen.get(
            Screen.DASHBOARD_SCREEN, "currentFileObjectSetProperty"
        )
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
        Spacer(modifier = Modifier.padding(top = 16.dp))
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
        Spacer(modifier = Modifier.padding(top = 12.dp))
        Row(horizontalArrangement = Arrangement.Center) {
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
    ModalPopup(
        stateModal = expandedState,
        modifierLayout = Modifier
            .width(360.dp)
            .height(180.dp),
        onDismissRequest = onDismissRequest
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
        Spacer(modifier = Modifier.padding(top = 16.dp))
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
        Spacer(modifier = Modifier.padding(top = 12.dp))
        Row(horizontalArrangement = Arrangement.Center) {
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

/**
 * Render the contents of a modal window to show the comments of a file object.
 *
 * @param comments Comments for file objects.
 * @param expandedStateModelInteractiveComment Current state of the modal
 * [PopupInteractionCommentFileObject] rename file object.
 */
@Composable
@Suppress("SameParameterValue")
internal fun PopupContentCommentsInFileObjectModal(
    comments: SnapshotStateList<CommentFileObject>,
    expandedStateModelInteractiveComment: MutableState<Boolean>
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "File object comments",
            color = Colors.TEXT.color,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(12.dp))
        IconButtonAdd(
            onClick = {
                expandedStateModelInteractiveComment.value = true
                val currentFileObject: String = ContextScreen.get(
                    Screen.DASHBOARD_SCREEN,
                    "currentFileObject"
                )

                ContextScreen.set(
                    Screen.DASHBOARD_SCREEN,
                    mapOf(
                        "isEditFileObjectForInteractive" to false,
                        "isCreateFileObjectForInteractive" to true,
                        "currentFileObjectForInteractive" to CommentFileObject(
                            id = null,
                            fileSystemObject = currentFileObject,
                            comment = "",
                            createdAt = LocalDateTime.now()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        )
                    )
                )
            },
            contentDescription = "Add a comment for file object"
        )
    }
    Spacer(modifier = Modifier.height(20.dp))
    LayoutCommentsInFileObject(
        comments = comments,
        expandedStateModelInteractiveComment = expandedStateModelInteractiveComment
    )
}

/**
 * Render the contents of file object comments.
 *
 * @param comments Comments for file objects.
 * @param expandedStateModelInteractiveComment Current state of the modal
 * [PopupInteractionCommentFileObject] rename file object.
 */
@Composable
private fun LayoutCommentsInFileObject(
    comments: SnapshotStateList<CommentFileObject>,
    expandedStateModelInteractiveComment: MutableState<Boolean>
) {
    Box {
        val stateScroll: LazyListState = rememberLazyListState()
        val adapterScroll: ScrollbarAdapter = rememberScrollbarAdapter(scrollState = stateScroll)

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 12.dp),
            state = stateScroll
        ) {
            items(comments.size) { index ->
                val comment: CommentFileObject = comments[index]

                CartCommentForFileObject(
                    text = comment.comment,
                    createdAt = comment.createdAt,
                    actionEdit = {
                        ContextScreen.set(
                            Screen.DASHBOARD_SCREEN,
                            mapOf(
                                "currentFileObjectForInteractive" to comment,
                                "currentIndexFileObjectForInteractive" to index,
                                "isEditFileObjectForInteractive" to true,
                                "isCreateFileObjectForInteractive" to false
                            )
                        )
                        expandedStateModelInteractiveComment.value = true
                    },
                    actionDelete = {
                        ReactiveFileObject.commentsFileSystemObject.removeAtReactive(index)
                        comments.removeAt(index)
                    }
                )

                if (comments.size - 1 != index) {
                    Spacer(
                        modifier = Modifier
                            .height(16.dp)
                    )
                }
            }
        }
        VerticalScrollbar(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .background(Color.Gray.copy(0.8f), RoundedCornerShape(4.dp))
                .fillMaxHeight(),
            adapter = adapterScroll
        )
    }
}

@Composable
internal fun PopupContentInfoFileObjectModal() {
    Text(
        text = "Information",
        color = Colors.TEXT.color,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
}
