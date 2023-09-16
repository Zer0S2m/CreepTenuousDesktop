package com.zer0s2m.creeptenuous.desktop.common.dto

import kotlinx.serialization.Serializable

/**
 * Data for creating categories
 */
@Serializable
data class UserCategory(

    val id: Int? = null,

    var title: String,

    var color: String? = null

)
