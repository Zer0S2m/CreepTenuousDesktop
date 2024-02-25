package com.zer0s2m.creeptenuous.desktop.common.data

import kotlinx.serialization.Serializable

/**
 * A general model for managing system objects in the form of a unique identifier.
 *
 * @param id Unique identifier.
 */
@Serializable
data class DataControlObject(

    val id: Int

)
