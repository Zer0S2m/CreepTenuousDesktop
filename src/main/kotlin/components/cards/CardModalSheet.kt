package components.cards

import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import components.animations.setAnimateColorAsStateInCard
import components.animations.setHoverInCard
import components.base.BaseCardModalSheet
import enums.Colors

/**
 * The map component for the modal sheet.
 * Extends a component [Card]
 *
 * @param modifier Modifier to be applied to the layout of the card.
 * @param backgroundColor The background color.
 * @param isAnimation Set background change animation for a component.
 */
class CardModalSheet(
    private var modifier: Modifier = Modifier
        .fillMaxSize(),
    private val backgroundColor: Color = Colors.CARD_BASE.color,
    override val isAnimation: Boolean = true
) : BaseCardModalSheet {

    /**
     * Component rendering
     *
     * @param content Content of your screen
     */
    @Composable
    override fun render(content: @Composable () -> Unit) {
        val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
        val isHover: MutableState<Boolean> = remember { mutableStateOf(false) }
        val animatedCardColor = setAnimateColorAsStateInCard(isHover = isHover)

        if (isAnimation) {
            modifier = modifier
                .hoverable(interactionSource = interactionSource)
            setHoverInCard(
                interactionSource = interactionSource,
                isHover = isHover
            )
        }

        Card(
            modifier = modifier,
            elevation = 0.dp,
            shape = RoundedCornerShape(8.dp),
            backgroundColor = if (isAnimation) animatedCardColor.value else backgroundColor
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