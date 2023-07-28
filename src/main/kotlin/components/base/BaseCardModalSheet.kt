package components.base

import androidx.compose.material.Card

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

}
