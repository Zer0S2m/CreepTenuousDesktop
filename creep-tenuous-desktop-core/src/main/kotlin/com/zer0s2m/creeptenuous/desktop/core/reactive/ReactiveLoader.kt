package com.zer0s2m.creeptenuous.desktop.core.reactive

import com.zer0s2m.creeptenuous.desktop.core.errors.ReactiveLoaderException
import java.lang.reflect.Field
import kotlin.reflect.KClass
import kotlin.reflect.full.*

/**
 * the main storage of values that will be loaded or will be loaded
 */
private val map: MutableMap<String, ReactiveLazy> = mutableMapOf()

/**
 * A class that stores information about a reactive or lazy object
 */
private data class ReactiveLazy(

    val isLazy: Boolean,

    val isReactive: Boolean,

    val field: Field,

    val reactiveLazyObject: ReactiveLazyObject,

    val handler: KClass<out ReactiveHandler<Any>>,

    var isLoad: Boolean = false

)

/**
 * The name of the method for inverting a reactive property. [ReactiveHandler.handler]
 */
private const val HANDLER_NAME = "handler"

/**
 * Main data loader
 */
object Loader {

    /**
     * Load a single property of lazy behavior from the map of objects that are collected with [collectLoader]
     *
     * @param nameProperty The name of the lazy behavior property is specified using an annotation [Lazy]
     */
    suspend fun load(nameProperty: String) {
        val lazyObject: ReactiveLazy? = map[nameProperty]
        if (lazyObject != null && !lazyObject.isLoad) {
            setReactiveValue(reactiveLazyObject = lazyObject)
            lazyObject.isLoad = true
        }
    }

}

/**
 * Collect all reactive classes for loading data and building an auxiliary map for loading data
 *
 * @param classes Reactive classes for collecting data
 */
suspend fun collectLoader(classes: Collection<ReactiveLazyObject>) {
    classes.forEach { reactiveLazyObject ->
        reactiveLazyObject::class.memberProperties.forEach { kProperty ->
            val annotationLazy = kProperty.findAnnotation<Lazy<Any>>()
            val annotationReactive = kProperty.findAnnotation<Reactive<Any>>()
            if (annotationLazy != null) {
                map[kProperty.name] = ReactiveLazy(
                    isLazy = true,
                    isReactive = false,
                    field = reactiveLazyObject::class.java.getDeclaredField(kProperty.name),
                    reactiveLazyObject = reactiveLazyObject,
                    handler = annotationLazy.handler
                )
            } else if (annotationReactive != null) {
                map[kProperty.name] = ReactiveLazy(
                    isLazy = false,
                    isReactive = true,
                    field = reactiveLazyObject::class.java.getDeclaredField(kProperty.name),
                    reactiveLazyObject = reactiveLazyObject,
                    handler = annotationReactive.handler
                )
            } else if (kProperty.hasAnnotation<Lazy<Any>>() && kProperty.hasAnnotation<Reactive<Any>>()) {
                throw ReactiveLoaderException("Parameter [${kProperty.name}] must have only one annotation")
            }
        }
    }

    setReactiveValues(
        isReactive = true,
        isLazy = false
    )
}

/**
 * Set reactive values via map
 */
private suspend fun setReactiveValues(isReactive: Boolean, isLazy: Boolean) {
    map.forEach { (_, value) ->
        if (isReactive && value.isReactive) {
            setReactiveValue(reactiveLazyObject = value)
        }
        if (isLazy && value.isLazy) {
            setReactiveValue(reactiveLazyObject = value)
        }
    }
}

/**
 * Set reactive value via map
 */
private suspend fun setReactiveValue(reactiveLazyObject: ReactiveLazy) {
    val field = reactiveLazyObject.field
    val method = reactiveLazyObject.handler.declaredMemberFunctions.find {
        it.name == HANDLER_NAME
    }
    if (method != null) {
        field.isAccessible = true
        field.set(
            reactiveLazyObject.reactiveLazyObject,
            method.callSuspend(reactiveLazyObject.handler.objectInstance)
        )
        reactiveLazyObject.isLoad = true
    }
}
