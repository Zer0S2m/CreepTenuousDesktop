package components.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import components.base.BaseCardModalSheet
import enums.Colors

/**
 * The map component for the modal sheet.
 * Extends a component [Card]
 *
 * @param modifier Modifier to be applied to the layout of the card.
 * @param backgroundColor The background color.
 */
class CardModalSheet(
    private val modifier: Modifier = Modifier
        .fillMaxSize(),
    private val backgroundColor: Color = Colors.CARD_BASE.color
) : BaseCardModalSheet {

    /**
     * Component rendering
     *
     * @param content Content of your screen
     */
    @Composable
    override fun render(content: @Composable () -> Unit) {
        Card(
            modifier = modifier,
            elevation = 0.dp,
            shape = RoundedCornerShape(8.dp),
            backgroundColor = backgroundColor
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                content()
            }
        }
    }

}