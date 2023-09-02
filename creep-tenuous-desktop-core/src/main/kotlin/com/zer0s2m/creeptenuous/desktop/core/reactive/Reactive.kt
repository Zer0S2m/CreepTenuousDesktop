package com.zer0s2m.creeptenuous.desktop.core.reactive

import com.zer0s2m.creeptenuous.desktop.core.injection.ReactiveInjection
import kotlin.reflect.KClass

/**
 * The annotation specifies that the data will be loaded when the application starts.
 *
 * Before specifying an annotation before a property, it is required to exclude `final`.
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class Reactive<T>(

    /**
     * Reactive behavior handler to inject data into a property
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
    val injection: ReactiveInjection = ReactiveInjection()

)
