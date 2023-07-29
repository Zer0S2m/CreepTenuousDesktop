package ui.components.base

import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Modal right sheet. Extends a component [Scaffold]
 */
interface BaseModalRightSheetLayout : BaseComponent {

    /**
     * State of this scaffold widget
     */
    val state: ScaffoldState

    /**
     * Modifiers to decorate or add behavior to elements
     */
    val modifier: Modifier

    /**
     * Modifiers to decorate or add behavior to elements content of the Drawer
     */
    val modifierDrawerInternal: Modifier

    /**
     * Modifiers to decorate or add behavior to elements for wrapping content
     */
    val modifierDrawerExternal: Modifier

    /**
     * Color of the content to use inside the drawer sheet
     */
    val drawerContentColor: Color

}
