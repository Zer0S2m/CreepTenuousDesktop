package com.zer0s2m.creeptenuous.desktop.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zer0s2m.creeptenuous.desktop.common.dto.UserColor
import com.zer0s2m.creeptenuous.desktop.common.utils.colorConvertHexToRgb

/**
 * Component responsible for the color of the user category
 *
 * @param color Dirty color as a string
 * @param size Declare the preferred size of the content to be exactly [size]dp square
 */
@Composable
fun CircleCategoryBox(
    color: String,
    size: Dp
) {
    val converterColor = colorConvertHexToRgb(color)

    Box(
        modifier = Modifier
            .size(size)
            .background(
                Color(
                    red = converterColor.red,
                    green = converterColor.green,
                    blue = converterColor.blue,
                ),
                CircleShape
            )
    )
}

/**
 * Display the custom color palette component.
 *
 * @param userColor User category information.
 * @param modifier Concatenates this modifier with another.
 * @param shape Desired shape of the background.
 */
@Composable
fun LayoutUserColor(
    userColor: UserColor,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(0.dp)
) {
    val converterColor = colorConvertHexToRgb(userColor.color)

    Box(
        modifier = Modifier
            .then(modifier)
            .background(
                Color(
                    red = converterColor.red,
                    green = converterColor.green,
                    blue = converterColor.blue,
                ),
                shape
            )
    )
}
