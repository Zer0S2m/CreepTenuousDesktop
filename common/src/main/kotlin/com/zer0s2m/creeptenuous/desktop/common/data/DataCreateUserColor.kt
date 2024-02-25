package com.zer0s2m.creeptenuous.desktop.common.data

import kotlinx.serialization.Serializable

/**
 * Model for creating a custom color palette.
 *
 * @param color The name of the custom color palette.
 */
@Serializable
data class DataCreateUserColor(

    val color: String

)