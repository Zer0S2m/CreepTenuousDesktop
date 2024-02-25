package com.zer0s2m.creeptenuous.desktop.common.data

import kotlinx.serialization.Serializable

/**
 * Model for obtaining information about file objects.
 *
 * @param systemNames System names of file objects.
 */
@Serializable
data class DataFileSystemObjectInfo(

    val systemNames: Collection<String>

)
