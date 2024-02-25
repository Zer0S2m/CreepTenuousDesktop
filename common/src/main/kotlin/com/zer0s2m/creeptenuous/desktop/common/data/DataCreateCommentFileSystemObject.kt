package com.zer0s2m.creeptenuous.desktop.common.data

import kotlinx.serialization.Serializable

/**
 * Model for creating a comment for a file object.
 *
 * @param comment The meaning of the comment.
 * @param fileSystemObject System name of the file object.
 */
@Serializable
data class DataCreateCommentFileSystemObject(

    val comment: String,

    val fileSystemObject: String

)
