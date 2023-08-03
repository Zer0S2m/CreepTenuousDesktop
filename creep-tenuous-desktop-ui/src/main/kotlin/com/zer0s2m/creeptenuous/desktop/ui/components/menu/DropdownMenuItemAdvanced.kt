package com.zer0s2m.creeptenuous.desktop.ui.components.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MenuDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.zer0s2m.creeptenuous.desktop.ui.components.base.BaseDropdownMenuItem
import com.zer0s2m.creeptenuous.desktop.ui.animations.setAnimateColorAsStateInDropMenuItem
import com.zer0s2m.creeptenuous.desktop.ui.animations.setHoverInDropMenuItem

/**
 * Base interface for implementing an extended component [DropdownMenuItem]
 *
 * @param text Display text in a component
 * @param isAnimation Set background change animation for a component
 * @param expanded Variable value holder for opening and closing the menu for the user
 * @param colorText Color to apply to the text
 * @param modifierMenu Modifiers to decorate or add behavior to elements
 * @param contentPadding The padding applied to the content of this menu item
 */
class DropdownMenuItemAdvanced(
    override val text: String = "",
    override val isAnimation: Boolean = true,
    private val expanded: MutableState<Boolean>,
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
        val isHover: MutableState<Boolean> = remember { mutableStateOf(false) }
        val animatedMenuItemColor = setAnimateColorAsStateInDropMenuItem(isHover = isHover)

        if (isAnimation) {
            modifierMenu = modifierMenu
                .background(color = animatedMenuItemColor.value)
                .hoverable(interactionSource = interactionSource)
            setHoverInDropMenuItem(
                interactionSource = interactionSource,
                isHover = isHover
            )
        }

        DropdownMenuItem(
            modifier = modifierMenu,
            contentPadding = contentPadding,
            onClick = {
                expanded.value = false
            }
        ) {
            Text(
                text = text,
                color = colorText
            )
        }
    }

}