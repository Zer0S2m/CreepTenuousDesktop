package com.zer0s2m.creeptenuous.desktop.reactive.triggers

import com.zer0s2m.creeptenuous.desktop.common.data.DataDeleteGrantedRights
import com.zer0s2m.creeptenuous.desktop.common.dto.GrantedRightItemUser
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.logging.infoDev
import com.zer0s2m.creeptenuous.desktop.core.logging.logger
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveIndependentTrigger
import io.ktor.client.request.*
import io.ktor.http.*
import org.slf4j.Logger

class ReactiveTriggerUserGrantedRightDelete : BaseReactiveIndependentTrigger {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution.
     *
     * @param values Arbitrary number of arguments passed regardless of type
     */
    @Suppress("UNCHECKED_CAST")
    override suspend fun execution(vararg values: Any?) {
        val grantedRights: Collection<GrantedRightItemUser> = if (values[0] is Collection<*>)
            values[0] as Collection<GrantedRightItemUser> else return
        val systemNameFileObject: String = if (values[1] is String) values[1].toString() else return

        logger.infoDev(
            "Removing granted rights\n" +
                    "DATA: $grantedRights"
        )

        if (grantedRights.isNotEmpty()) {
            grantedRights.forEach { grantedRight: GrantedRightItemUser ->
                HttpClient.client.delete() {
                    url("/api/v1/user/global/right")
                    header("Authorization", "Bearer ${HttpClient.accessToken}")
                    contentType(ContentType.Application.Json)
                    setBody(
                        DataDeleteGrantedRights(
                            systemName = systemNameFileObject,
                            loginUser = grantedRight.user,
                            right = grantedRight.rights
                        )
                    )
                }
            }
        }
    }

}