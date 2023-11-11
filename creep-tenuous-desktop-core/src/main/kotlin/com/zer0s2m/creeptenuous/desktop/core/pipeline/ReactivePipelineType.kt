package com.zer0s2m.creeptenuous.desktop.core.pipeline

import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveIndependentTrigger
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveTrigger

/**
 * Types of reactive piping after calling a reactive trigger.
 * [BaseReactiveIndependentTrigger] or [BaseReactiveTrigger].
 */
enum class ReactivePipelineType {

    /**
     * The pipeline runs before the trigger is called.
     */
    BEFORE,

    /**
     * The pipeline runs after the trigger is called.
     */
    AFTER

}