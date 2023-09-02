package com.zer0s2m.creeptenuous.desktop.core.injection

import com.zer0s2m.creeptenuous.desktop.core.reactive.Lazy
import com.zer0s2m.creeptenuous.desktop.core.reactive.Reactive
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveHandler
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLoader

/**
 * A base annotation to indicate that a [Reactive] or [Lazy] property is also an injection.
 *
 * Injected after all loading ([ReactiveLoader]) and preprocessor steps have been passed
 * through the specified [method] which is static
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class ReactiveInjection(

    /**
     * Static method that will be called after all handlers to inject the final
     * object of the type [ReactiveHandler.handler]
     */
    val method: String = ""

)
