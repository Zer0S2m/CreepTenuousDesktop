package core.actions

import androidx.compose.runtime.State
import core.navigation.NavigationController
import enums.Screen

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
