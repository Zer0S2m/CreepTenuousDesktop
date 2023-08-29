package com.zer0s2m.creeptenuous.desktop.core.handlers

import com.zer0s2m.creeptenuous.desktop.common.dto.*
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.reactive.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse

/**
 * Lazy handler for getting data about file object distribution settings
 */
object HandlerUserSettingsFileObjectDistribution :
    ReactiveHandler<UserSettingsFileObjectDistribution>,
    ReactiveHandlerKtor by HandlerReactiveUser {

    /**
     * Process reactive property
     *
     * @return result
     */
    override suspend fun handler(): UserSettingsFileObjectDistribution {
        return HttpClient.client.get("/api/v1/user/profile").body()
    }

}

/**
 * Reactive handler for getting data about custom categories
 */
object HandlerReactiveUserCustomCategories : ReactiveHandler<MutableCollection<UserCategory>> {

    /**
     * Process reactive property
     *
     * @return result
     */
    override suspend fun handler(): MutableCollection<UserCategory> {
        return HttpClient.client.get("/api/v1/user/category").body()
    }

}

/**
 * Reactive handler for getting data about granted rights
 */
object HandlerReactiveUserGrantedRights : ReactiveHandler<GrantedRight> {

    /**
     * Process reactive property
     *
     * @return result
     */
    override suspend fun handler(): GrantedRight {
        return HttpClient.client.get("/api/v1/user/global/right/list-all").body()
    }

}

/**
 * Reactive handler for getting data about user parameters
 */
object HandlerReactiveUserProfileSettings :
    ReactiveHandler<UserProfileSettings>,
    ReactiveHandlerKtor by HandlerReactiveUser {

    /**
     * Process reactive property
     *
     * @return result
     */
    override suspend fun handler(): UserProfileSettings {
        return HttpClient.client.get("/api/v1/user/profile").body()
    }

}

private object HandlerReactiveUser : ReactiveHandlerKtor {

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

/**
 * Reactive handler for getting custom color data
 */
object HandlerReactiveUserColor : ReactiveHandler<MutableCollection<UserColor>> {

    /**
     * Process reactive property
     *
     * @return result
     */
    override suspend fun handler(): MutableCollection<UserColor> {
        return HttpClient.client.get("/api/v1/user/customization/color").body()
    }

}
