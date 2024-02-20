package com.zer0s2m.creeptenuous.desktop.common.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreatedDirectory(

    val realDirectoryName: String,

    val systemDirectoryName: String

)