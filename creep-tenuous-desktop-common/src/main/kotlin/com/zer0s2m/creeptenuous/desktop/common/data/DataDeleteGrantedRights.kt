package com.zer0s2m.creeptenuous.desktop.common.data

import com.zer0s2m.creeptenuous.desktop.common.enums.TypeRight
import kotlinx.serialization.Serializable

/**
 * Model for removing granted rights to a file object.
 *
 * @param systemName System name of the file object.
 * @param loginUser User login.
 * @param right Collection type rights.
 */
@Serializable
data class DataDeleteGrantedRights(

    val systemName: String,

    val loginUser: String,

    val right: Collection<TypeRight>

)