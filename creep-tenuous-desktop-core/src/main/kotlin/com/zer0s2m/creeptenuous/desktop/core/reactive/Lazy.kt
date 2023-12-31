package com.zer0s2m.creeptenuous.desktop.core.reactive

import com.zer0s2m.creeptenuous.desktop.core.injection.ReactiveInjection
import com.zer0s2m.creeptenuous.desktop.core.pipeline.ReactivePipeline
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveIndependentTrigger
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveTrigger
import kotlin.reflect.KClass

/**
 * The annotation defines that the data will be loaded at a certain moment.
 *
 * Before specifying an annotation before a property, it is required to exclude `final`.
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class Lazy<T>(

    /**
     * Memo at which event will load data.
     *
     * Doesn't affect the system at all.
     */
    @Suppress("UNUSED")
    val event: String = "",

    /**
     * Lazy behavior handler to inject data into a property at a certain moment
     */
    val handler: KClass<out ReactiveHandler<T>>,

    /**
     * Node for injecting objects into other reactive or lazy objects
     */
    val node: Node = Node(),

    /**
     * List of handlers that run after the object is loaded [handler]
     */
    val handlerAfter: KClass<out ReactiveHandlerAfter> = ReactiveHandlerAfter::class,

    /**
     * Injection to inject end object after handlers
     */
    val injection: ReactiveInjection = ReactiveInjection(),

    /**
     * Reactive triggers when setting new data to a property.
     */
    val triggers: Array<ReactiveTrigger<T>> = [],

    /**
     * Independent reactive triggers fire when new data is set to a property.
     */
    val independentTriggers: Array<ReactiveIndependentTrigger> = [],

    /**
     * Reactive pipelines that are called before or after the completion of a reactive trigger.
     * [BaseReactiveIndependentTrigger] or [BaseReactiveTrigger].
     */
    val pipelines: Array<ReactivePipeline<Any>> = [],

    /**
     * Should I send information that the data has been downloaded.
     */
    val sendIsLoad: ReactiveSendIsLoad = ReactiveSendIsLoad()

)
