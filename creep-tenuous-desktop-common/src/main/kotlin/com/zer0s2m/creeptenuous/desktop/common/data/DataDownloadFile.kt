package com.zer0s2m.creeptenuous.desktop.common.data

import kotlinx.serialization.Serializable

/**
 * Model for downloading files.
 *
 * @param parents Parts of real system paths (directories).
 * @param systemParents Parts of system paths (directories).
 * @param fileName Real file name.
 * @param systemFileName System file name.
 */
@Serializable
data class DataDownloadFile(

    val parents: Collection<String>,

    val systemParents: Collection<String>,

    val fileName: String,

    val systemFileName: String

)