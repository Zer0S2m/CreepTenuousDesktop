package com.zer0s2m.creeptenuous.desktop.ui.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zer0s2m.creeptenuous.desktop.common.dto.FileObject
import com.zer0s2m.creeptenuous.desktop.common.dto.ManagerFileObject
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLoader
import com.zer0s2m.creeptenuous.desktop.reactive.actions.ActionDownloadFileOrDirectory
import com.zer0s2m.creeptenuous.desktop.reactive.actions.ActionsWalkingThroughDirectories
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveFileObject
import com.zer0s2m.creeptenuous.desktop.ui.components.Avatar
import com.zer0s2m.creeptenuous.desktop.ui.components.CartFileObject
import com.zer0s2m.creeptenuous.desktop.ui.components.FieldSearch
import com.zer0s2m.creeptenuous.desktop.ui.components.IconButtonAdd
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Content on the right side of the main dashboard
 *
 * @param directories file objects with type - directory
 * @param files file objects with type - file
 * @param expandedStateSetCategoryPopup Current state of the modal
 * [PopupSetUserCategoryInFileObject] when setting a custom category
 * @param expandedStateSetColorPopup Current state of the modal
 * [PopupSetUserColorInFileObject] when setting a custom color
 * @param expandedStateModalRenameFileObject Current state of the modal [PopupRenameFileObject] rename file object.
 * @param scaffoldStateCommentFileObject State of this scaffold widget [PopupContentCommentsInFileObjectModal].
 * @param scaffoldStateInfoFileObject State of this scaffold widget [PopupContentInfoFileObjectModal].
 */
@Composable
internal fun RenderLayoutFilesObject(
    scope: CoroutineScope,
    directories: MutableState<MutableList<FileObject>>,
    files: MutableState<MutableList<FileObject>>,
    expandedStateSetCategoryPopup: MutableState<Boolean>,
    expandedStateSetColorPopup: MutableState<Boolean>,
    expandedStateCreateFileObjectTypeDirectory: MutableState<Boolean>,
    expandedStateModalRenameFileObject: MutableState<Boolean>,
    scaffoldStateCommentFileObject: ScaffoldState,
    scaffoldStateInfoFileObject: ScaffoldState
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Column(modifier = Modifier.padding(bottom = 28.dp)) {
            RenderLayoutDirectories(
                scope = scope,
                directories = directories,
                expandedStateSetCategoryPopup = expandedStateSetCategoryPopup,
                expandedStateSetColorPopup = expandedStateSetColorPopup,
                expandedStateCreateFileObjectTypeDirectory = expandedStateCreateFileObjectTypeDirectory,
                expandedStateModalRenameFileObject = expandedStateModalRenameFileObject,
                scaffoldStateCommentFileObject = scaffoldStateCommentFileObject,
                scaffoldStateInfoFileObject = scaffoldStateInfoFileObject
            )
            RenderLayoutFiles(
                scope = scope,
                files = files,
                expandedStateSetCategoryPopup = expandedStateSetCategoryPopup,
                expandedStateModalRenameFileObject = expandedStateModalRenameFileObject,
                scaffoldStateCommentFileObject = scaffoldStateCommentFileObject,
                scaffoldStateInfoFileObject = scaffoldStateInfoFileObject
            )
        }
    }
}

/**
 * Content renderer responsible for file objects with type - category
 *
 * @param scope Defines a scope for new coroutines.
 * @param directories file objects with type - directory
 * @param expandedStateSetCategoryPopup Current state of the modal
 * [PopupSetUserCategoryInFileObject] when setting a custom category
 * @param expandedStateSetColorPopup Current state of the modal
 * [PopupSetUserColorInFileObject] when setting a custom color
 * @param expandedStateModalRenameFileObject Current state of the modal [PopupRenameFileObject] rename file object.
 * @param scaffoldStateCommentFileObject State of this scaffold widget [PopupContentCommentsInFileObjectModal].
 * @param scaffoldStateInfoFileObject State of this scaffold widget [PopupContentInfoFileObjectModal].
 */
