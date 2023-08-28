package com.zer0s2m.creeptenuous.desktop.common.dto

import kotlinx.serialization.Serializable

/**
 * Custom colors for the user
 */
@Serializable
data class UserColor(

    val color: String,

    val id: Int

)
