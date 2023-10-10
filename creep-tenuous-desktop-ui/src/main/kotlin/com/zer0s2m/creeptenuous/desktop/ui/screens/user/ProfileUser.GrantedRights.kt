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
import com.zer0s2m.creeptenuous.desktop.common.dto.GrantedRightItem
import com.zer0s2m.creeptenuous.desktop.common.dto.GrantedRightItemUser
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.common.enums.TypeRight
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveUser
import com.zer0s2m.creeptenuous.desktop.ui.components.IconButtonRemove
import com.zer0s2m.creeptenuous.desktop.ui.screens.ProfileUser
import com.zer0s2m.creeptenuous.desktop.ui.screens.base.BaseModalPopup

/**
 * Rendering part of the user profile screen [Screen.PROFILE_GRANTED_RIGHTS_SCREEN]
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileUser.ProfileGrantedRights.render() {
    val grantedRight: GrantedRight = ReactiveUser.GrantedRights.grantedRightsFileObjects

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text("Panel")
        }

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(4),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(grantedRight.rights.size) { index ->
                ItemGrantedRight(item = grantedRight.rights[index])
            }
        }
    }

}

/**
 * Selected right for later deletion
 */
@Stable
private var contextSelectDeleteRight: MutableList<GrantedRightItemUser> = mutableListOf()

private const val baseHeightPopupItemDeleteRight: Int = 50

private const val baseWidthElementRight: Int = 180

private const val basePaddingElementRight: Int = 16

/**
 * Basic grant card for interacting with file objects
 */
@Composable
@Suppress("UnusedReceiverParameter")
internal fun ProfileUser.ProfileGrantedRights.ItemGrantedRight(
    item: GrantedRightItem
) {
    val stateModal: MutableState<Boolean> = remember { mutableStateOf(false) }
    val maxCountTypeRights: MutableState<Int> = remember { mutableStateOf(0) }
    val maxCountRights: MutableState<Int> = remember { mutableStateOf(1) }

    BaseCardItemGrid(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Column {
            Row {
                Text(
                    text = item.systemName
                )
            }
            Column(
                modifier = Modifier
                    .padding(top = 4.dp)
            ) {
                item.rights.forEachIndexed { index, right ->
                    Column(
                        modifier = if (index == item.rights.size - 1) Modifier
                                   else Modifier.padding(bottom = 6.dp)
                    ) {
                        Text(
                            text = "User: ${right.user}",
                            fontWeight = FontWeight.SemiBold
                        )

                        Column(
                            modifier = Modifier
                                .padding(start = 8.dp)
                        ) {
                            Row {
                                Text(text = "Types:")

                                Column(
                                    modifier = Modifier
                                        .padding(start = 4.dp)
                                ) {
                                    right.rights.forEach {
                                        Text(
                                            text = it.title,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        IconButtonRemove(onClick = {
            stateModal.value = true
            contextSelectDeleteRight.clear()
            contextSelectDeleteRight.addAll(item.rights)

            // Looks for max size type rights to set max height and width for modal window
            item.rights.forEach {
                if (maxCountTypeRights.value == 0) {
                    maxCountTypeRights.value = it.rights.size
                }

                if (maxCountTypeRights.value < it.rights.size) {
                    maxCountTypeRights.value = it.rights.size
                }
            }

            maxCountRights.value = item.rights.size
        })
    }

    PopupDeleteRight(
        stateModal = stateModal,
        height = 148 + maxCountTypeRights.value * baseHeightPopupItemDeleteRight,
        width = if (item.rights.size == 1) maxCountRights.value * baseWidthElementRight + 32
                else (maxCountRights.value * baseWidthElementRight + 32)
                     + ((item.rights.size - 1) * basePaddingElementRight)
    )
}

/**
 * Modal window for selecting right removal
 *
 * @param stateModal Modal window states for category creation
 * @param height Declare the preferred width of the content to be exactly width
 * @param width Declare the preferred height of the content to be exactly height
 */
@Composable
private fun PopupDeleteRight(
    stateModal: MutableState<Boolean>,
    height: Int,
    width: Int
) {
    BaseModalPopup(
        stateModal = stateModal
    ) {
        Surface(
            contentColor = contentColorFor(MaterialTheme.colors.surface),
            modifier = Modifier
                .width(width.dp)
                .height(height.dp)
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
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                contextSelectDeleteRight.forEach { block ->
                    Column(
                        modifier = Modifier.width(baseWidthElementRight.dp)
                    ) {
                        Text(
                            text = block.user
                        )
                        block.rights.forEach { typeRight ->
                            PopupItemDeleteRight(typeRight)
                        }
                    }
                }
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
                .height(baseHeightPopupItemDeleteRight.dp),
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
