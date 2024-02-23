package com.zer0s2m.creeptenuous.desktop.reactive.triggers

import com.zer0s2m.creeptenuous.desktop.common.data.DataControlObject
import com.zer0s2m.creeptenuous.desktop.common.dto.UserColor
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.logging.infoDev
import com.zer0s2m.creeptenuous.desktop.core.logging.logger
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveTrigger
import io.ktor.client.request.*
import io.ktor.http.*
import org.slf4j.Logger

/**
 * Reactive trigger to remove custom color
 */
open class ReactiveTriggerUserColorRemove : BaseReactiveTrigger<UserColor> {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution
     *
     * @param value The new value of a property or object
     */
    override suspend fun execution(value: UserColor) {
        logger.infoDev("Delete color\nDATA: $value")

        HttpClient.client.delete {
            url("/api/v1/user/customization/color")
            header("Authorization", "Bearer ${HttpClient.accessToken}")
            contentType(ContentType.Application.Json)
            setBody(value.id?.let { DataControlObject(it) })
        }
    }

}
