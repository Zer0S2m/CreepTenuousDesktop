package core.reactive

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
    val event: String = "",

    /**
     * Lazy behavior handler to inject data into a property at a certain moment
     */
    val handler: KClass<out ReactiveHandler<T>>

)
