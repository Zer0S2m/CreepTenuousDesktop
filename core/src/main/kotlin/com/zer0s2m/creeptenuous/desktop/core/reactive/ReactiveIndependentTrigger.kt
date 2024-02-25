package com.zer0s2m.creeptenuous.desktop.core.reactive

import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveIndependentTrigger
import kotlin.reflect.KClass

/**
 * Annotation for calling an independent reactive trigger on classes that are not reactive collections
 * [ReactiveMutableList]
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class ReactiveIndependentTrigger(

    /**
     * Name of the event at which the [trigger] will be called
     */
    val event: String,

    /**
     * Calling trigger when an [event] fires
     */
    val trigger: KClass<out BaseReactiveIndependentTrigger>

)
