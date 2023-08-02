package dto

import enums.UserRole
import kotlinx.serialization.Serializable

/**
 * Base user in the system
 */
@Serializable
data class User(

    val name: String,

    val avatar: String?,

    val login: String,

    val role: List<UserRole>

)
