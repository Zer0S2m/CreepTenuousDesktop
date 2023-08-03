package com.zer0s2m.creeptenuous.desktop.core.reactive

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
    val handler: KClass<out ReactiveHandler<T>>

)
