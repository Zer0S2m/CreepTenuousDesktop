package com.zer0s2m.creeptenuous.desktop.core.handlers

import com.zer0s2m.creeptenuous.desktop.common.dto.GrantedRight
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveHandler
import com.zer0s2m.creeptenuous.desktop.common.dto.UserCategory
import com.zer0s2m.creeptenuous.desktop.common.dto.UserProfileSettings
import com.zer0s2m.creeptenuous.desktop.common.dto.UserSettingsFileObjectDistribution
import io.ktor.client.call.*
import io.ktor.client.request.*

/**
 * Lazy handler for getting data about file object distribution settings
 */
object HandlerUserSettingsFileObjectDistribution : ReactiveHandler<UserSettingsFileObjectDistribution> {

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
object HandlerReactiveUserProfileSettings : ReactiveHandler<UserProfileSettings> {

    /**
     * Process reactive property
     *
     * @return result
     */
    override suspend fun handler(): UserProfileSettings {
        return HttpClient.client.get("/api/v1/user/profile").body()
    }

}
