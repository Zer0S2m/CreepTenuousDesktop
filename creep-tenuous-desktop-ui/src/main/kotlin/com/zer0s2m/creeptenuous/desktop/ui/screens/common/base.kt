package com.zer0s2m.creeptenuous.desktop.ui.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zer0s2m.creeptenuous.desktop.common.dto.ConverterColor
import com.zer0s2m.creeptenuous.desktop.common.dto.UserCategory
import com.zer0s2m.creeptenuous.desktop.common.enums.Resources
import com.zer0s2m.creeptenuous.desktop.common.utils.colorConvertHexToRgb
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveUser
import com.zer0s2m.creeptenuous.desktop.ui.components.CircleCategoryBox
import com.zer0s2m.creeptenuous.desktop.ui.components.IconButtonRemove
import com.zer0s2m.creeptenuous.desktop.ui.components.InputSelect

/**
 * The component responsible for displaying the selected color palette from the component [DropdownMenuSelectColor].
 *
 * @param isSetColor Status: Is any color palette installed.
 * @param currentColor Current set color.
 * @param isDelete Whether to show the delete object button.
 * @param action Configure component to receive clicks [Row].
 * @param actionDelete Configure component to receive clicks [Icon] (action delete).
 */
@Composable
internal fun InputSelectColor(
    isSetColor: MutableState<Boolean>,
    currentColor: MutableState<Color?>,
    isDelete: Boolean = true,
    action: () -> Unit,
    actionDelete: () -> Unit = {}
) {
    InputSelect(
        onClick = action,
        isDelete = isDelete,
        actionDelete = actionDelete
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
    }
}

/**
 * The main component for choosing a color is a dropdown list.
 *
 * Extends a component [DropdownMenu]
 *
 * @param expandedState Whether the menu is currently open and visible to the user
 * @param modifier Modifiers to decorate or add behavior to elements
 * @param action Call an action when an element is clicked [DropdownMenuItem]
 */
@Composable
internal fun DropdownMenuSelectColor(
    expandedState: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    action: (String, Color, Int?) -> Unit
) {
    DropdownMenu(
        expanded = expandedState.value,
        onDismissRequest = {
            expandedState.value = false
        },
        modifier = modifier
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
                    action(it.color, color, it.id)
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

/**
 * Field action call layout - delete and open
 *
 * @param actionDelete Configure component to receive clicks [Icon] (action delete).
 * @param isDelete Whether to show the delete object button.
 */
@Composable
internal fun LayoutDeleteAndOpenInputSelect(
    actionDelete: () -> Unit = {},
    isDelete: Boolean = true
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (isDelete) {
            IconButtonRemove(
                onClick = actionDelete
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Icon(
            painter = painterResource(resourcePath = Resources.ICON_ARROW.path),
            contentDescription = "asd",
            tint = MaterialTheme.colors.secondary,
            modifier = Modifier
                .size(20.dp)
        )
    }
}

/**
 * Component responsible for minimal information about the user category
 *
 * @param userCategory User category information
 */
@Composable
internal fun RenderLayoutUserCategory(userCategory: UserCategory) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (userCategory.color != null) {
            CircleCategoryBox(userCategory.color!!, 16.dp)
        }
        Text(
            text = userCategory.title,
            modifier = if (userCategory.color != null) Modifier
                .padding(start = 8.dp) else Modifier
        )
    }
}
