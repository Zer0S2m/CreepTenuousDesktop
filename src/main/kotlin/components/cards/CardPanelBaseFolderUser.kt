package components.cards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import components.base.BaseComponent
import enums.Colors

/**
 * Component on the left side of the dashboard to show the user's base directories
 *
 * @param text The text to be displayed
 */
class CardPanelBaseFolderUser(
    private val text: String = ""
) : BaseComponent {

    /**
     * Component rendering
     */
    @Composable
    override fun render() {
        Card(
            backgroundColor = Colors.CARD_BASE.color,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(12.dp, 6.dp)
                .pointerHoverIcon(icon = PointerIcon.Hand),
            elevation = 0.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = text,
                    fontWeight = FontWeight.Medium,
                    color = Colors.TEXT.color,
                    modifier = Modifier.padding(12.dp, 0.dp)
                )
            }
        }
    }

}