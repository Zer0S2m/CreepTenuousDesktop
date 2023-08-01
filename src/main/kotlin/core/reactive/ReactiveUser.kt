package core.reactive

import core.handlers.HandlerLazyUserSettingsDeleteFiles
import core.handlers.HandlerLazyUserSettingsPassingFiles
import core.handlers.HandlerReactiveUserCustomCategories
import dto.User
import dto.UserCategory

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
         * Whether to delete file objects when deleting a user
         */
        @Lazy<Boolean?>(
            event = "Go to the user settings in the section - File object distribution settings",
            handler = HandlerLazyUserSettingsDeleteFiles::class
        )
        var isDeletingFilesWhenDeletingUser: Boolean? = null
            set(isDeleting) {
                field = isDeleting
            }

        /**
         * Transfer file objects to assigned user if their owner leaves
         */
        @Lazy<User?>(
            event = "Go to the user settings in the section - File object distribution settings",
            handler = HandlerLazyUserSettingsPassingFiles::class
        )
        var passingFilesToUser: User? = null
            set(user) {
                field = user
            }
    }

    object GrantedRights : ReactiveLazyObject {

    }

}
