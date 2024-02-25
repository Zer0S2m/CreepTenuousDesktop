package com.zer0s2m.creeptenuous.desktop.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zer0s2m.creeptenuous.desktop.common.dto.ConverterColor
import com.zer0s2m.creeptenuous.desktop.common.enums.Resources
import com.zer0s2m.creeptenuous.desktop.common.utils.colorConvertHexToRgb
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveUser

/**
 * Component for rendering a component that will subsequently call a drop-down list.
 *
 * @param isDelete Whether to show the delete object button.
 * @param onClickDelete The lambda to be invoked when this icon is pressed.
 * @param onClick Configure component to receive clicks.
 * @param content Content layout.
 */
@Composable
@OptIn(ExperimentalFoundationApi::class)
fun InputSelect(
    isDelete: Boolean = true,
    onClickDelete: () -> Unit = {},
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .semantics { role = Role.Button }
            .onClick {
                onClick()
            }
            .border(0.5.dp, MaterialTheme.colors.secondary, RoundedCornerShape(4.dp))
            .pointerHoverIcon(PointerIcon.Hand)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .pointerHoverIcon(PointerIcon.Hand),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            content()

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (isDelete) {
                    IconButtonRemove(onClick = onClickDelete)
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Icon(
                    painter = painterResource(resourcePath = Resources.ICON_ARROW.path),
                    contentDescription = "Open select property",
                    tint = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .size(20.dp)
                )
            }
        }
    }
}

/**
 * The component responsible for displaying the selected color palette from the component [DropdownMenuSelectColor].
 *
 * @param modifierDropdownMenu [Modifier] to be applied to the menu's content.
 * @param expandedStateDropdown Whether the menu is expanded or not.
 * @param isSetColor Status: Is any color palette installed.
 * @param currentColor Current set color.
 * @param onClick Configure component to receive clicks [Row].
 * @param onClickDelete Configure component to receive clicks [Icon] (action delete).
 * @param onClickDropdownMenuItem Configure component to receive clicks [Row].
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InputSelectColor(
    modifierDropdownMenu: Modifier = Modifier,
    expandedStateDropdown: MutableState<Boolean>,
    isSetColor: MutableState<Boolean>,
    currentColor: MutableState<Color?>,
    onClick: () -> Unit,
    onClickDelete: () -> Unit = {},
    onClickDropdownMenuItem: (colorStr: String, color: Color, colorId: Int?) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .onClick {
               onClick()
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
            if (!isSetColor.value) {
                Text(
                    text = "Color...",
                    color = Color(255, 255, 255, 160),
                )
            } else {
                val baseModifier = Modifier
                    .width(120.dp)
                    .height(24.dp)
                Box(
                    modifier = if (currentColor.value == null) baseModifier
                    else baseModifier.background(currentColor.value!!, RoundedCornerShape(4.dp))
                )
            }

            LayoutDeleteAndOpenInputSelect(
                actionDelete = onClickDelete
            )
        }

        DropdownMenu(
            expanded = expandedStateDropdown.value,
            onDismissRequest = {
                expandedStateDropdown.value = false
            },
            modifier = modifierDropdownMenu
        ) {
            ReactiveUser.userColors.forEach {
                val convertedColor: ConverterColor = colorConvertHexToRgb(it.color)
                val color = Color(
                    red = convertedColor.red,
                    green = convertedColor.green,
                    blue = convertedColor.blue
                )

                DropdownMenuItem(
                    onClick = {
                        onClickDropdownMenuItem(it.color, color, it.id)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .pointerHoverIcon(PointerIcon.Hand),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color, RoundedCornerShape(4.dp))
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

/**
 * Standalone component - select a custom category.
 *
 * @param expandedState Modal window states.
 * @param actionDropdownItem Call an action when an element is clicked [DropdownMenuItem].
 * @param actionDelete Call an action when deleting a category.
 * @param categoryId Current selected user category.
 */
@Composable
@OptIn(ExperimentalFoundationApi::class)
fun InputSelectCategory(
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
                    LayoutUserCategory(userCategory)
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
            modifier = Modifier.width(CommonInput.baseWidthColumnSelectItem)
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
                    LayoutUserCategory(userCategory = it)
                }
            }
        }
    }
}

private object CommonInput {

    /**
     * Width of category selection area from list
     */
    @get:ReadOnlyComposable
    val baseWidthColumnSelectItem: Dp get() = 328.dp

}
