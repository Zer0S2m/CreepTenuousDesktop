package com.zer0s2m.creeptenuous.desktop.core.actions

import androidx.compose.runtime.Stable

/**
 * Navigation action to change screen state
 */
@Stable
val navigationScreen = ActionNavigation { state, route ->
    state.value.navigate(route.name)
}
