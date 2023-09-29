package com.zer0s2m.creeptenuous.desktop.reactive.triggers.common

import com.zer0s2m.creeptenuous.desktop.common.dto.User
import com.zer0s2m.creeptenuous.desktop.core.logging.infoDev
import com.zer0s2m.creeptenuous.desktop.core.logging.logger
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveIndependentTrigger
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveTrigger
import org.slf4j.Logger

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
    override fun execution(value: User) {
        logger.infoDev("Delete system user\nDATA: $value")
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
    override fun execution(vararg values: Any?) {
        val user: User? = if (values[0] is User) values[0] as User else null
        logger.infoDev("Unblock system user\nDATA: $user")
    }

}
