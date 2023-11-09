package com.zer0s2m.creeptenuous.desktop.ui.screens.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveUser
import com.zer0s2m.creeptenuous.desktop.ui.screens.common.LayoutDeleteAndOpenInputSelect
import com.zer0s2m.creeptenuous.desktop.ui.screens.common.RenderLayoutUserCategory

/**
 * Width of category selection area from list
 */
@get:ReadOnlyComposable
private val baseWidthColumnSelectItem: Dp get() = 328.dp

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
            modifier = Modifier.width(baseWidthColumnSelectItem)
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
