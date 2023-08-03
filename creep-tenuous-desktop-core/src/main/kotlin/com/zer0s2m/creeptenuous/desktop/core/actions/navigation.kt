package com.zer0s2m.creeptenuous.desktop.core.actions

import com.zer0s2m.creeptenuous.desktop.core.context.ReactiveContextRouting
import com.zer0s2m.creeptenuous.desktop.core.reactive.Reactive
import com.zer0s2m.creeptenuous.desktop.core.reactive.Lazy
import kotlinx.coroutines.launch

/**
 * Navigation action to change screen state
 */
val navigationScreen = ActionNavigation { state, route, scope ->
    scope.launch {
        state.value.navigate(route.name)
    }
}

/**
 * Transition to a new screen state after loading [Reactive] and [Lazy] objects
 */
val reactiveNavigationScreen = ReactiveActionNavigation { state, route, objects, scope ->
    if (objects.isNotEmpty()) {
        ReactiveContextRouting.setObjectsToContext(route, objects)
    }
    navigationScreen.action(state, route, scope)
}
