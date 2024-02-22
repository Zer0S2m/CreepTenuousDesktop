package com.zer0s2m.creeptenuous.desktop.common.data

import kotlinx.serialization.Serializable

/**
 * Model for blocking a user for a period of time.
 *
 * @param login User login.
 * @param fromDate Blocking start date (if the date is not specified, the current one is taken).
 * @param toDate Blocking end date.
 */
@Serializable
data class DataBlockTemporarilyUser(

    val login: String,

    val fromDate: String?,

    val toDate: String

)