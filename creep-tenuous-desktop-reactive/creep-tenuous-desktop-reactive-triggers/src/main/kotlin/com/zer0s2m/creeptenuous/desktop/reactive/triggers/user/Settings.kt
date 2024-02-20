package com.zer0s2m.creeptenuous.desktop.reactive.triggers.user

import com.zer0s2m.creeptenuous.desktop.common.data.DataSettingIsDeleteFileObject
import com.zer0s2m.creeptenuous.desktop.common.dto.UserSettingsFileObjectDistribution
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.logging.infoDev
import com.zer0s2m.creeptenuous.desktop.core.logging.logger
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveTrigger
import io.ktor.client.request.*
import io.ktor.http.*
import org.slf4j.Logger

/**
 * The reactive trigger fires when a new user is installed to transfer file objects.
 */
class ReactiveTriggerUserSettingsSetTransferUser : BaseReactiveTrigger<UserSettingsFileObjectDistribution> {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution
     *
     * @param value The new value of a property or object
     */
    override suspend fun execution(value: UserSettingsFileObjectDistribution) {
        logger.infoDev("Set transfer user\nDATA: $value")

        HttpClient.client.patch {
            url("/api/v1/user/profile/settings/set-transfer-user")
            header("Authorization", "Bearer ${HttpClient.accessToken}")
            contentType(ContentType.Application.Json)
            setBody(value.isDeletingFilesWhenDeletingUser?.let { DataSettingIsDeleteFileObject(it) })
        }
    }

}

/**
 * Reactive trigger fires when file objects are set to be deleted.
 */
class ReactiveTriggerUserSettingsSetIsDeleteFileObject : BaseReactiveTrigger<UserSettingsFileObjectDistribution> {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution
     *
     * @param value The new value of a property or object
     */
    override suspend fun execution(value: UserSettingsFileObjectDistribution) {
        logger.infoDev("Set is delete file object\nDATA: $value")

        HttpClient.client.patch {
            url("/api/v1/user/profile/settings/is-delete-file-objects")
            header("Authorization", "Bearer ${HttpClient.accessToken}")
            contentType(ContentType.Application.Json)
            setBody(value.isDeletingFilesWhenDeletingUser?.let { DataSettingIsDeleteFileObject(it) })
        }
    }

}
