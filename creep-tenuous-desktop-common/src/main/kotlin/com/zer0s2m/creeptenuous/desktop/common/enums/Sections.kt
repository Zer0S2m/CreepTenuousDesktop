package com.zer0s2m.creeptenuous.desktop.common.enums

/**
 * The main storage of the main names of navigation blocks
 *
 * @param title Name of the sectional block
 * @param sections Basic name blocks
 * @param routes Routes to change the current state of the screen through an action
 * @param objects The name of the properties that are marked with an annotation Reactive or Lazy
 */
enum class Sections(
    val title: String,
    val sections: List<String>,
    val routes: List<Screen>,
    val objects: Map<Screen, Collection<String>> = mapOf()
) {

    /**
     * Main sections for storing user settings for system regulation
     */
    MAIN_PROFILE(
        title = "Main",
        sections = listOf(
            "File object distribution settings",
            "Settings",
            "Viewing granted rights"
        ),
        routes = listOf(
            Screen.PROFILE_FILE_OBJECT_DISTRIBUTION,
            Screen.PROFILE_SETTINGS_SCREEN,
            Screen.PROFILE_GRANTED_RIGHTS_SCREEN
        ),
        objects = mapOf(
            Screen.PROFILE_FILE_OBJECT_DISTRIBUTION to listOf(
                "userSettingsFileObjectDistribution"
            )
        )
    ),

    /**
     * Basic blocks for user control
     */
    USER_CONTROL(
        title = "User control",
        sections = listOf(
            "List of users",
            "User management"
        ),
        routes = listOf(
            Screen.PROFILE_LIST_USERS_SCREEN,
            Screen.PROFILE_USER_MANAGEMENT_SCREEN
        )
    ),

    /**
     * Basic blocks for customizing file objects
     */
    USER_CUSTOMIZATION(
        title = "Customization",
        sections = listOf(
            "Categories",
            "Colors"
        ),
        routes = listOf(
            Screen.PROFILE_CATEGORY_SCREEN,
            Screen.PROFILE_COLORS_SCREEN
        )
    )

}
