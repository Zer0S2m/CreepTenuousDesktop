package com.zer0s2m.creeptenuous.desktop.common.data

import kotlinx.serialization.Serializable

/**
 * Model for updating a user category.
 *
 * @param id Unique identifier of the user category.
 * @param title Custom category name.
 */
@Serializable
data class DataEditUserCategory(

    val id: Int,

    val title: String

)
