package com.zer0s2m.creeptenuous.desktop.common.data

import kotlinx.serialization.Serializable

/**
 * A general model for managing system objects in the form of a login.
 *
 * @param login User login.
 */
@Serializable
data class DataControlUser(

    val login: String

)
