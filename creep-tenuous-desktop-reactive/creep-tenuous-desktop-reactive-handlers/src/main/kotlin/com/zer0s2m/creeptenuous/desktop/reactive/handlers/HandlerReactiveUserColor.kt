package com.zer0s2m.creeptenuous.desktop.reactive.handlers

import com.zer0s2m.creeptenuous.desktop.common.dto.UserColor
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveHandler
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveMutableList
import com.zer0s2m.creeptenuous.desktop.core.reactive.toReactiveMutableList
import com.zer0s2m.creeptenuous.desktop.reactive.triggers.ReactiveTriggerUserColorAdd
import com.zer0s2m.creeptenuous.desktop.reactive.triggers.ReactiveTriggerUserColorRemove
import com.zer0s2m.creeptenuous.desktop.reactive.triggers.ReactiveTriggerUserColorSet
import io.ktor.client.call.*
import io.ktor.client.request.*

/**
 * Reactive handler for getting custom color data
 */
object HandlerReactiveUserColor : ReactiveHandler<ReactiveMutableList<UserColor>> {

    /**
     * Process reactive property
     *
     * @return result
     */
    override suspend fun handler(): ReactiveMutableList<UserColor> {
        val data: MutableCollection<UserColor> = HttpClient.client.get("/api/v1/user/customization/color").body()
        return data.toReactiveMutableList(
            triggerAdd = ReactiveTriggerUserColorAdd(),
            triggerRemove = ReactiveTriggerUserColorRemove(),
            triggerSet = ReactiveTriggerUserColorSet()
        )
    }

}
