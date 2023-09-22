package com.zer0s2m.creeptenuous.desktop.core.reactive

import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveTrigger
import kotlin.reflect.KClass

/**
 * Annotation for calling a reactive trigger on classes that are not reactive collections [ReactiveMutableList].
 *
 * @param T The type of the variable that will take [BaseReactiveTrigger.execution].
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class ReactiveTrigger<T>(

    /**
     * Name of the event at which the [trigger] will be called
     */
    val event: String,

    /**
     * Calling trigger when an [event] fires
     */
    val trigger: KClass<out BaseReactiveTrigger<T>>

)
