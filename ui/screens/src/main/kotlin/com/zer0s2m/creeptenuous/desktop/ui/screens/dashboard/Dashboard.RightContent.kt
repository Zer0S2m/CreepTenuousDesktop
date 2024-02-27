package com.zer0s2m.creeptenuous.desktop.ui.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zer0s2m.creeptenuous.desktop.common.dto.FileObject
import com.zer0s2m.creeptenuous.desktop.common.dto.ManagerFileObject
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLoader
import com.zer0s2m.creeptenuous.desktop.reactive.actions.ActionDownloadFileOrDirectory
import com.zer0s2m.creeptenuous.desktop.reactive.actions.ActionUploadDirectory
import com.zer0s2m.creeptenuous.desktop.reactive.actions.ActionUploadFiles
import com.zer0s2m.creeptenuous.desktop.reactive.actions.ActionWalkingThroughDirectories
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveFileObject
import com.zer0s2m.creeptenuous.desktop.ui.components.CartFileObject
import com.zer0s2m.creeptenuous.desktop.ui.components.IconButtonAdd
import com.zer0s2m.creeptenuous.desktop.ui.components.IconButtonUpload
import com.zer0s2m.creeptenuous.desktop.ui.components.TitleCategoryFileObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File
import java.util.*
import javax.swing.JFileChooser
import javax.swing.filechooser.FileFilter


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
        Column {
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
            Spacer(modifier = Modifier.height(28.dp))
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
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TitleCategoryFileObject("Folders", directories.value.size)
            Spacer(modifier = Modifier.width(12.dp))
            IconButtonAdd(
                onClick = {
                    expandedStateCreateFileObjectTypeDirectory.value = true
                },
                contentDescription = "Add file object"
            )
            Spacer(modifier = Modifier.width(6.dp))
            IconButtonUpload(
                onClick = {
                    val fileChooser = JFileChooser()
                    fileChooser.dialogTitle = "Selection of zip archive"
                    fileChooser.fileFilter = FileFilterZipArchive()
                    fileChooser.showSaveDialog(null)

                    val selectedFile: File? = fileChooser.selectedFile
                    if (selectedFile != null) {
                        ActionUploadDirectory.call(
                            scope = scope,
                            selectedFile
                        )
                    }
                }
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

                        ActionWalkingThroughDirectories.call(
                            scope = scope,
                            directory
                        )
                    },
                    actionDownload = {
                        ActionDownloadFileOrDirectory.call(
                            scope = scope,
                            directories.value[index].realName,
                            directories.value[index].systemName,
                            false,
                            true
                        )
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
        Row(verticalAlignment = Alignment.CenterVertically) {
            TitleCategoryFileObject("Files", files.value.size)
            Spacer(modifier = Modifier.width(12.dp))
            IconButtonUpload(
                onClick = {
                    val fileChooser = JFileChooser()
                    fileChooser.isMultiSelectionEnabled = true
                    fileChooser.dialogTitle = "Selection of files"
                    fileChooser.showSaveDialog(null)

                    val selectedFiles: List<File> = fileChooser.selectedFiles.toList()
                    if (selectedFiles.isNotEmpty()) {
                        ActionUploadFiles.call(
                            scope = scope,
                            selectedFiles
                        )
                    }
                }
            )
        }

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
                        ActionDownloadFileOrDirectory.call(
                            scope = scope,
                            files.value[index].realName,
                            files.value[index].systemName,
                            true,
                            false
                        )
                    }
                ).render()
            }
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

private class FileFilterZipArchive : FileFilter() {

    override fun getDescription(): String {
        return "ZIP Archive (*.zip)"
    }

    override fun accept(file: File): Boolean {
        return if (file.isDirectory) {
            true
        } else {
            val filename: String = file.name.lowercase(Locale.getDefault())
            filename.endsWith(".zip")
        }
    }

}
