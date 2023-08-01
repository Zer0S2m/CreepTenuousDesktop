package core.reactive

import kotlin.reflect.KClass

/**
 * The annotation specifies that the data will be loaded when the application starts.
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
