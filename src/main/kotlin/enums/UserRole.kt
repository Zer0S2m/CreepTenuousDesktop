package enums

/**
 * Role enumerations for users in the system
 *
 * @param title Name of the role to display in the interface
 */
enum class UserRole(val title: String) {

    ROLE_ADMIN(
        title = "ADMIN"
    ),

    ROLE_USER(
        title = "USER"
    )

}