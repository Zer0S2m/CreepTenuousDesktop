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
            context[screen] = mutableMapOf(key to value)
        }
    }

    /**
     * Set data to a specific area.
     *
     * @param screen The area of the screen for which the value will be set.
     * @param map Hash data map.
     */
    fun set(screen: Screen, map: Map<String, Any?>) {
        if (context.containsKey(screen)) {
            context[screen]?.putAll(map)
        } else {
            context[screen] = map.toMutableMap()
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

    /**
     * Check the presence of a property in the screen using the key.
     *
     * @param screen The area of the screen from which data will be taken.
     * @param key Property name.
     */
    fun containsValueByKey(screen: Screen, key: String): Boolean {
        return context[screen]?.containsKey(key) ?: false
    }

    /**
     * Clear screen state.
     *
     * @param screen The area of the screen from which data will be taken.
     */
    fun clearScreen(screen: Screen) {
        context[screen] = mutableMapOf()
    }

    /**
     * Removes the specified key and its corresponding value from the selected screen.
     *
     * @param screen The area of the screen from which data will be taken.
     * @param key Property name.
     */
    fun clearValueByKey(screen: Screen, key: String) {
        context[screen]?.remove(key)
    }

    /**
     * Removes the specified key and its corresponding value from the selected screen.
     *
     * @param screen The area of the screen from which data will be taken.
     * @param key Property names.
     */
    fun clearValueByKey(screen: Screen, key: Iterable<String>) {
        key.forEach { context[screen]?.remove(it) }
    }

}