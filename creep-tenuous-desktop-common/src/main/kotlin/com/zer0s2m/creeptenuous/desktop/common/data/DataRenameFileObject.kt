package com.zer0s2m.creeptenuous.desktop.common.data

import kotlinx.serialization.Serializable

/**
 * Model for renaming a file object.
 *
 * @param systemName System name of the file object.
 * @param newRealName New real name of the file object.
 */
@Serializable
data class DataRenameFileObject(

    val systemName: String,

    val newRealName: String

)
