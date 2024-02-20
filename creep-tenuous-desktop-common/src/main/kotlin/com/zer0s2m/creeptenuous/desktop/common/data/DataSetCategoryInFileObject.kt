package com.zer0s2m.creeptenuous.desktop.common.data

import kotlinx.serialization.Serializable

/**
 * Model for setting a custom category for a file object.
 *
 * @param fileSystemObject System name of the file object.
 * @param categoryId
 */
@Serializable
data class DataSetCategoryInFileObject(

    val fileSystemObject: String,

    val categoryId: Int

)
