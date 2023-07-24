package components.menu

import androidx.compose.runtime.Composable
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.MutableState
import components.base.BaseDropdownMenuItem

/**
 * Base interface for implementing an extended component [DropdownMenuItem]
 *
 * @param text Display text in a component
 * @param expanded Variable value holder for opening and closing the menu for the user
 */
class DropdownMenuItemAdvanced(
    override val text: String = "",
    private val expanded: MutableState<Boolean>
) : BaseDropdownMenuItem {

    /**
     * Component rendering
     */
    @Composable
    override fun render() {
        DropdownMenuItem(onClick = {
            expanded.value = false
        }) {
            Text(text)
        }
    }

}