package com.zer0s2m.creeptenuous.desktop.common.data

import kotlinx.serialization.Serializable

/**
 * The model for saving the setting is deleting file objects.
 *
 * @param isDelete Whether file objects need to be deleted.
 */
@Serializable
data class DataSettingIsDeleteFileObject(

    val isDelete: Boolean

)
