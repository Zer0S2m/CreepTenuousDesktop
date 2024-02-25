package com.zer0s2m.creeptenuous.desktop.reactive.triggers

import com.zer0s2m.creeptenuous.desktop.common.data.DataSetCategoryInFileObject
import com.zer0s2m.creeptenuous.desktop.common.data.DataSetColorInFieSystemObject
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.state.SystemSettings
import io.ktor.client.request.*
import io.ktor.http.*

open class ReactiveTriggerCommon {

    internal suspend fun setColorInDirectory(systemName: String, colorId: Int) {
        HttpClient.client.put {
            url("${SystemSettings.host}:${SystemSettings.port}/api/v1/user/customization/directory/color")
            header("Authorization", "Bearer ${SystemSettings.accessToken}")
            contentType(ContentType.Application.Json)
            setBody(DataSetColorInFieSystemObject(systemName, colorId))
        }
    }

    internal suspend fun setCategoryInDirectory(systemName: String, categoryId: Int) {
        HttpClient.client.post {
            url("${SystemSettings.host}:${SystemSettings.port}/api/v1/user/category/file-system-object")
            header("Authorization", "Bearer ${SystemSettings.accessToken}")
            contentType(ContentType.Application.Json)
            setBody(DataSetCategoryInFileObject(systemName, categoryId))
        }
    }

}