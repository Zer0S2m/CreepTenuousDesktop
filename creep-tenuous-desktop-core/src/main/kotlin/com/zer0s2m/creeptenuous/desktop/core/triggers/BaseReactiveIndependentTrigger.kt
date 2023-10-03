package com.zer0s2m.creeptenuous.desktop.core.triggers

import com.zer0s2m.creeptenuous.desktop.core.reactive.Lazy
import com.zer0s2m.creeptenuous.desktop.core.reactive.Reactive
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLazyObject

/**
 * Basic interface for implementing an independent reactive trigger.
 *
 * Use it on properties annotated with [Reactive] or [Lazy] and also if the class
 * implements [ReactiveLazyObject].
 *
 * Performs the role of changing a value in properties.
 */
interface BaseReactiveIndependentTrigger {

    /**
     * Trigger execution.
     *
     * @param values Arbitrary number of arguments passed regardless of type
     */
    fun execution(vararg values: Any?)

}