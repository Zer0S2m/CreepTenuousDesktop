package enums

import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color

/**
 * Color `enums` contain information
 *
 * @param color The `Color` class contains color information to be used while painting in [Canvas]
 */
enum class Colors(val color: Color) {

    CARD_BASE(Color(red = 225, green = 229, blue = 234)),

    TEXT(Color(red = 27, green = 31, blue = 31))

}