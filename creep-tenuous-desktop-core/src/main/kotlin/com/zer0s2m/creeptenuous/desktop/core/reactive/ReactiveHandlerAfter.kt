package com.zer0s2m.creeptenuous.desktop.core.reactive

/**
 * The main interface for implementing handlers that will be executed after the data is [ReactiveHandler]
 */
interface ReactiveHandlerAfter {

    /**
     * Perform an action
     */
    suspend fun action()

}
