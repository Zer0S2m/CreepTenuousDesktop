package com.zer0s2m.creeptenuous.desktop.reactive.triggers.common

import com.zer0s2m.creeptenuous.desktop.common.data.DataBlockTemporarilyUser
import com.zer0s2m.creeptenuous.desktop.common.data.DataControlUser
import com.zer0s2m.creeptenuous.desktop.common.dto.User
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.logging.infoDev
import com.zer0s2m.creeptenuous.desktop.core.logging.logger
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveIndependentTrigger
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveTrigger
import io.ktor.client.request.*
import io.ktor.http.*
import org.slf4j.Logger
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * The trigger fires when a user is removed from the system
 */
class ReactiveTriggerReactiveSystemUserRemove : BaseReactiveTrigger<User> {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution.
     *
     * @param value The new value of a property or object.
     */
    override suspend fun execution(value: User) {
        logger.infoDev("Delete system user\nDATA: $value")

        HttpClient.client.delete {
            url("/api/v1/user/control/delete")
            header("Authorization", "Bearer ${HttpClient.accessToken}")
            contentType(ContentType.Application.Json)
            setBody(DataControlUser(value.login))
        }
    }

}

/**
 * The trigger fires when a user is unblocking from the system
 */
class ReactiveTriggerReactiveSystemUserUnblock : BaseReactiveIndependentTrigger {

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
        logger.infoDev("Unblock system user\nDATA: $user")

        if (user != null) {
            HttpClient.client.patch {
                url("/api/v1/user/control/unblock")
                header("Authorization", "Bearer ${HttpClient.accessToken}")
                contentType(ContentType.Application.Json)
                setBody(DataControlUser(user.login))
            }
        }
    }

}

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
                url("/api/v1/user/control/block")
                header("Authorization", "Bearer ${HttpClient.accessToken}")
                contentType(ContentType.Application.Json)
                setBody(DataControlUser(user.login))
            }
        }
    }

}

/**
 * The trigger is called when the user is blocked for a certain period of time.
 */
class ReactiveTriggerReactiveSystemUserBlockTemporary : BaseReactiveIndependentTrigger {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution.
     *
     * @param values Arbitrary number of arguments passed regardless of type
     */
    override suspend fun execution(vararg values: Any?) {
        val startDateBlock: Date? = if (values[0] is Date) values[0] as Date else null
        val endDateBlock: Date? = if (values[1] is Date) values[1] as Date else null
        val user: User? = if (values[2] is User) values[2] as User else null

        logger.infoDev(
            "Block temporary system user\nDATA: " +
                    "[user: $user, startDateBlock: $startDateBlock, endDateBlock: $endDateBlock]"
        )

        if (endDateBlock != null && user != null) {
            val format: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS")

            HttpClient.client.post {
                url("/api/v1/user/control/block-temporarily")
                header("Authorization", "Bearer ${HttpClient.accessToken}")
                contentType(ContentType.Application.Json)
                setBody(
                    DataBlockTemporarilyUser(
                        login = user.login,
                        fromDate = if (startDateBlock != null) format.format(startDateBlock) else null,
                        toDate = format.format(endDateBlock)
                    )
                )
            }
        }
    }

}