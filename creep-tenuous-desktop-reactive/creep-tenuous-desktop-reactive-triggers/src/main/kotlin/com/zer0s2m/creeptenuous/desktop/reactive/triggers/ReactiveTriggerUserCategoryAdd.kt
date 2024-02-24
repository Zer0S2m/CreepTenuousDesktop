package com.zer0s2m.creeptenuous.desktop.reactive.triggers

import com.zer0s2m.creeptenuous.desktop.common.data.DataCreateUserCategory
import com.zer0s2m.creeptenuous.desktop.common.data.DataSetColorInUserCategory
import com.zer0s2m.creeptenuous.desktop.common.dto.UserCategory
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.logging.infoDev
import com.zer0s2m.creeptenuous.desktop.core.logging.logger
import com.zer0s2m.creeptenuous.desktop.core.state.SystemSettings
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveTrigger
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.slf4j.Logger

/**
 * Reactive trigger to add new custom category
 */
class ReactiveTriggerUserCategoryAdd : BaseReactiveTrigger<UserCategory> {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution
     *
     * @param value The new value of a property or object
     */
    override suspend fun execution(value: UserCategory) {
        logger.infoDev("Create category\nDATA: $value")

        val userCategoryCreated: UserCategory = HttpClient.client.post {
            url("${SystemSettings.host}:${SystemSettings.port}/api/v1/user/category")
            header("Authorization", "Bearer ${SystemSettings.accessToken}")
            contentType(ContentType.Application.Json)
            setBody(DataCreateUserCategory(value.title))
        }.body()

        HttpClient.client.put {
            url("${SystemSettings.host}:${SystemSettings.port}/api/v1/user/customization/category/color")
            header("Authorization", "Bearer ${SystemSettings.accessToken}")
            contentType(ContentType.Application.Json)
            setBody(
                DataSetColorInUserCategory(
                    userColorId = value.colorId!!,
                    userCategoryId = userCategoryCreated.id!!
                )
            )
        }
    }

}
