package components.menu

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MenuDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import components.base.BaseDropdownMenuItem
import androidx.compose.ui.graphics.Color

/**
 * Base interface for implementing an extended component [DropdownMenuItem]
 *
 * @param text Display text in a component
 * @param expanded Variable value holder for opening and closing the menu for the user
 * @param colorText Color to apply to the text
 * @param modifierMenu Modifiers to decorate or add behavior to elements
 * @param contentPadding The padding applied to the content of this menu item
 */
class DropdownMenuItemAdvanced(
    override val text: String = "",
    private val expanded: MutableState<Boolean>,
    private val colorText: Color = Color.White,
    private val modifierMenu: Modifier = Modifier,
    private val contentPadding: PaddingValues = MenuDefaults.DropdownMenuItemContentPadding
) : BaseDropdownMenuItem {

    /**
     * Component rendering
     */
    @Composable
    override fun render() {
        DropdownMenuItem(
            modifier = modifierMenu,
            contentPadding = contentPadding,
            onClick = {
                expanded.value = false
            }
        ) {
            Text(
                text = text,
                color = colorText
            )
        }
    }

}