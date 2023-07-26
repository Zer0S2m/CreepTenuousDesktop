package components.cards

import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import components.animations.setAnimateColorAsStateInCard
import components.animations.setHoverInCard
import components.base.BaseAnimation
import components.base.BaseComponent
import enums.Colors

/**
 * Component on the left side of the dashboard to show the user's base directories
 *
 * @param text The text to be displayed
 * @param isAnimation Set background change animation for a component
 */
class CardPanelBaseFolderUser(
    private val text: String = "",
    override val isAnimation: Boolean = true
) : BaseComponent, BaseAnimation {

    /**
     * Component rendering
     */
    @Composable
    override fun render() {
        val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
        val isHover: MutableState<Boolean> = remember { mutableStateOf(false) }
        val animatedButtonColor = setAnimateColorAsStateInCard(isHover = isHover)

        if (isAnimation) {
            setHoverInCard(
                interactionSource = interactionSource,
                isHover = isHover
            )
        }

        Card(
            backgroundColor = animatedButtonColor.value,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(12.dp, 6.dp)
                .hoverable(interactionSource = interactionSource)
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