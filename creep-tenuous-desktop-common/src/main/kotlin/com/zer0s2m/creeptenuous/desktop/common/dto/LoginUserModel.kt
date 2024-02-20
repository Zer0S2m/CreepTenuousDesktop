package com.zer0s2m.creeptenuous.desktop.common.dto

import kotlinx.serialization.Serializable

/**
 * User authorization
 */
@Serializable
data class LoginUserModel(

    val login: String,

    val password: String

)
