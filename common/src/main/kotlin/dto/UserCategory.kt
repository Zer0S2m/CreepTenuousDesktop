package dto

import kotlinx.serialization.Serializable

/**
 * Data for creating categories
 */
@Serializable
data class UserCategory(

    val id: Int? = null,

    val title: String

)
