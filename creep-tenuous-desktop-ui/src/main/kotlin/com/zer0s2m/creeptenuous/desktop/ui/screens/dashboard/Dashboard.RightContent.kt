package com.zer0s2m.creeptenuous.desktop.ui.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zer0s2m.creeptenuous.desktop.common.dto.FileObject
import com.zer0s2m.creeptenuous.desktop.common.dto.ManagerFileObject
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLoader
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveFileObject
import com.zer0s2m.creeptenuous.desktop.ui.components.CartFileObject
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.Avatar
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.FieldSearch
import kotlinx.coroutines.CoroutineScope

/**
 * Content on the right side of the main dashboard
 *
 * @param directories file objects with type - directory
 * @param files file objects with type - file
 * @param expandedStateSetCategoryPopup Current state of the modal
 * [PopupSetUserCategoryInFileObject] when setting a custom category
 * @param expandedStateSetColorPopup Current state of the modal
 * [PopupSetUserColorInFileObject] when setting a custom color
 */
@Composable
internal fun RenderLayoutFilesObject(
    directories: MutableState<MutableList<FileObject>>,
    files: MutableState<MutableList<FileObject>>,
    expandedStateSetCategoryPopup: MutableState<Boolean>,
    expandedStateSetColorPopup: MutableState<Boolean>
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 28.dp)
        ) {
            RenderLayoutDirectories(
                directories = directories,
                expandedStateSetCategoryPopup = expandedStateSetCategoryPopup,
                expandedStateSetColorPopup = expandedStateSetColorPopup,
            )
            RenderLayoutFiles(
                files = files,
                expandedStateSetCategoryPopup = expandedStateSetCategoryPopup
            )
        }
    }
}

/**
 * Content renderer responsible for file objects with type - category
 *
 * @param directories file objects with type - directory
 * @param expandedStateSetCategoryPopup Current state of the modal
 * [PopupSetUserCategoryInFileObject] when setting a custom category
 * @param expandedStateSetColorPopup Current state of the modal
 * [PopupSetUserColorInFileObject] when setting a custom color
 */
@Composable
internal fun RenderLayoutDirectories(
    directories: MutableState<MutableList<FileObject>>,
    expandedStateSetCategoryPopup: MutableState<Boolean>,
    expandedStateSetColorPopup: MutableState<Boolean>,
) {
    Column(
        modifier = Modifier
            .padding(bottom = 28.dp)
    ) {
        TitleCategoryFileObject("Folders", directories.value.size)
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
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
                    actionDelete = {
                        directories.value = actionDelete(directories.value[index])
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
                    }
                ).render()
            }
        }
    }
}

/**
 * Content renderer responsible for file objects with type - file
 *
 * @param files file objects with type - file
 * @param expandedStateSetCategoryPopup Current state of the modal [PopupSetUserCategoryInFileObject]
 * when setting a custom category
 */
@Composable
internal fun RenderLayoutFiles(
    files: MutableState<MutableList<FileObject>>,
    expandedStateSetCategoryPopup: MutableState<Boolean>
) {
    Column {
        TitleCategoryFileObject("Files", files.value.size)
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(files.value.size) { index ->
                val categoryId: Int? = files.value[index].categoryId
                CartFileObject(
                    isDirectory = false,
                    isFile = true,
                    text = files.value[index].realName,
                    categoryId = categoryId,
                    actionDelete = {
                        files.value = actionDelete(files.value[index])
                    },
                    actionSetCategory = {
                        actionSetCategory(categoryId, files.value[index].systemName)
                        expandedStateSetCategoryPopup.value = true
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
 */
@Composable
internal fun TopPanelDashboard(
    scaffoldState: ScaffoldState,
    scope: CoroutineScope
) {
    Row(
        modifier = Modifier.fillMaxSize()
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
                .fillMaxSize()
                .padding(0.dp, 12.dp, 12.dp, 12.dp)
        ) {
            Avatar(
                stateScaffold = scaffoldState,
                scope = scope
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
private fun actionDelete(fileObject: FileObject): MutableList<FileObject> {
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
