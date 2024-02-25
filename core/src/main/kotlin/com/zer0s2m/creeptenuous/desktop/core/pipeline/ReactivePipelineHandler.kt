package com.zer0s2m.creeptenuous.desktop.core.pipeline

import com.zer0s2m.creeptenuous.desktop.core.reactive.Lazy
import com.zer0s2m.creeptenuous.desktop.core.reactive.Reactive
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveIndependentTrigger
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveTrigger

/**
 * Reactive pipeline handler after or before executing a reactive trigger.
 * [BaseReactiveIndependentTrigger] or [BaseReactiveTrigger].
 *
 * @param T The type of data that is returned after the reactive handler specified in
 * [Lazy.handler] or [Reactive.handler]
 */
interface ReactivePipelineHandler<T> {

    /**
     * Jet pipeline launch.
     *
     * @param value Data.
     */
    fun launch(value: T)

}
