package components.base

import androidx.compose.material.DropdownMenuItem

/**
 * Base interface for implementing an extended component [DropdownMenuItem]
 */
interface BaseDropdownMenuItem : BaseComponent {

    /**
     * Display text in a component
     */
    val text: String
        get() = ""

}
