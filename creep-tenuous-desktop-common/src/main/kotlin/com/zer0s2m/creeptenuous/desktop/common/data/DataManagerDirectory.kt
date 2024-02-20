package com.zer0s2m.creeptenuous.desktop.common.data

import kotlinx.serialization.Serializable

@Serializable
data class DataManagerDirectory(

    val level: Int,

    val parents: Collection<String>,

    val systemParents: Collection<String>,

)
