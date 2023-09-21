package com.zer0s2m.creeptenuous.desktop.reactive.triggers

import com.zer0s2m.creeptenuous.desktop.common.dto.UserCategory
import com.zer0s2m.creeptenuous.desktop.common.dto.UserColor
import com.zer0s2m.creeptenuous.desktop.core.logging.infoDev
import com.zer0s2m.creeptenuous.desktop.core.logging.logger
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveTrigger
import org.slf4j.Logger

/**
 * Reactive trigger to add new custom category
 */
open class ReactiveTriggerUserCategoryAdd : BaseReactiveTrigger<UserCategory> {

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
open class ReactiveTriggerUserCategoryRemove : BaseReactiveTrigger<UserCategory> {

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
open class ReactiveTriggerUserCategorySet : BaseReactiveTrigger<UserCategory> {

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

/**
 * Reactive trigger to add new custom color
 */
open class ReactiveTriggerUserColorAdd : BaseReactiveTrigger<UserColor> {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution
     *
     * @param value The new value of a property or object
     */
    override fun execution(value: UserColor) {
        logger.infoDev("Create color\nDATA: $value")
    }

}

/**
 * Reactive trigger to remove custom color
 */
open class ReactiveTriggerUserColorRemove : BaseReactiveTrigger<UserColor> {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution
     *
     * @param value The new value of a property or object
     */
    override fun execution(value: UserColor) {
        logger.infoDev("Delete color\nDATA: $value")
    }

}

/**
 * Reactive trigger to update custom color
 */
open class ReactiveTriggerUserColorSet : BaseReactiveTrigger<UserColor> {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution
     *
     * @param value The new value of a property or object
     */
    override fun execution(value: UserColor) {
        logger.infoDev("Update color\nDATA: $value")
    }

}
