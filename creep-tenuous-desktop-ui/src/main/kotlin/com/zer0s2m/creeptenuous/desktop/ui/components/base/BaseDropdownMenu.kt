package com.zer0s2m.creeptenuous.desktop.ui.components.base

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

/**
 * Base interface for implementing an extended component [DropdownMenu]
 */
interface BaseDropdownMenu : BaseComponent {

    /**
     * Iterable object of combobox items expanding component [DropdownMenuItem]
     */
    val items: Iterable<BaseDropdownMenuItem>

    /**
     * Variable value holder for opening and closing the menu for the user
     */
    val expanded: MutableState<Boolean>

    /**
     * Modifiers to decorate or add behavior to elements
     */
    val modifier: Modifier

}