@Composable
internal fun RenderLayoutDirectories(
    scope: CoroutineScope,
    directories: MutableState<MutableList<FileObject>>,
    expandedStateSetCategoryPopup: MutableState<Boolean>,
    expandedStateSetColorPopup: MutableState<Boolean>,
    expandedStateCreateFileObjectTypeDirectory: MutableState<Boolean>,
    expandedStateModalRenameFileObject: MutableState<Boolean>,
    scaffoldStateCommentFileObject: ScaffoldState,
    scaffoldStateInfoFileObject: ScaffoldState
) {
    Column(modifier = Modifier.padding(bottom = 28.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TitleCategoryFileObject("Folders", directories.value.size)
            Spacer(modifier = Modifier.width(12.dp))
            IconButtonAdd(
                onClick = {
                    expandedStateCreateFileObjectTypeDirectory.value = true
                },
                contentDescription = "Add file object"
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(top = 12.dp)
        ) {
            items(directories.value.size) { index ->
                val categoryId: Int? = directories.value[index].categoryId
                val color: String? = directories.value[index].color

                CartFileObject(
                    isDirectory = true,
                    isFile = false,
                    text = directories.value[index].realName,
                    color = directories.value[index].color,
                    categoryId = categoryId,
                    actionInfo = {
                        actionInfo(
                            scope = scope,
                            scaffoldStateInfoFileObject = scaffoldStateInfoFileObject,
                            systemName = directories.value[index].systemName
                        )
                    },
                    actionDelete = {
                        scope.launch {
                            directories.value = actionDelete(directories.value[index])
                        }
                    },
                    actionSetCategory = {
                        actionSetCategory(categoryId, directories.value[index].systemName)
                        expandedStateSetCategoryPopup.value = true
                    },
                    actionSetColor = {
                        ContextScreen.set(
                            Screen.DASHBOARD_SCREEN,
                            "currentFileObjectSetProperty",
                            directories.value[index].systemName
                        )
                        if (color != null) {
                            ContextScreen.set(Screen.DASHBOARD_SCREEN, "colorEditFileObject", color)
                        } else {
                            ContextScreen.set(Screen.DASHBOARD_SCREEN, "colorEditFileObject", null)
                        }
                        expandedStateSetColorPopup.value = true
                    },
                    actionRename = {
                        actionRename(directories.value[index].systemName)
                        expandedStateModalRenameFileObject.value = true
                    },
                    actionComments = {
                        actionComments(
                            scope = scope,
                            scaffoldStateCommentFileObject = scaffoldStateCommentFileObject,
                            systemName = directories.value[index].systemName
                        )
                    },
                    actionDoubleClick = {
                        val directory: FileObject = directories.value[index]

                        ActionsWalkingThroughDirectories.call(
                            scope = scope,
                            directory
                        )
                    },
                    actionDownload = {
                        scope.launch {
                            ActionDownloadFileOrDirectory.call(
                                scope = scope,
                                directories.value[index].realName,
                                directories.value[index].systemName,
                                false,
                                true
                            )
                        }
                    }
                ).render()
            }
        }
    }
}

/**
 * Content renderer responsible for file objects with type - file
 *
 * @param scope Defines a scope for new coroutines.
 * @param files file objects with type - file
 * @param expandedStateSetCategoryPopup Current state of the modal [PopupSetUserCategoryInFileObject]
 * when setting a custom category
 * @param expandedStateModalRenameFileObject Current state of the modal [PopupRenameFileObject] rename file object.
 * @param scaffoldStateCommentFileObject State of this scaffold widget [PopupContentCommentsInFileObjectModal].
 * @param scaffoldStateInfoFileObject State of this scaffold widget [PopupContentInfoFileObjectModal].
 */
@Composable
internal fun RenderLayoutFiles(
    scope: CoroutineScope,
    files: MutableState<MutableList<FileObject>>,
    expandedStateSetCategoryPopup: MutableState<Boolean>,
    expandedStateModalRenameFileObject: MutableState<Boolean>,
    scaffoldStateCommentFileObject: ScaffoldState,
    scaffoldStateInfoFileObject: ScaffoldState
) {
    Column {
        TitleCategoryFileObject("Files", files.value.size)
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(top = 12.dp)
        ) {
            items(files.value.size) { index ->
                val categoryId: Int? = files.value[index].categoryId
                CartFileObject(
                    isDirectory = false,
                    isFile = true,
                    text = files.value[index].realName,
                    categoryId = categoryId,
                    actionInfo = {
                        actionInfo(
                            scope = scope,
                            scaffoldStateInfoFileObject = scaffoldStateInfoFileObject,
                            systemName = files.value[index].systemName
                        )
                    },
                    actionDelete = {
                        scope.launch {
                            files.value = actionDelete(files.value[index])
                        }
                    },
                    actionSetCategory = {
                        actionSetCategory(categoryId, files.value[index].systemName)
                        expandedStateSetCategoryPopup.value = true
                    },
                    actionRename = {
                        actionRename(files.value[index].systemName)
                        expandedStateModalRenameFileObject.value = true
                    },
                    actionComments = {
                        actionComments(
                            scope = scope,
                            scaffoldStateCommentFileObject = scaffoldStateCommentFileObject,
                            systemName = files.value[index].systemName
                        )
                    },
                    actionDownload = {
                        scope.launch {
                            ActionDownloadFileOrDirectory.call(
                                scope = scope,
                                files.value[index].realName,
                                files.value[index].systemName,
                                true,
                                false
                            )
                        }
                    }
                ).render()
            }
        }
    }
}

