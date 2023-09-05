package com.zer0s2m.creeptenuous.desktop.ui.components.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zer0s2m.creeptenuous.desktop.common.enums.Resources
import com.zer0s2m.creeptenuous.desktop.ui.animations.setAnimateColorAsStateInCard
import com.zer0s2m.creeptenuous.desktop.ui.animations.setHoverInCard
import com.zer0s2m.creeptenuous.desktop.ui.components.base.BaseCardPanelBaseFolderUser

/**
 * Component on the left side of the dashboard to show the user's base directories
 *
 * @param text The text to be displayed
 * @param isAnimation Set background change animation for a component
 * @param isIcon Set an icon for a component
 * @param iconPath Path to icons [Resources]
 * @param action Callback to be called when the [Card] is clicked
 */
class CardPanelBaseFolderUser(
    override val text: String = "",
    override val isAnimation: Boolean = true,
    override val isIcon: Boolean = false,
    override val iconPath: String? = null,
    override val action: () -> Unit
) : BaseCardPanelBaseFolderUser {

    /**
     * Text used by accessibility services to describe what this image represents
     */
    private val contentDescriptionIcon: String = "Icon displayed meaning directory"

    /**
     * Component rendering
     */
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun render() {
        val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
        val isHover: MutableState<Boolean> = remember { mutableStateOf(false) }
        val animatedButtonColor = setAnimateColorAsStateInCard(
            isHover = isHover
        )

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
                .height(52.dp)
                .hoverable(interactionSource = interactionSource)
                .pointerHoverIcon(icon = PointerIcon.Hand),
            elevation = 0.dp,
            shape = RoundedCornerShape(0.dp),
            contentColor = Color.Black,
            onClick = action
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 0.dp),
                verticalArrangement = Arrangement.Center
            ) {
                if (!isIcon) {
                    renderText()
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        iconPath?.let { painterResource(resourcePath = it) }?.let {
                            Image(
                                painter = it,
                                contentDescription = contentDescriptionIcon,
                                modifier = Modifier
                                    .height(24.dp)
                                    .width(24.dp)
                            )
                        }
                        renderText()
                    }
                }
            }
        }
    }

    @Composable
    private fun renderText() {
        Text(
            text = text,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            modifier = Modifier.padding(12.dp, 0.dp)
        )
    }

}