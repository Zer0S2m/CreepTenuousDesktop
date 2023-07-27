package enums

/**
 * Application screen storage
 */
enum class Screen {

    /**
     * User authorization screen
     */
    LOGIN_SCREEN,

    /**
     * Basic settings screen for connecting to the main system
     */
    SETTINGS_SYSTEM_SCREEN,

    /**
     * Main screen for interacting with file objects
     */
    DASHBOARD_SCREEN,

    /**
     * Main screen for adjusting settings by users
     */
    PROFILE_SCREEN,

    /**
     * Screen by interaction category for allocating file objects
     */
    PROFILE_CATEGORY_SCREEN,

    /**
     * Screen for viewing granted rights for interacting with file objects
     */
    PROFILE_GRANTED_RIGHTS_SCREEN,

    /**
     * Screen for administering users in the system
     */
    PROFILE_USER_MANAGEMENT_SCREEN,

    /**
     * Screen for setting preferences for allocating file objects
     */
    PROFILE_FILE_OBJECT_DISTRIBUTION,

    /**
     * Screen for customizing file objects
     */
    PROFILE_COLORS_SCREEN;

    /**
     * List of child elements of parent
     */
    val childs: Collection<Screen>
        get() = when (this) {
            PROFILE_SCREEN -> listOf(
                PROFILE_CATEGORY_SCREEN,
                PROFILE_GRANTED_RIGHTS_SCREEN,
                PROFILE_USER_MANAGEMENT_SCREEN,
                PROFILE_FILE_OBJECT_DISTRIBUTION,
                PROFILE_COLORS_SCREEN
            )

            else -> { listOf() }
        }

}