/**
 * Top panel render - search for file objects and avatar to open profile menu
 *
 * @param scaffoldState State for [Scaffold] composable component.
 * @param scope Defines a scope for new coroutines.
 * @param avatar User avatar URL.
 */
@Composable
internal fun TopPanelDashboard(
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    avatar: MutableState<String?> = mutableStateOf(null)
) {
    Row(
        modifier = Modifier
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .padding(0.dp, 12.dp, 12.dp, 12.dp)
        ) {
            FieldSearch().render()
        }
        Column(
            modifier = Modifier
                .height(40.dp)
                .width(52.dp)
                .padding(end = 12.dp)
        ) {
            Avatar(
                stateScaffold = scaffoldState,
                scope = scope,
                avatar = avatar.value
            ).render()
        }
    }
}

/**
 * Action when deleting a file object.
 *
 * @param fileObject File object to be deleted.
 * @return New filtered list of file objects.
 */
private suspend fun actionDelete(fileObject: FileObject): MutableList<FileObject> {
    val objects: MutableList<FileObject> = mutableListOf()
    objects.addAll(ReactiveFileObject.managerFileSystemObjects.objects)

    val managerFileObject = ManagerFileObject(
        ReactiveFileObject.managerFileSystemObjects.systemParents,
        ReactiveFileObject.managerFileSystemObjects.level,
        objects
    )
    managerFileObject.objects.remove(fileObject)

    ReactiveLoader.setReactiveValue(
        "managerFileSystemObjects",
        "deleteFileObject",
        managerFileObject,
        true
    )

    return if (fileObject.isFile) {
        managerFileObject.objects.filter { it.isFile }.toMutableList()
    } else {
        managerFileObject.objects.filter { it.isDirectory }.toMutableList()
    }
}

/**
 * Action when binding a custom category to a file object.
 *
 * @param categoryId The current state of the user category of the file object.
 * @param systemName System name of the file object.
 */
private fun actionSetCategory(categoryId: Int?, systemName: String) {
    ContextScreen.set(
        Screen.DASHBOARD_SCREEN,
        "currentFileObjectSetProperty",
        systemName
    )
    if (categoryId != null) {
        ContextScreen.set(Screen.DASHBOARD_SCREEN, "categoryIdEditFileObject", categoryId)
    } else {
        ContextScreen.set(Screen.DASHBOARD_SCREEN, "categoryIdEditFileObject", -1)
    }
}

/**
 * The action is called when a file object is renamed.
 *
 * @param systemName System name of the file object.
 */
private fun actionRename(systemName: String) {
    ContextScreen.set(
        Screen.DASHBOARD_SCREEN,
        "currentFileObjectSetProperty",
        systemName
    )
}

/**
 * Load comments for a file object and render.
 *
 * @param scope Defines a scope for new coroutines.
 * @param scaffoldStateCommentFileObject State for [Scaffold] composable component.
 * @param systemName System name of the file object.
 */
private fun actionComments(
    scope: CoroutineScope,
    scaffoldStateCommentFileObject: ScaffoldState,
    systemName: String
) {
    if (scaffoldStateCommentFileObject.drawerState.isClosed) {
        ReactiveLoader.resetIsLoad("commentsFileSystemObject")
        scope.launch {
            ContextScreen.set(
                Screen.DASHBOARD_SCREEN,
                "currentFileObject",
                systemName
            )

            ReactiveLoader.load("commentsFileSystemObject")
            scaffoldStateCommentFileObject.drawerState.open()
        }
    }
}

/**
 * Load info for a file object and render.
 *
 * @param scope Defines a scope for new coroutines.
 * @param scaffoldStateInfoFileObject State for [Scaffold] composable component.
 * @param systemName System name of the file object.
 */
private fun actionInfo(
    scope: CoroutineScope,
    scaffoldStateInfoFileObject: ScaffoldState,
    systemName: String
) {
    if (scaffoldStateInfoFileObject.drawerState.isClosed) {
        scope.launch {
            ContextScreen.set(
                Screen.DASHBOARD_SCREEN,
                "currentFileObject",
                systemName
            )

            scaffoldStateInfoFileObject.drawerState.open()
        }
    }
}
