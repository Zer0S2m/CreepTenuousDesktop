package com.zer0s2m.creeptenuous.desktop.core.reactive

import com.zer0s2m.creeptenuous.desktop.common.dto.*
import com.zer0s2m.creeptenuous.desktop.core.handlers.*
import com.zer0s2m.creeptenuous.desktop.core.reactive.collections.ReactiveMutableList
import com.zer0s2m.creeptenuous.desktop.core.reactive.collections.mutableReactiveListOf

/**
 * `Reactive` behavior user model
 */
object ReactiveUser : ReactiveLazyObject {

    /**
     * Custom categories for the user
     */
    @Reactive<ReactiveMutableList<UserCategory>>(
        handler = HandlerReactiveUserCustomCategories::class
    )
    var customCategories: ReactiveMutableList<UserCategory> = mutableReactiveListOf()

    /**
     * Basic parameters for the user
     */
    @Reactive<UserProfileSettings>(
        handler = HandlerReactiveUserProfileSettings::class,
        node = Node(
            type = NodeType.KTOR,
            unit = "userProfile"
        )
    )
    var profileSettings: UserProfileSettings? = null

    /**
     * Custom colors
     */
    @Reactive<ReactiveMutableList<UserColor>>(
        handler = HandlerReactiveUserColor::class
    )
    var userColors: ReactiveMutableList<UserColor> = mutableReactiveListOf()

    /**
     * Storage of user settings
     */
    object UserSettings : ReactiveLazyObject {

        /**
         * Basic settings for the distribution of file objects
         */
        @Lazy<UserSettingsFileObjectDistribution>(
            event = "Go to the user profile in the section - File object distribution settings",
            handler = HandlerUserSettingsFileObjectDistribution::class,
            node = Node(
                type = NodeType.KTOR,
                unit = "userProfile"
            )
        )
        var userSettingsFileObjectDistribution: UserSettingsFileObjectDistribution =
            UserSettingsFileObjectDistribution(null, null)

    }

    /**
     * Storage of granted rights to file objects
     */
    object GrantedRights : ReactiveLazyObject {

        /**
         * Permissions granted to other users on file objects
         */
        @Lazy<GrantedRight>(
            event = "Go to the user profile in the section - Viewing granted rights",
            handler = HandlerReactiveUserGrantedRights::class
        )
        var grantedRightsFileObjects: GrantedRight = GrantedRight()

    }

}
