package com.zer0s2m.creeptenuous.desktop.core.triggers

import com.zer0s2m.creeptenuous.desktop.core.reactive.Lazy
import com.zer0s2m.creeptenuous.desktop.core.reactive.Reactive
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLazyObject

/**
 * The main interface for implementing a reactive trigger.
 *
 * Use it on properties annotated with [Reactive] or [Lazy] and also if the class
 * implements [ReactiveLazyObject].
 *
 * Performs the role of changing a value in properties.
 *
 * @param T The type of the variable that will take [BaseReactiveTrigger.execution].
 */
interface BaseReactiveTrigger<T> {

    /**
     * Trigger execution.
     *
     * @param value The new value of a property or object.
     */
    suspend fun execution(value: T) {}

    /**
     * Trigger execution.
     *
     * @param oldValue The old value of a property or object.
     * @param newValue The new value of a property or object.
     */
    suspend fun execution(oldValue: T, newValue: T) {}

}
