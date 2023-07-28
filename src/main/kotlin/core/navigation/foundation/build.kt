package core.navigation.foundation

import androidx.compose.runtime.Composable
import core.navigation.NavigationHost

@Composable
fun NavigationHost.NavigationGraphBuilder.composable(
    route: String,
    content: @Composable () -> Unit
) {
    if (navigationControllerGraph.currentScreen.value == route) {
        content()
    }
}