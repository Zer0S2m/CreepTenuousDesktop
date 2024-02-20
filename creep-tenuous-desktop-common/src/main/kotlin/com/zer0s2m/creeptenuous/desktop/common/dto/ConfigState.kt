package com.zer0s2m.creeptenuous.desktop.common.dto

import kotlinx.serialization.Serializable

@Serializable
data class ConfigState(

    val login: String,

    val password: String,

    val accessToken: String?,

    val refreshToken: String?

)
