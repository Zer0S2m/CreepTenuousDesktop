package com.zer0s2m.creeptenuous.desktop.core.actions

import androidx.compose.runtime.State
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.core.navigation.NavigationController
import com.zer0s2m.creeptenuous.desktop.core.reactive.Lazy
import com.zer0s2m.creeptenuous.desktop.core.reactive.Reactive
import kotlinx.coroutines.CoroutineScope

/**
 * Basic interface for implementing the transition to a
 * new [Screen] state after loading [Reactive] and [Lazy] objects
 */
fun interface ReactiveActionNavigation {

    /**
     * Perform an action to change the current screen and
     * complete the context for loading [Reactive] and [Lazy] objects
     *
     * @param state The current state of the screen state handler
     * @param route Name of the screen to go to it [Screen]
     * @param objects The name of the properties that are marked with an annotation [Reactive] or [Lazy]
     * @param scope Defines a scope for new coroutines
     */
    suspend fun action(
        state: State<NavigationController>, route: Screen, objects: Collection<String>,
        scope: CoroutineScope)

}