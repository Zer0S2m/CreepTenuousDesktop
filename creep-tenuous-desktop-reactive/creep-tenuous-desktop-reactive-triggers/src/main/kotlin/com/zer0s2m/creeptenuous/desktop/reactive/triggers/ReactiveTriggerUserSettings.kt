package com.zer0s2m.creeptenuous.desktop.reactive.triggers

import com.zer0s2m.creeptenuous.desktop.common.dto.UserSettingsFileObjectDistribution
import com.zer0s2m.creeptenuous.desktop.core.logging.infoDev
import com.zer0s2m.creeptenuous.desktop.core.logging.logger
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveTrigger
import org.slf4j.Logger

/**
 * The reactive trigger fires when a new user is installed to transfer file objects.
 */
open class ReactiveTriggerUserSettingsSetTransferUser : BaseReactiveTrigger<UserSettingsFileObjectDistribution> {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution
     *
     * @param value The new value of a property or object
     */
    override fun execution(value: UserSettingsFileObjectDistribution) {
        logger.infoDev("Set transfer user\nDATA: $value")
    }

}

/**
 * Reactive trigger fires when file objects are set to be deleted
 */
open class ReactiveTriggerUserSettingsSetIsDeleteFileObject : BaseReactiveTrigger<UserSettingsFileObjectDistribution> {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution
     *
     * @param value The new value of a property or object
     */
    override fun execution(value: UserSettingsFileObjectDistribution) {
        logger.infoDev("Set is delete file object\nDATA: $value")
    }

}
