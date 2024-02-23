package com.zer0s2m.creeptenuous.desktop.common.data

import kotlinx.serialization.Serializable

/**
 * User authorization.
 *
 * @param login User login.
 * @param password User password.
 */
@Serializable
data class DataLoginUser(

    val login: String,

    val password: String

)
