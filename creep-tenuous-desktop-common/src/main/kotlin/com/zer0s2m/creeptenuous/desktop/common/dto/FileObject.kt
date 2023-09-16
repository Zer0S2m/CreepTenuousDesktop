package com.zer0s2m.creeptenuous.desktop.common.dto

import kotlinx.serialization.Serializable

/**
 * Main class for storing information about a file object
 */
@Serializable
data class FileObject(

    val realName: String,

    val systemName: String,

    val isFile: Boolean,

    val isDirectory: Boolean,

    var color: String? = null,

    var categoryId: Int? = null

)
