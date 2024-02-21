package com.zer0s2m.creeptenuous.desktop.common.dto

/**
 * Model for bread crumbs (navigation).
 *
 * @param systemName System name of the file object.
 * @param realName Real name of the file object.
 */
data class BreadCrumbFileObject(

    val systemName: String,

    val realName: String

)