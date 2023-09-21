package com.zer0s2m.creeptenuous.desktop.core.context

import com.zer0s2m.creeptenuous.desktop.common.enums.Screen

/**
 * A primitive context for storing data for different types of screens
 */
object ContextScreen {

    val context: MutableMap<Screen, MutableMap<String, Any?>> = mutableMapOf()

    /**
     * Set data to a specific area.
     *
     * @param screen The area of the screen for which the value will be set.
     * @param key Property name.
     * @param value Data.
     */
    fun set(screen: Screen, key: String, value: Any?) {
        if (context.containsKey(screen)) {
            context[screen]?.set(key, value)
        } else {
            context[screen] = mutableMapOf(
                key to value
            )
        }
    }

    /**
     * Get data from a specific screen.
     *
     * @param screen The area of the screen from which data will be taken.
     * @param key Property name.
     */
    inline fun <reified T> get(screen: Screen, key: String): T {
        return context[screen]?.get(key) as T
    }

}