package com.zer0s2m.creeptenuous.desktop.core.reactive

import com.zer0s2m.creeptenuous.desktop.core.injection.ReactiveInjectionClass
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveIndependentTrigger
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveTrigger
import java.lang.reflect.Field
import kotlin.reflect.KClass

/**
 * A class that stores information about a reactive or lazy object.
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
