package com.zer0s2m.creeptenuous.desktop.ui.components.base

import androidx.compose.material.DropdownMenuItem

/**
 * Base interface for implementing an extended component [DropdownMenuItem]
 */
interface BaseDropdownMenuItem : BaseComponent, BaseAnimation {

    /**
     * Display text in a component
     */
    val text: String
        get() = ""

    /**
     * Call an action when an element is clicked
     */
    val action: () -> Unit

}
