package com.zer0s2m.creeptenuous.desktop.reactive.triggers

import com.zer0s2m.creeptenuous.desktop.common.data.DataControlFileSystemObject
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.logging.infoDev
import com.zer0s2m.creeptenuous.desktop.core.logging.logger
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveIndependentTrigger
import io.ktor.client.request.*
import io.ktor.http.*
import org.slf4j.Logger

/**
 * The trigger is called when a file object is set or unset to a custom category.
 */
class ReactiveTriggerReactiveFileObjectSetCategoryInFileObject
    : ReactiveTriggerCommon(), BaseReactiveIndependentTrigger {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution.
     *
     * @param values Arbitrary number of arguments passed regardless of type
     */
    override suspend fun execution(vararg values: Any?) {
        val systemNameFileObject: String? = if (values[0] is String) values[0].toString() else null
        val categoryId: Int? = if (values[1] is Int) values[1].toString().toInt() else null
        logger.infoDev("Set category in file object\nDATA: [$systemNameFileObject, $categoryId]")

        if (categoryId != null && systemNameFileObject != null) {
            setCategoryInDirectory(systemNameFileObject, categoryId)
        } else if (categoryId == null && systemNameFileObject != null) {
            HttpClient.client.delete {
                url("/api/v1/user/category/file-system-object")
                header("Authorization", "Bearer ${HttpClient.accessToken}")
                contentType(ContentType.Application.Json)
                setBody(DataControlFileSystemObject(systemNameFileObject))
            }
        }
    }

}