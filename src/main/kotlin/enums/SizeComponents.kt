package enums

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp

/**
 * System component size storage
 *
 * @param size Size as a [Number]
 */
enum class SizeComponents(val size: Number) {

    WIDTH_FIELD(240),

    WIDTH_BUTTON(240),

    LEFT_PANEL_DASHBOARD(0.225),

}

/**
 * Create a [Dp] using an [SizeComponents]
 */
@Stable
inline val SizeComponents.dp: Dp get() = Dp(value = this.size.toFloat())

/**
 * Create a [float] using an [SizeComponents]
 */
@Stable
inline val SizeComponents.float: Float get() = this.size.toFloat()
