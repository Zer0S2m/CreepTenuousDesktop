package com.zer0s2m.creeptenuous.desktop.core.context

import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.core.reactive.Reactive
import com.zer0s2m.creeptenuous.desktop.core.reactive.Lazy
import com.zer0s2m.creeptenuous.desktop.core.navigation.NavigationController

/**
 * Interface to implement the main context for loading [Reactive] and [Lazy] properties
 * via navigation elements [NavigationController]
 */
interface BaseReactiveContextRouting {

    /**
     * Load data when navigating to a new screen via a navigation element [NavigationController.navigate]
     *
     * @param screen Screen for which data will be loaded
     */
    suspend fun load(screen: Screen)

    /**
     * Set objects to the context for their subsequent loading when switching to the installed screen
     *
     * @param screen Screen to go to it
     * @param objects The name of the properties that are marked with an annotation [Reactive] or [Lazy]
     */
    fun setObjectsToContext(screen: Screen, objects: Collection<String>)

}
