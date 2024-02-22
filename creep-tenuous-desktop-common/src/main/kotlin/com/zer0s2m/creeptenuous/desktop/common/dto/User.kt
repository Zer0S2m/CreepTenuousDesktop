package com.zer0s2m.creeptenuous.desktop.common.dto

import com.zer0s2m.creeptenuous.desktop.common.enums.UserRole
import kotlinx.serialization.Serializable

/**
 * Base user in the system
 */
@Serializable
data class User(

    val name: String,

    val avatar: String?,

    val login: String,

    val role: List<UserRole>,

    var isBlocked: Boolean,

    var isTemporarilyBlocked: Boolean

)
