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

    BREAD_CRUMBS_BASE(Color(red = 225, green = 229, blue = 234)),

    CARD_BASE_HOVER(Color(red = 220, green = 222, blue = 228)),

    DROP_MENU_ITEM_HOVER(Color(red = 238, green = 240, blue = 244)),

    TEXT(Color(red = 27, green = 31, blue = 31)),

    CARD_SECTION_PROFILE_HOVER(Color(red = 54,green = 54,blue = 54)),

    SECONDARY_VARIANT(Color(red = 219, green = 216, blue = 227))

}