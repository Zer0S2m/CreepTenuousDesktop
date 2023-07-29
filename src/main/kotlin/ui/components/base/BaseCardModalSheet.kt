package ui.components.base

import androidx.compose.material.Card
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * The base interface for implementing the map component for a modal sheet.
 * Extends a component [Card]
 */
interface BaseCardModalSheet : BaseComponent, BaseAnimation {

    /**
     * Callback to be called when the [Card] is clicked.
     */
    val onClick: (() -> Unit?)?
        get() = null

    /**
     * The background color.
     */
    val backgroundColor: Color

    /**
     * Modifier to be applied to the layout of the [Card].
     */
    val modifier: Modifier

}
