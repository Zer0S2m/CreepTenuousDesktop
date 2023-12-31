package com.zer0s2m.creeptenuous.desktop.ui.animations

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.zer0s2m.creeptenuous.desktop.ui.components.base.BaseDropdownMenuItem
import com.zer0s2m.creeptenuous.desktop.ui.misc.Colors

/**
 * Set hover event on component [Card] - listens for events [HoverInteraction]
 *
 * @param interactionSource [MutableInteractionSource] that will be used to emit [HoverInteraction]
 * @param isHover Switch in the form of a state when hovering over a component
 */
@Composable
fun setHoverInCard(
    interactionSource: MutableInteractionSource,
    isHover: MutableState<Boolean>
) {
    setHoverInComponent(
        interactionSource = interactionSource,
        isHover = isHover
    )
}

/**
 * Set hover event on component [BaseDropdownMenuItem] - listens for events [HoverInteraction]
 *
 * @param interactionSource [MutableInteractionSource] that will be used to emit [HoverInteraction]
 * @param isHover Switch in the form of a state when hovering over a component
 */
@Composable
fun setHoverInDropMenuItem(
    interactionSource: MutableInteractionSource,
    isHover: MutableState<Boolean>
) {
    setHoverInComponent(
        interactionSource = interactionSource,
        isHover = isHover
    )
}

/**
 * Set hover event on component [BaseDropdownMenuItem] - listens for events [HoverInteraction]
 *
 * @param interactionSource [MutableInteractionSource] that will be used to emit [HoverInteraction]
 * @param isHover Switch in the form of a state when hovering over a component
 */
@Composable
fun setHoverInSelectUser(
    interactionSource: MutableInteractionSource,
    isHover: MutableState<Boolean>
) {
    setHoverInComponent(
        interactionSource = interactionSource,
        isHover = isHover
    )
}

/**
 * Set hover event on component - listens for events [HoverInteraction]
 *
 * @param interactionSource [MutableInteractionSource] that will be used to emit [HoverInteraction]
 * @param isHover Switch in the form of a state when hovering over a component
 */
@Composable
private fun setHoverInComponent(
    interactionSource: MutableInteractionSource,
    isHover: MutableState<Boolean>
) {
    val interactions = remember { mutableStateListOf<Interaction>() }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is HoverInteraction.Enter -> {
                    interactions.add(interaction)
                    isHover.value = true
                }
                is HoverInteraction.Exit -> {
                    interactions.add(interaction)
                    isHover.value = false
                }
            }
        }
    }
}

/**
 * Set color change states for events [HoverInteraction] (used together with [setHoverInCard])
 *
 * @param isHover Switch in the form of a state when hovering over a component
 * @param backgroundColor Component's initial background
 * @param backgroundHover Background on element hover
 */
@Composable
fun setAnimateColorAsStateInCard(
    isHover: MutableState<Boolean>,
    backgroundColor: Color = Colors.CARD_BASE.color,
    backgroundHover: Color = Colors.CARD_BASE_HOVER.color
): State<Color> {
    return animateColorAsState(
        targetValue = if (isHover.value) backgroundHover else backgroundColor,
        animationSpec = tween(0, 0, LinearEasing)
    )
}

/**
 * Set color change states for events [HoverInteraction] (used together with [setHoverInDropMenuItem])
 *
 * @param isHover Switch in the form of a state when hovering over a component
 */
@Composable
fun setAnimateColorAsStateInDropMenuItem(
    isHover: State<Boolean>
): State<Color> {
    return animateColorAsState(
        targetValue = if (isHover.value) Colors.DROP_MENU_ITEM_HOVER.color else Color.White,
        animationSpec = tween(0, 0, LinearEasing)
    )
}

/**
 * Set color change states for events [HoverInteraction] (used together with [setHoverInSelectUser])
 *
 * @param isHover Switch in the form of a state when hovering over a component
 * @param backgroundColor Component's initial background
 * @param backgroundHover Background on element hover
 */
@Composable
fun setAnimateColorAsStateInSelectUser(
    isHover: MutableState<Boolean>,
    backgroundColor: Color = Color.White,
    backgroundHover: Color = Colors.CARD_BASE_HOVER.color
): State<Color> {
    return animateColorAsState(
        targetValue = if (isHover.value) backgroundHover else backgroundColor,
        animationSpec = tween(0, 0, LinearEasing)
    )
}
