package com.zer0s2m.creeptenuous.desktop.core.actions

import androidx.compose.runtime.State
import com.zer0s2m.creeptenuous.desktop.core.navigation.NavigationController
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen

/**
 * Basic interface for all kinds of actions for system elements,
 * such as - `navigation`
 */
fun interface ActionNavigation {

    /**
     * Perform an action to change the current screen
     *
     * @param state The current state of the screen state handler
     * @param route Name of the screen to go to it [Screen]
     */
    fun action(state: State<NavigationController>, route: Screen)

}
