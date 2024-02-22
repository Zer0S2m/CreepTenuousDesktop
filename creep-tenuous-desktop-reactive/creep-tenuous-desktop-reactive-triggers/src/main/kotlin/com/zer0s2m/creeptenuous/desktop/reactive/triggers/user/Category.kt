package com.zer0s2m.creeptenuous.desktop.reactive.triggers.user

import com.zer0s2m.creeptenuous.desktop.common.data.*
import com.zer0s2m.creeptenuous.desktop.common.dto.UserCategory
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.logging.infoDev
import com.zer0s2m.creeptenuous.desktop.core.logging.logger
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
            url("/api/v1/user/category")
            header("Authorization", "Bearer ${HttpClient.accessToken}")
            contentType(ContentType.Application.Json)
            setBody(DataCreateUserCategory(value.title))
        }.body()

        HttpClient.client.put {
            url("/api/v1/user/customization/category/color")
            header("Authorization", "Bearer ${HttpClient.accessToken}")
            contentType(ContentType.Application.Json)
            setBody(DataSetColorInUserCategory(
                userColorId = value.colorId!!,
                userCategoryId = userCategoryCreated.id!!
            ))
        }
    }

}

/**
 * Reactive trigger to remove custom category
 */
class ReactiveTriggerUserCategoryRemove : BaseReactiveTrigger<UserCategory> {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution
     *
     * @param value The new value of a property or object
     */
    override suspend fun execution(value: UserCategory) {
        logger.infoDev("Delete category\nDATA: $value")

        HttpClient.client.delete {
            url("/api/v1/user/category")
            header("Authorization", "Bearer ${HttpClient.accessToken}")
            contentType(ContentType.Application.Json)
            setBody(value.id?.let { DataControlObject(it) })
        }
    }

}

/**
 * Reactive trigger to update custom category
 */
class ReactiveTriggerUserCategorySet : BaseReactiveTrigger<UserCategory> {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution
     *
     * @param value The new value of a property or object
     */
    override suspend fun execution(value: UserCategory) {
        logger.infoDev("Update category\nDATA: $value")

        HttpClient.client.put {
            url("/api/v1/user/category")
            header("Authorization", "Bearer ${HttpClient.accessToken}")
            contentType(ContentType.Application.Json)
            setBody(value.id?.let { idUserCategory -> DataEditUserCategory(idUserCategory, value.title) })
        }

        if (value.colorId == null && value.id != null) {
            HttpClient.client.delete {
                url("/api/v1/user/customization/category/color")
                header("Authorization", "Bearer ${HttpClient.accessToken}")
                contentType(ContentType.Application.Json)
                setBody(
                    DataDeleteColorInUserCategory(userCategoryId = value.id!!)
                )
            }
        } else if (value.colorId != null && value.id != null) {
            HttpClient.client.put {
                url("/api/v1/user/customization/category/color")
                header("Authorization", "Bearer ${HttpClient.accessToken}")
                contentType(ContentType.Application.Json)
                setBody(DataSetColorInUserCategory(
                    userColorId = value.colorId!!,
                    userCategoryId = value.id!!
                ))
            }
        }
    }

}
