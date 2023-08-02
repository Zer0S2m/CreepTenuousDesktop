package com.zer0s2m.creeptenuous.desktop.core.navigation.runtime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.zer0s2m.creeptenuous.desktop.core.navigation.NavigationController

@Composable
fun rememberNavigationController(
    startDestination: String,
    backStackScreen: MutableSet<String> = mutableSetOf()
): MutableState<NavigationController> = rememberSaveable {
    mutableStateOf(NavigationController(startDestination, backStackScreen))
}
