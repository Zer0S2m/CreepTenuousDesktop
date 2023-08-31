package com.zer0s2m.creeptenuous.desktop.core.reactive

import io.ktor.client.statement.*

/**
 * The main interface for implementing a node handler under type [NodeType.KTOR].
 */
interface ReactiveHandlerKtor {

    /**
     * The handler type is needed for [NodeType.KTOR].
     *
     * It is necessary to have in each property that is annotated [Reactive] or [Lazy] for linking nodes
     *
     * @return result
     */
    suspend fun handlerKtor(): HttpResponse? {
        return null
    }

}
