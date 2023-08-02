package com.zer0s2m.creeptenuous.desktop.core.handlers

import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveHandler
import com.zer0s2m.creeptenuous.desktop.common.dto.User
import io.ktor.client.call.*
import io.ktor.client.request.get

/**
 * Reactive behavior handler to get list of system users
 */
object HandlerReactiveCommonUsers : ReactiveHandler<List<User>> {

    /**
     * Process reactive property
     *
     * @return users
     */
    override suspend fun handler(): List<User> {
        return HttpClient.client.get("/api/v1/user/control/list").body()
    }

}
