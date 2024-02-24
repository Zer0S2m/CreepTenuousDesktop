package com.zer0s2m.creeptenuous.desktop.reactive.triggers

import com.zer0s2m.creeptenuous.desktop.common.data.DataSettingIsDeleteFileObject
import com.zer0s2m.creeptenuous.desktop.common.dto.UserSettingsFileObjectDistribution
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.logging.infoDev
import com.zer0s2m.creeptenuous.desktop.core.logging.logger
import com.zer0s2m.creeptenuous.desktop.core.state.SystemSettings
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveTrigger
import io.ktor.client.request.*
import io.ktor.http.*
import org.slf4j.Logger

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
            url("${SystemSettings.host}:${SystemSettings.port}/api/v1/user/profile/settings/is-delete-file-objects")
            header("Authorization", "Bearer ${SystemSettings.accessToken}")
            contentType(ContentType.Application.Json)
            setBody(value.isDeletingFilesWhenDeletingUser?.let { DataSettingIsDeleteFileObject(it) })
        }
    }

}
