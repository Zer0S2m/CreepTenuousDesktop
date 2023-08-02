package com.zer0s2m.creeptenuous.desktop.core.reactive

import com.zer0s2m.creeptenuous.desktop.core.handlers.HandlerUserSettingsFileObjectDistribution
import com.zer0s2m.creeptenuous.desktop.core.handlers.HandlerReactiveUserCustomCategories
import com.zer0s2m.creeptenuous.desktop.common.dto.UserCategory
import com.zer0s2m.creeptenuous.desktop.common.dto.UserSettingsFileObjectDistribution

/**
 * `Reactive` behavior user model
 */
object ReactiveUser : ReactiveLazyObject {

    /**
     * Custom categories for the user
     */
    @Reactive<MutableCollection<UserCategory>>(
        handler = HandlerReactiveUserCustomCategories::class
    )
    var customCategories: MutableCollection<UserCategory> = mutableListOf()

    /**
     * Storage of user settings
     */
    object UserSettings : ReactiveLazyObject {

        /**
         * Basic settings for the distribution of file objects
         */
        @Lazy<UserSettingsFileObjectDistribution>(
            event = "Go to the user settings in the section - File object distribution settings",
            handler = HandlerUserSettingsFileObjectDistribution::class
        )
        var userSettingsFileObjectDistribution: UserSettingsFileObjectDistribution =
            UserSettingsFileObjectDistribution(null, null)
            set(settings) {
                field = settings
            }

    }

    object GrantedRights : ReactiveLazyObject

}
