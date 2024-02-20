package com.zer0s2m.creeptenuous.desktop.common.dto

import kotlinx.serialization.Serializable

/**
 * Basic parameters for the user
 */
@Serializable
data class UserProfileSettings(

    val login: String,

    val email: String,

    val name: String,

    val avatar: String?,

    val role: List<String>

)
