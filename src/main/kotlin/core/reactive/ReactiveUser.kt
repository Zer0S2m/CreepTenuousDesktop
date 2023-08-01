package core.reactive

import core.handlers.HandlerUserSettingsFileObjectDistribution
import core.handlers.HandlerReactiveUserCustomCategories
import dto.UserCategory
import dto.UserSettingsFileObjectDistribution

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

    object GrantedRights : ReactiveLazyObject {

    }

}
