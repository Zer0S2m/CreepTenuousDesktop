package com.zer0s2m.creeptenuous.desktop.common.data

import kotlinx.serialization.Serializable

/**
 * Model for downloading directories.
 *
 * @param parents Parts of real system paths (directories).
 * @param systemParents Parts of system paths (directories).
 * @param directoryName The real name of the directory.
 * @param systemDirectoryName System name of the directory.
 */
@Serializable
data class DataDownloadDirectory(

    val parents: Collection<String>,

    val systemParents: Collection<String>,

    val directoryName: String,

    val systemDirectoryName: String

)
