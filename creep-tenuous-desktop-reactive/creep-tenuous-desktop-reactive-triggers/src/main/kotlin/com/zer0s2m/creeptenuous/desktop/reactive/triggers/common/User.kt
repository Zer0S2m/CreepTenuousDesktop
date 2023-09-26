package com.zer0s2m.creeptenuous.desktop.reactive.triggers.common

import com.zer0s2m.creeptenuous.desktop.common.dto.User
import com.zer0s2m.creeptenuous.desktop.core.logging.infoDev
import com.zer0s2m.creeptenuous.desktop.core.logging.logger
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