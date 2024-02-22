package com.zer0s2m.creeptenuous.desktop.common.data

import kotlinx.serialization.Serializable

/**
 * Model for deleting a file object of the directory type.
 *
 * @param parents Parts of real system paths (directories).
 * @param systemParents Parts of system paths (directories).
 * @param directoryName Real directory name.
 * @param systemDirectoryName System name of the file object.
 */
@Serializable
data class DataDeleteFileSystemObjectDirectory(

    val parents: Collection<String>,

    val systemParents: Collection<String>,

    val directoryName: String,

    val systemDirectoryName: String

)