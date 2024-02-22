package com.zer0s2m.creeptenuous.desktop.reactive.handlers

import com.zer0s2m.creeptenuous.desktop.common.dto.User
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveHandler
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveMutableList
import com.zer0s2m.creeptenuous.desktop.core.reactive.toReactiveMutableList
import com.zer0s2m.creeptenuous.desktop.reactive.triggers.common.ReactiveTriggerReactiveSystemUserRemove
import io.ktor.client.call.*
import io.ktor.client.request.*

/**
 * Reactive behavior handler to get list of system users
 */
object HandlerReactiveCommonUsers : ReactiveHandler<ReactiveMutableList<User>> {

    /**
     * Process reactive property
     *
     * @return users
     */
    override suspend fun handler(): ReactiveMutableList<User> {
        val users: MutableCollection<User> = HttpClient.client.get("/api/v1/user/control/list") {
            header("Authorization", "Bearer ${HttpClient.accessToken}")
        }.body()
        return users.toReactiveMutableList(
            triggerRemove = ReactiveTriggerReactiveSystemUserRemove()
        )
    }

}
