package com.zer0s2m.creeptenuous.desktop.common.dto

import kotlinx.serialization.Serializable

/**
 * Information about the directory at a certain segment of the nesting level
 */
@Serializable
data class ManagerFileObject(

    val systemParents: Collection<String>,

    val level: Int,

    val objects: MutableList<FileObject>

)
