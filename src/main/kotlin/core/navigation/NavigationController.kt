package core.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class NavigationController(
    private val startDestination: String,
    private var backStackScreen: MutableSet<String> = mutableSetOf()
) {

    var currentScreen: MutableState<String> = mutableStateOf(startDestination)

    fun navigate(route: String) {
        if (route != currentScreen.value) {
            if (backStackScreen.contains(currentScreen.value) && currentScreen.value != startDestination) {
                backStackScreen.remove(currentScreen.value)
            }

            if (route == startDestination) {
                backStackScreen = mutableSetOf()
            } else {
                backStackScreen.add(currentScreen.value)
            }

            currentScreen.value = route
        }
    }

    fun navigateBack() {
        if (backStackScreen.isNotEmpty()) {
            currentScreen.value = backStackScreen.last()
            backStackScreen.remove(currentScreen.value)
        }
    }

}

