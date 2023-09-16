package com.zer0s2m.creeptenuous.desktop.reactive.handlers

import com.zer0s2m.creeptenuous.desktop.common.dto.UserCategory
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveHandler
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveMutableList
import com.zer0s2m.creeptenuous.desktop.core.reactive.toReactiveMutableList
import com.zer0s2m.creeptenuous.desktop.reactive.triggers.ReactiveTriggerUserCategoryAdd
import com.zer0s2m.creeptenuous.desktop.reactive.triggers.ReactiveTriggerUserCategoryRemove
import com.zer0s2m.creeptenuous.desktop.reactive.triggers.ReactiveTriggerUserCategorySet
import io.ktor.client.call.*
import io.ktor.client.request.*

/**
 * Reactive handler for getting data about custom categories
 */
object HandlerReactiveUserCustomCategories : ReactiveHandler<ReactiveMutableList<UserCategory>> {

    /**
     * Process reactive property
     *
     * @return result
     */
    override suspend fun handler(): ReactiveMutableList<UserCategory> {
        val data: MutableCollection<UserCategory> = HttpClient.client.get("/api/v1/user/category").body()
        return data.toReactiveMutableList(
            triggerAdd = ReactiveTriggerUserCategoryAdd(),
            triggerRemove = ReactiveTriggerUserCategoryRemove(),
            triggerSet = ReactiveTriggerUserCategorySet()
        )
    }

}
