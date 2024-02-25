package com.zer0s2m.creeptenuous.desktop.core.reactive

import com.zer0s2m.creeptenuous.desktop.core.injection.ReactiveInjectionClass
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveIndependentTrigger
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveTrigger
import java.lang.reflect.Field
import kotlin.reflect.KClass

/**
 * A class that stores information about a reactive or lazy object.
 *
 * @param isLazy Whether the object is [Lazy].
 * @param isReactive Whether the object is [Reactive].
 * @param priority Data loading priority. The higher the priority the object will load first.
 * @param field Field which is [Reactive] or [Lazy] objects.
 * @param reactiveLazyObject [Reactive] or [Lazy] object.
 * @param handler Handler for later mounting an object into a [Lazy] or [Reactive] property.
 * @param handlerAfter Handler after main handler.
 * @param triggers Dependent triggers based on the type of property that is [Lazy] or [Reactive].
 * @param independentTriggers Independent triggers that do not depend on the underlying
 * type of [Reactive] or [Lazy] object.
 * @param injectionClass The class where the object will be mounted when it is loaded.
 * @param injectionMethod The method that will be called to mount the loaded object.
 * @param isLoad Whether the object is loaded.
 * @param sendIsLoad Whether to mount information that the object was downloaded and installed.
 * @param sendIsLoadInjectionClass The class into which information about the object being loaded will be mounted.
 * @param sendIsLoadInjectionMethod The method that will be called to mount information that the object is loaded.
 */
internal data class ReactiveLazy(

    val isLazy: Boolean,

    val isReactive: Boolean,

    val priority: Int = 1,

    val field: Field,

    val reactiveLazyObject: ReactiveLazyObject,

    val handler: KClass<out ReactiveHandler<Any>>,

    val handlerAfter: KClass<out ReactiveHandlerAfter>,

    val triggers: MutableMap<String, KClass<out BaseReactiveTrigger<Any>>> = mutableMapOf(),

    val independentTriggers: MutableMap<String, KClass<out BaseReactiveIndependentTrigger>> = mutableMapOf(),

    val injectionClass: KClass<out ReactiveInjectionClass>? = null,

    val injectionMethod: String = "",

    var isLoad: Boolean = false,

    var sendIsLoad: Boolean,

    val sendIsLoadInjectionClass: KClass<out ReactiveInjectionClass>? = null,

    val sendIsLoadInjectionMethod: String = ""

)
