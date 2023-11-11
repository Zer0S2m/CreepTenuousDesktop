package com.zer0s2m.creeptenuous.desktop.common.dto

import com.zer0s2m.creeptenuous.desktop.common.enums.TypeRight
import kotlinx.serialization.Serializable

@Serializable
data class IssuedRights(

    val systemName: String,

    val rights: Collection<TypeRight>

)
