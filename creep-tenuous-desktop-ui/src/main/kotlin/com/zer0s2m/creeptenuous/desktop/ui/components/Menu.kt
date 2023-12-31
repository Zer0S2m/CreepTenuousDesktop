package com.zer0s2m.creeptenuous.desktop.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.PaddingValues
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
import com.zer0s2m.creeptenuous.desktop.ui.animations.setAnimateColorAsStateInDropMenuItem
import com.zer0s2m.creeptenuous.desktop.ui.components.base.BaseDropdownMenu
import com.zer0s2m.creeptenuous.desktop.ui.components.base.BaseDropdownMenuItem

/**
 * Base class for extended component
 *
 * @param itemsComp Iterable object of combobox items expanding component [DropdownMenuItem]
 * @param expanded Variable value holder for opening and closing the menu for the user
 * @param modifier Modifiers to decorate or add behavior to elements
 */
class DropdownMenuAdvanced(
    itemsComp: Iterable<BaseDropdownMenuItem>,
    override val expanded: MutableState<Boolean>,
    override val modifier: Modifier = Modifier
) : BaseDropdownMenu {

    /**
     * Iterable object of combobox items expanding component [DropdownMenuItem]
     */
    override val items = itemsComp

    /**
     * Component rendering
     */
    @Composable
    override fun render() {
        DropdownMenu(
            expanded = expanded.value,
            modifier = modifier,
            onDismissRequest = {
                expanded.value = false
            }
        ) {
            items.forEach { it.render() }
        }
    }

}

/**
 * Base interface for implementing an extended component [DropdownMenuItem]
 *
 * @param text Display text in a component
 * @param isAnimation Set background change animation for a component
 * @param action Call an action when an element is clicked
 * @param colorText Color to apply to the text
 * @param modifierMenu Modifiers to decorate or add behavior to elements
 * @param contentPadding The padding applied to the content of this menu item
 */
class DropdownMenuItemAdvanced(
    override val text: String = "",
    override val isAnimation: Boolean = true,
    override val action: () -> Unit,
    private val colorText: Color = Color.White,
    private var modifierMenu: Modifier = Modifier,
    private val contentPadding: PaddingValues = MenuDefaults.DropdownMenuItemContentPadding
) : BaseDropdownMenuItem {

    /**
     * Component rendering
     */
    @Composable
    override fun render() {
        val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
        val isHover: State<Boolean> = interactionSource.collectIsHoveredAsState()
        val animatedMenuItemColor = setAnimateColorAsStateInDropMenuItem(isHover = isHover)

        if (isAnimation) {
            modifierMenu = modifierMenu.background(color = animatedMenuItemColor.value)
        }

        DropdownMenuItem(
            modifier = modifierMenu,
            contentPadding = contentPadding,
            onClick = action,
            interactionSource = interactionSource
        ) {
            Text(
                text = text,
                color = colorText
            )
        }
    }

}
