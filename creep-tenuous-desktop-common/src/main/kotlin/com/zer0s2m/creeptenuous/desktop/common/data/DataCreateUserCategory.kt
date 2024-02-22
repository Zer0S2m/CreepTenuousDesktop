package com.zer0s2m.creeptenuous.desktop.common.data

import kotlinx.serialization.Serializable

/**
 * Model for creating a custom category.
 *
 * @param title Custom category name.
 */
@Serializable
data class DataCreateUserCategory(

    val title: String

)