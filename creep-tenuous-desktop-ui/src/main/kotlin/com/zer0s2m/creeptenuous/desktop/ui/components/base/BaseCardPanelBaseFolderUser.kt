package com.zer0s2m.creeptenuous.desktop.ui.components.base

import androidx.compose.material.Card
import com.zer0s2m.creeptenuous.desktop.common.enums.Resources

/**
 * The base interface for building the component on the left side of the
 * toolbar to display the user's base directories.
 */
interface BaseCardPanelBaseFolderUser : BaseComponent, BaseAnimation {

    /**
     * The text to be displayed
     */
    val text: String

    /**
     * Set an icon for a component
     */
    val isIcon: Boolean

    /**
     * Path to icons [Resources]
     */
    val iconPath: String?
        get() = null

    /**
     * Callback to be called when the [Card] is clicked
     */
    val action: () -> Unit

}