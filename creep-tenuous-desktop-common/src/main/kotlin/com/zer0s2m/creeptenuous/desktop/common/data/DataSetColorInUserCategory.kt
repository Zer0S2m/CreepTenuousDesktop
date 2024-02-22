package com.zer0s2m.creeptenuous.desktop.common.data

import kotlinx.serialization.Serializable

/**
 * Model for setting the color palette for a custom category.
 *
 * @param userColorId Unique identifier of the custom color palette.
 * @param userCategoryId Unique identifier of the user category.
 */
@Serializable
data class DataSetColorInUserCategory(

    val userColorId: Int,

    val userCategoryId: Int

)
