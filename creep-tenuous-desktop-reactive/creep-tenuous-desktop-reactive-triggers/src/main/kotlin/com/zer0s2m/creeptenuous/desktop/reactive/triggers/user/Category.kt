package com.zer0s2m.creeptenuous.desktop.reactive.triggers.user

import com.zer0s2m.creeptenuous.desktop.common.dto.UserCategory
import com.zer0s2m.creeptenuous.desktop.core.logging.infoDev
import com.zer0s2m.creeptenuous.desktop.core.logging.logger
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveTrigger
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
    override fun execution(value: UserCategory) {
        logger.infoDev("Create category\nDATA: $value")
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
    override fun execution(value: UserCategory) {
        logger.infoDev("Delete category\nDATA: $value")
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
    override fun execution(value: UserCategory) {
        logger.infoDev("Update category\nDATA: $value")
    }

}
