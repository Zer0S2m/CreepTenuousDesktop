package com.zer0s2m.creeptenuous.desktop.core.actions

import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLoader
import kotlinx.coroutines.CoroutineScope

/**
 * The basic interface for implementing a call to some action.
 *
 * Actions should only work with [ContextScreen] and [ReactiveLoader].
 */
interface Action {

    /**
     * Call action.
     *
     * @param scope Defines a scope for new coroutines.
     * @param values Additional data.
     */
    fun call(scope: CoroutineScope, vararg values: Any?)

}
