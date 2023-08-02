package com.zer0s2m.creeptenuous.desktop.ui.screens.user

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zer0s2m.creeptenuous.desktop.common.dto.GrantedRight
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.common.enums.TypeFileObject
import com.zer0s2m.creeptenuous.desktop.common.enums.TypeRight
import com.zer0s2m.creeptenuous.desktop.ui.screens.ProfileUser

/**
 * Rendering part of the user profile screen [Screen.PROFILE_GRANTED_RIGHTS_SCREEN]
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileUser.ProfileGrantedRights.render() {
    val list: MutableList<GrantedRight> = listOf(
        GrantedRight(TypeFileObject.FOLDER, listOf(TypeRight.MOVE), "Folder 1"),
        GrantedRight(TypeFileObject.FILE, listOf(TypeRight.COPY), "File 1"),
        GrantedRight(TypeFileObject.FILE, listOf(TypeRight.SHOW), "File 2"),
        GrantedRight(TypeFileObject.FOLDER, listOf(TypeRight.COPY, TypeRight.SHOW), "Folder 2"),
        GrantedRight(TypeFileObject.FILE, listOf(TypeRight.MOVE, TypeRight.SHOW), "File 3"),
        GrantedRight(TypeFileObject.FOLDER, listOf(TypeRight.MOVE, TypeRight.SHOW), "Folder 3"),
        GrantedRight(TypeFileObject.FOLDER, listOf(TypeRight.MOVE, TypeRight.SHOW, TypeRight.DELETE), "Folder 4"),
        GrantedRight(TypeFileObject.FOLDER, listOf(TypeRight.MOVE, TypeRight.SHOW, TypeRight.DELETE, TypeRight.COPY, TypeRight.UPLOAD), "Folder 5"),
        GrantedRight(TypeFileObject.FOLDER, listOf(TypeRight.MOVE, TypeRight.SHOW, TypeRight.DELETE), "Folder 6"),
        GrantedRight(TypeFileObject.FILE, listOf(TypeRight.MOVE, TypeRight.SHOW, TypeRight.DELETE, TypeRight.DOWNLOAD), "Folder 4"),
    ).toMutableStateList()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {

        }

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(4),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(list.size) { index ->
                ItemGrantedRight(item = list[index])
            }
        }
    }

}

/**
 * Selected right for later deletion
 */
@Stable
private var contextSelectDeleteRight: MutableList<TypeRight> = mutableListOf()

/**
 * Basic grant card for interacting with file objects
 */
@Composable
internal fun ProfileUser.ProfileGrantedRights.ItemGrantedRight(
    item: GrantedRight
) {
    val stateModal: MutableState<Boolean> = remember { mutableStateOf(false) }

    BaseCardItemGrid(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Column {
            Row {
                Text(
                    if (item.typeFileObject == TypeFileObject.FILE) "File: ${item.titleFileObject}"
                    else "Folder: ${item.titleFileObject}"
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(end = 4.dp)
                ) {
                    Text(text = "Types:")
                }
                Column {
                    item.typeRight.forEach { type ->
                        Text(
                            text = type.title,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        IconButtonDelete(onClick = {
            stateModal.value = true
            contextSelectDeleteRight.clear()
            contextSelectDeleteRight.addAll(item.typeRight)
        })
    }

    PopupDeleteRight(stateModal = stateModal)
}

/**
 * Modal window for selecting right removal
 *
 * @param stateModal Modal window states for category creation
 */
@Composable
private fun PopupDeleteRight(
    stateModal: MutableState<Boolean>
) {
    BaseModalPopup(
        stateModal = stateModal
    ) {
        Surface(
            contentColor = contentColorFor(MaterialTheme.colors.surface),
            modifier = Modifier
                .width(360.dp)
                .height(500.dp)
                .shadow(24.dp, RoundedCornerShape(4.dp))
        ) {
            PopupDeleteRightContent()
        }
    }
}

@Composable
private fun PopupDeleteRightContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .pointerInput({}) {
                detectTapGestures(onPress = {
                    // Workaround to disable clicks on Surface background
                    // https://github.com/JetBrains/compose-jb/issues/2581
                })
            }
    ) {
        Text(
            text = "Removing rights",
            fontSize = 20.sp
        )
        Column(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
        ) {
            contextSelectDeleteRight.forEach { item ->
                PopupItemDeleteRight(item)
            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .pointerHoverIcon(PointerIcon.Hand)
                .padding(top = 8.dp),
            onClick = {}
        ) {
            Text("Delete")
        }
    }
}

/**
 * Right element to delete
 *
 * @param typeRight Type of right to delete
 */
@Composable
private fun PopupItemDeleteRight(typeRight: TypeRight) {
    val checkedState: MutableState<Boolean> = remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = typeRight.title,
                fontWeight = FontWeight.Medium
            )

            Checkbox(
                checked = checkedState.value,
                modifier = Modifier
                    .pointerHoverIcon(PointerIcon.Hand),
                onCheckedChange = { checkedState.value = it }
            )
        }
    }

    Divider(thickness = 1.dp)
}
