package com.zer0s2m.creeptenuous.desktop.common.dto

import kotlinx.serialization.Serializable

@Serializable
data class InfoFileObjects(

    val objects: List<InfoFileObject> = listOf()
    
)

@Serializable
data class InfoFileObject(

    val realName: String,

    val systemName: String,

    val isFile: Boolean,

    val isDirectory: Boolean,

    val color: String? = null,

    val createdAt: String,

    val colorId: Int? = null,

    val categoryId: Int? = null,

    val owner: String

)
