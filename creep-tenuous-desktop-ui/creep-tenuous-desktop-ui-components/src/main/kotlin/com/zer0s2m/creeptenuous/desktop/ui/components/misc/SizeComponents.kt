package com.zer0s2m.creeptenuous.desktop.ui.components.misc

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import com.zer0s2m.creeptenuous.desktop.common.enums.SizeComponents

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
