package com.zer0s2m.creeptenuous.desktop.reactive.triggers

import com.zer0s2m.creeptenuous.desktop.common.data.DataControlUser
import com.zer0s2m.creeptenuous.desktop.common.dto.User
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.logging.infoDev
import com.zer0s2m.creeptenuous.desktop.core.logging.logger
import com.zer0s2m.creeptenuous.desktop.core.state.SystemSettings
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveIndependentTrigger
import io.ktor.client.request.*
import io.ktor.http.*
import org.slf4j.Logger

/**
 * Trigger is called when the user is permanently blocked.
 */
class ReactiveTriggerReactiveSystemUserBlockCompletely : BaseReactiveIndependentTrigger {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution.
     *
     * @param values Arbitrary number of arguments passed regardless of type
     */
    override suspend fun execution(vararg values: Any?) {
        val user: User? = if (values[0] is User) values[0] as User else null
        logger.infoDev("Block completely system user\nDATA: $user")

        if (user != null) {
            HttpClient.client.patch {
                url("${SystemSettings.host}:${SystemSettings.port}/api/v1/user/control/block")
                header("Authorization", "Bearer ${SystemSettings.accessToken}")
                contentType(ContentType.Application.Json)
                setBody(DataControlUser(user.login))
            }
        }
    }

}
