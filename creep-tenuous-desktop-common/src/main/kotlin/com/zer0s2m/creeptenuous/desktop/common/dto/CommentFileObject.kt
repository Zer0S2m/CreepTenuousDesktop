package com.zer0s2m.creeptenuous.desktop.common.dto

import kotlinx.serialization.Serializable

/**
 * Main class for storing comments for a file object.
 */
@Serializable
data class CommentFileObject(

    val id: Int?,

    var comment: String,

    val fileSystemObject: String,

    val createdAt: String

)
