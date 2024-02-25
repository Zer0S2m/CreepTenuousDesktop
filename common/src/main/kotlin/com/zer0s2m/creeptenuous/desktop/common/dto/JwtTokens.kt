package com.zer0s2m.creeptenuous.desktop.common.dto

import kotlinx.serialization.Serializable

@Serializable
data class JwtTokens(

    val accessToken: String,

    val refreshToken: String

)
