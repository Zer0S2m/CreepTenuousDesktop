package com.zer0s2m.creeptenuous.desktop.common.data

import kotlinx.serialization.Serializable

/**
 * Model for editing a comment on a file object.
 *
 * @param id Unique identifier of the comment.
 * @param comment The meaning of the comment.
 */
@Serializable
data class DataEditCommentFileSystemObject(

    val id: Int,

    val comment: String

)
