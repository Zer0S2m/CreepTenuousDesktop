package com.zer0s2m.creeptenuous.desktop.common.data

import kotlinx.serialization.Serializable

/**
 * Model for creating a file object of type - directory
 *
 * @param parents Parts of real system paths (directories).
 * @param systemParents Parts of system paths (directories).
 * @param directoryName The real name of the directory.
 */
@Serializable
data class DataCreateFileSystemObjectDirectory(

    val parents: Collection<String>,

    val systemParents: Collection<String>,

    val directoryName: String

)
