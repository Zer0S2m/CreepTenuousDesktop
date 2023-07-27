package enums

/**
 * The main storage of the main names of navigation blocks
 *
 * @param sections Basic name blocks
 */
enum class Sections(val sections: List<String>) {

    /**
     * Main sections for storing user settings for system regulation
     */
    MAIN_PROFILE(sections = listOf(
        "File object distribution settings",
        "Settings",
        "Viewing granted rights"
    )),

    /**
     * Basic blocks for user control
     */
    USER_CONTROL(sections = listOf(
        "List of users",
        "User management"
    )),

    /**
     * Basic blocks for customizing file objects
     */
    USER_CUSTOMIZATION(sections = listOf(
        "Categories",
        "Colors"
    ))

}
