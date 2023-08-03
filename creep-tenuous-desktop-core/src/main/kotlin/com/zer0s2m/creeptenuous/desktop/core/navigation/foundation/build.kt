package com.zer0s2m.creeptenuous.desktop.core.navigation.foundation

import androidx.compose.runtime.Composable
import com.zer0s2m.creeptenuous.desktop.core.navigation.NavigationHost

@Composable
fun NavigationHost.NavigationGraphBuilder.composable(
    route: String,
    content: @Composable () -> Unit
) {
    if (navigationControllerGraph.currentScreen.value == route) {
        content()
    }
}