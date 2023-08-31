package com.zer0s2m.creeptenuous.desktop.reactive.triggers

import com.zer0s2m.creeptenuous.desktop.common.dto.UserCategory
import com.zer0s2m.creeptenuous.desktop.common.dto.UserColor
import com.zer0s2m.creeptenuous.desktop.core.triggers.ReactiveTrigger

/**
 * Reactive trigger to add new custom category
 */
open class ReactiveTriggerUserCategoryAdd : ReactiveTrigger<UserCategory> {

    /**
     * Trigger execution
     *
     * @param value The new value of a property or object
     */
    override fun execution(value: UserCategory) {
        println(value)
    }

}

/**
 * Reactive trigger to remove custom category
 */
open class ReactiveTriggerUserCategoryRemove : ReactiveTrigger<UserCategory> {

    /**
     * Trigger execution
     *
     * @param value The new value of a property or object
     */
    override fun execution(value: UserCategory) {
        println(value)
    }

}

/**
 * Reactive trigger to add new custom color
 */
open class ReactiveTriggerUserColorAdd : ReactiveTrigger<UserColor> {

    /**
     * Trigger execution
     *
     * @param value The new value of a property or object
     */
    override fun execution(value: UserColor) {
        println(value)
    }

}

/**
 * Reactive trigger to remove custom color
 */
open class ReactiveTriggerUserColorRemove : ReactiveTrigger<UserColor> {

    /**
     * Trigger execution
     *
     * @param value The new value of a property or object
     */
    override fun execution(value: UserColor) {
        println(value)
    }

}
