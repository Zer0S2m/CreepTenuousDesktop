package com.zer0s2m.creeptenuous.desktop.common.dto

import com.zer0s2m.creeptenuous.desktop.common.enums.TypeRight
import kotlinx.serialization.Serializable

/**
 * Data for the granted right to interact with the file object
 */
@Serializable
data class GrantedRight(

    val rights: List<GrantedRightItem> = listOf()

)

@Serializable
data class GrantedRightItem(

    val systemName: String,

    val rights: Collection<GrantedRightItemUser>

)

@Serializable
data class GrantedRightItemUser(

    val user: String,

    val rights: Collection<TypeRight>

)
