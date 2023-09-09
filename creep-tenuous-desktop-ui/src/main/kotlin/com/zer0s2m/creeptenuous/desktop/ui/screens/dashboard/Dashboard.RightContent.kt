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
import com.zer0s2m.creeptenuous.desktop.ui.components.cards.CartFileObject
import com.zer0s2m.creeptenuous.desktop.ui.components.fields.FieldSearch
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.Avatar
import kotlinx.coroutines.CoroutineScope

/**
 * Content on the right side of the main dashboard
 *
 * @param directories file objects with type - directory
 * @param files file objects with type - file
 */
@Composable
internal fun RenderLayoutFilesObject(
    directories: MutableState<MutableList<FileObject>>,
    files: MutableState<MutableList<FileObject>>
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 28.dp)
        ) {
            RenderLayoutDirectories(directories)
            RenderLayoutFiles(files)
        }
    }
}

/**
 * Content renderer responsible for file objects with type - category
 *
 * @param directories file objects with type - directory
 */
@Composable
internal fun RenderLayoutDirectories(directories: MutableState<MutableList<FileObject>>) {
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
                CartFileObject(
                    isDirectory = true,
                    isFile = false,
                    text = directories.value[index].realName,
                    color = directories.value[index].color,
                    categoryId = directories.value[index].categoryId
                ).render()
            }
        }
    }
}

/**
 * Content renderer responsible for file objects with type - file
 *
 * @param files file objects with type - file
 */
@Composable
internal fun RenderLayoutFiles(files: MutableState<MutableList<FileObject>>) {
    Column {
        TitleCategoryFileObject("Files", files.value.size)
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(files.value.size) { index ->
                CartFileObject(
                    isDirectory = false,
                    isFile = true,
                    text = files.value[index].realName,
                    categoryId = files.value[index].categoryId
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
