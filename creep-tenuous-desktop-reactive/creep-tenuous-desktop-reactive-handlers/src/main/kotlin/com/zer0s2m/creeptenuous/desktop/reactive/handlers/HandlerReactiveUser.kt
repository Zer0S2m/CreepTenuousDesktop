package com.zer0s2m.creeptenuous.desktop.reactive.handlers

import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.reactive.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

internal object HandlerReactiveUser : ReactiveHandlerKtor {

    /**
     * The handler type is needed for [NodeType.KTOR].
     *
     * It is necessary to have in each property that is annotated [Reactive] or [Lazy] for linking nodes
     *
     * @return result
     */
    override suspend fun handlerKtor(): HttpResponse {
        return HttpClient.client.get("/api/v1/user/profile")
    }

}
