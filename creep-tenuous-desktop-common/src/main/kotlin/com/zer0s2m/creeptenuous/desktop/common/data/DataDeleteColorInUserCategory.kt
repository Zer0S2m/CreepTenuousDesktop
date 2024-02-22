package com.zer0s2m.creeptenuous.desktop.common.data

import kotlinx.serialization.Serializable

/**
 * Model for removing a custom color palette from a category.
 *
 * @param userCategoryId Unique identifier of the user category.
 */
@Serializable
data class DataDeleteColorInUserCategory(

    val userCategoryId: Int

)
