package com.zer0s2m.creeptenuous.desktop.ui.components.menu

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
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