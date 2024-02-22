package com.zer0s2m.creeptenuous.desktop.common.data

import kotlinx.serialization.Serializable

/**
 * Model for updating a custom color palette.
 *
 * @param id Unique identifier of the custom color palette.
 * @param color The name of the custom color palette.
 */
@Serializable
data class DataEditUserColor(

    val id: Int,

    val color: String

)
