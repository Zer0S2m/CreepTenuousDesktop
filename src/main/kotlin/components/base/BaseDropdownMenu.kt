package components.base

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem

/**
 * Base interface for implementing an extended component [DropdownMenu]
 */
interface BaseDropdownMenu : BaseComponent {

    /**
     * Iterable object of combobox items expanding component [DropdownMenuItem]
     */
    val items: Iterable<BaseDropdownMenuItem>

}