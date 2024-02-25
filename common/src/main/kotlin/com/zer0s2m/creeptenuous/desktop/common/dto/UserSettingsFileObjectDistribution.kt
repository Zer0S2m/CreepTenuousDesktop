package com.zer0s2m.creeptenuous.desktop.common.dto

import kotlinx.serialization.Serializable

/**
 * Basic user settings for issuing file objects when their owner is deleted
 */
@Serializable
data class UserSettingsFileObjectDistribution(

    var isDeletingFilesWhenDeletingUser: Boolean?,

    var passingFilesToUser: String?

)
