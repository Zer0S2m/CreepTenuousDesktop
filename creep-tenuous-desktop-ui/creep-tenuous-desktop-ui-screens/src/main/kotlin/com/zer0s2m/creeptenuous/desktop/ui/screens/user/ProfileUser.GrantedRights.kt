package com.zer0s2m.creeptenuous.desktop.ui.screens.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zer0s2m.creeptenuous.desktop.common.dto.GrantedRight
import com.zer0s2m.creeptenuous.desktop.common.dto.GrantedRightItem
import com.zer0s2m.creeptenuous.desktop.common.dto.GrantedRightItemUser
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.common.enums.TypeRight
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLoader
import com.zer0s2m.creeptenuous.desktop.ui.components.IconButtonRemove
import com.zer0s2m.creeptenuous.desktop.ui.components.ModalPopup
import com.zer0s2m.creeptenuous.desktop.ui.screens.ProfileUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Rendering part of the user profile screen [Screen.PROFILE_GRANTED_RIGHTS_SCREEN]
 */
@Composable
@Suppress("UnusedReceiverParameter")
fun ProfileUser.ProfileGrantedRights.render() {
    val scope: CoroutineScope = rememberCoroutineScope()
    val grantedRight: MutableState<GrantedRight> = remember {
        ProfileUser.ProfileGrantedRights.userProfileGrantedRightsFileObjects
    }

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
            items(grantedRight.value.rights.size) { index ->
                CardGrantedRight(item = grantedRight.value.rights[index], scope = scope)
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
private fun CardGrantedRight(
    item: GrantedRightItem,
    scope: CoroutineScope
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
                    text = item.realName
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

    // TODO: Increase the height of a popup when removing rights
    // TODO: Place it in a separate component
    PopupDeleteRight(
        stateModal = stateModal,
        height = 148 + maxCountTypeRights.value * baseHeightPopupItemDeleteRight,
        width = if (item.rights.size == 1) maxCountRights.value * baseWidthElementRight + 32
        else (maxCountRights.value * baseWidthElementRight + 32)
                + ((item.rights.size - 1) * basePaddingElementRight),
        onClickDelete = { selectedDeleteRights ->
            stateModal.value = false

            if (selectedDeleteRights.isNotEmpty()) {
                scope.launch {
                    ReactiveLoader.executionIndependentTrigger(
                        nameProperty = "grantedRightsFileObjects",
                        event = "deleteUserGrantedRight",
                        selectedDeleteRights.values,
                        item.systemName
                    )
                    ReactiveLoader.resetIsLoad(nameProperty = "grantedRightsFileObjects")
                    ReactiveLoader.load(nameProperty = "grantedRightsFileObjects")
                }
            }
        }
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
    width: Int,
    onClickDelete: (selectedDeleteRights: Map<String, GrantedRightItemUser>) -> Unit
) {
    val selectedDeleteRights: MutableMap<String, GrantedRightItemUser> = mutableMapOf()

    ModalPopup(
        stateModal = stateModal,
        modifierLayout = Modifier
            .width(width.dp)
            .height(height.dp)
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
                            ItemDeleteRight(
                                typeRight = typeRight,
                                onCheckedChange = { typeRightChecked: TypeRight, checkedState: Boolean ->
                                    if (!selectedDeleteRights.containsKey(block.user) && checkedState) {
                                        selectedDeleteRights[block.user] = GrantedRightItemUser(
                                            user = block.user,
                                            rights = mutableListOf(typeRightChecked)
                                        )
                                    } else if (selectedDeleteRights.containsKey(block.user) && checkedState) {
                                        val rights: MutableList<TypeRight> = mutableListOf()
                                        rights.addAll(selectedDeleteRights[block.user]!!.rights)
                                        rights.add(typeRightChecked)

                                        selectedDeleteRights[block.user] = GrantedRightItemUser(
                                            user = block.user,
                                            rights = rights
                                        )
                                    } else if (selectedDeleteRights.containsKey(block.user) && !checkedState) {
                                        var rights: MutableList<TypeRight> = mutableListOf()
                                        rights.addAll(selectedDeleteRights[block.user]!!.rights)

                                        rights = rights
                                            .stream()
                                            .filter { it != typeRightChecked }
                                            .toList()

                                        if (rights.isEmpty()) {
                                            selectedDeleteRights.remove(block.user)
                                        } else {
                                            selectedDeleteRights[block.user] = GrantedRightItemUser(
                                                user = block.user,
                                                rights = rights
                                            )
                                        }
                                    }
                                }
                            )
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
            onClick = {
                onClickDelete(selectedDeleteRights.toMap())
                selectedDeleteRights.clear()
            }
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
private fun ItemDeleteRight(
    typeRight: TypeRight,
    onCheckedChange: (typeRight: TypeRight, checkedState: Boolean) -> Unit
) {
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
                onCheckedChange = {
                    checkedState.value = it
                    onCheckedChange(typeRight, checkedState.value)
                }
            )
        }
    }

    Divider(thickness = 1.dp)
}
