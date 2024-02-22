package com.zer0s2m.creeptenuous.desktop.common.data

import kotlinx.serialization.Serializable

/**
 * Model for setting the color palette to a file object of type - directory.
 *
 * @param fileSystemObject System name of the file object.
 * @param userColorId Unique identifier of the custom color palette.
 */
@Serializable
data class DataSetColorInFieSystemObject(

    val fileSystemObject: String,

    val userColorId: Int

)
