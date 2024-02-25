package com.zer0s2m.creeptenuous.desktop.core.context

import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.core.reactive.Lazy
import com.zer0s2m.creeptenuous.desktop.core.reactive.Reactive
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLoader

/**
 * Main context for loading [Reactive] and [Lazy] properties via navigation elements
 */
object ReactiveContextRouting : BaseReactiveContextRouting {

    /**
     * The main card for storing information what data to download when changing
     * the current state of the screen by switching to a new screen
     */
    private val map: MutableMap<Screen, Collection<String>> = mutableMapOf()

    /**
     * Load data when navigating to a new screen via a navigation element
     *
     * @param screen Screen for which data will be loaded
     */
    override suspend fun load(screen: Screen) {
        if (map.containsKey(screen) && map[screen]?.isNotEmpty() == true) {
            map[screen]?.forEach {
                ReactiveLoader.load(it)
            }
            map.remove(screen)
        }
    }

    /**
     * Set objects to the context for their subsequent loading when switching to the installed screen
     *
     * @param screen Screen to go to it
     * @param objects The name of the properties that are marked with an annotation [Reactive] or [Lazy]
     */
    override fun setObjectsToContext(screen: Screen, objects: Collection<String>) {
        map[screen] = objects
    }

}
