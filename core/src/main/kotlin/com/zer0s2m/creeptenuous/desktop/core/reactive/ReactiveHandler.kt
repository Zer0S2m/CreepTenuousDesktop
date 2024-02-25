package com.zer0s2m.creeptenuous.desktop.core.reactive

/**
 * Base interface for a reactive behavior handler
 *
 * @param T Return type
 */
fun interface ReactiveHandler<T> : ReactiveHandlerKtor {

    /**
     * Process reactive property
     *
     * @return result
     */
    suspend fun handler(): T

}
