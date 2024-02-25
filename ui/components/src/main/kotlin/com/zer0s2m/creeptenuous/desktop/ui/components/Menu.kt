package com.zer0s2m.creeptenuous.desktop.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MenuDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import com.zer0s2m.creeptenuous.desktop.common.dto.ConverterColor
import com.zer0s2m.creeptenuous.desktop.common.utils.colorConvertHexToRgb
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveUser
import com.zer0s2m.creeptenuous.desktop.ui.components.animations.setAnimateColorAsStateInDropMenuItem

/**
 * A standalone component is a drop-down list element.
 *
 * Extended component [DropdownMenuItem].
 *
 * @param text Display text in a component.
 * @param isAnimation Set background change animation for a component.
 * @param onClick Call an action when an element is clicked.
 * @param colorText Color to apply to the text.
 * @param modifier Modifiers to decorate or add behavior to elements.
 * @param contentPadding The padding applied to the content of this menu item.
 */
@Composable
fun DropdownMenuItemAdvanced(
    text: String = "",
    isAnimation: Boolean = true,
    onClick: () -> Unit,
    colorText: Color = Color.White,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = MenuDefaults.DropdownMenuItemContentPadding
) {
    var modifierMenuLocal: Modifier = modifier
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    val isHover: State<Boolean> = interactionSource.collectIsHoveredAsState()
    val animatedMenuItemColor = setAnimateColorAsStateInDropMenuItem(isHover = isHover)

    if (isAnimation) {
        modifierMenuLocal = modifierMenuLocal.background(color = animatedMenuItemColor.value)
    }

    DropdownMenuItem(
        modifier = modifierMenuLocal,
        contentPadding = contentPadding,
        onClick = onClick,
        interactionSource = interactionSource
    ) {
        Text(
            text = text,
            color = colorText
        )
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
fun DropdownMenuSelectColor(
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
