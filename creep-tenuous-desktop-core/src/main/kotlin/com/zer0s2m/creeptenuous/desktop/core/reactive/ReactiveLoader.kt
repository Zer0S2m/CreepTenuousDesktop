package com.zer0s2m.creeptenuous.desktop.core.reactive

import com.zer0s2m.creeptenuous.desktop.core.env.Environment
import com.zer0s2m.creeptenuous.desktop.core.errors.ReactiveLoaderException
import com.zer0s2m.creeptenuous.desktop.core.injection.ReactiveInjection
import com.zer0s2m.creeptenuous.desktop.core.injection.ReactiveInjectionClass
import com.zer0s2m.creeptenuous.desktop.core.logging.infoDev
import com.zer0s2m.creeptenuous.desktop.core.logging.logger
import com.zer0s2m.creeptenuous.desktop.core.reactive.backend.Ktor
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveTrigger
import org.slf4j.Logger
import java.lang.reflect.Field
import kotlin.reflect.KClass
import kotlin.reflect.full.*

/**
 * The main storage of values that will be loaded or will be loaded
 */
private val mapReactiveLazyObjects: MutableMap<String, ReactiveLazy> = mutableMapOf()

/**
 * Core node storage for reactive and lazy properties
 */
private val mapNodes: MutableMap<String, MutableCollection<ReactiveLazyNode>> = mutableMapOf()

/**
 * A class that stores information about a reactive or lazy object
 */
internal data class ReactiveLazy(

    val isLazy: Boolean,

    val isReactive: Boolean,

    val field: Field,

    val reactiveLazyObject: ReactiveLazyObject,

    val handler: KClass<out ReactiveHandler<Any>>,

    val handlerAfter: KClass<out ReactiveHandlerAfter>,

    val triggers: MutableMap<String, KClass<out BaseReactiveTrigger<Any>>> = mutableMapOf(),

    val injectionClass: KClass<out ReactiveInjectionClass>? = null,

    val injectionMethod: String = "",

    var isLoad: Boolean = false

)

/**
 * The main class for storing information about the [Node]
 */
private data class ReactiveLazyNode(

    val title: String,

    val type: NodeType,

    val reactiveLazyObject: ReactiveLazy,

    val handler: ReactiveHandlerKtor? = null
)

/**
 * The name of the method for inverting a reactive property. [ReactiveHandler.handler]
 */
internal const val HANDLER_NAME = "handler"

/**
 * The name of the method for inverting a reactive property. [ReactiveHandlerAfter.action]
 */
internal const val HANDLER_AFTER_NAME = "action"

/**
 * Main data loader
 */
object ReactiveLoader {

    internal val logger: Logger = logger()

    /**
     * Load a single property of lazy behavior from the map of objects that are collected with [collectLoader]
     *
     * @param nameProperty The name of the lazy behavior property is specified using an annotation
     * [Lazy] or [Reactive]
     */
    suspend fun load(nameProperty: String) {
        val lazyObject: ReactiveLazy? = mapReactiveLazyObjects[nameProperty]
        if (lazyObject != null && !lazyObject.isLoad) {
            setReactiveValue(reactiveLazyObject = lazyObject)
            lazyObject.isLoad = true

            logger.infoDev("Loading a lazy property:\n\t[" +
                    "${lazyObject.reactiveLazyObject.javaClass.canonicalName}] [$nameProperty]")
        }
    }

    /**
     * Set the data for a specific event to a [Reactive] or [Lazy] property and call the
     * reactive trigger [BaseReactiveTrigger] when the data is set.
     *
     * @param nameProperty The name of the lazy behavior property is specified using an annotation
     * [Lazy] or [Reactive]
     * @param event Name of the event at which the reactive trigger should be executed.
     * @param data Data.
     * @param useOldData Whether to call the trigger using the old set data.
     */
    fun <T : Any> setReactiveValue(nameProperty: String, event: String, data: T, useOldData: Boolean = false) {
        val reactiveLazyObject: ReactiveLazy? = mapReactiveLazyObjects[nameProperty]
        if (reactiveLazyObject != null && reactiveLazyObject.triggers.containsKey(event)) {
            val trigger: KClass<out BaseReactiveTrigger<Any>>? = reactiveLazyObject.triggers[event]

            if (!useOldData) {
                reactiveLazyObject.field.set(reactiveLazyObject.reactiveLazyObject, data)
                trigger?.createInstance()?.execution(data)
            } else {
                trigger?.createInstance()?.execution(
                    reactiveLazyObject.field.get(reactiveLazyObject.reactiveLazyObject), data)
                reactiveLazyObject.field.set(reactiveLazyObject.reactiveLazyObject, data)
            }
        }
    }

}

/**
 * Collect all reactive classes for loading data and building an auxiliary map for loading data
 *
 * @param classes Reactive classes for collecting data
 * @param injectionClasses Map of classes to which reactive and lazy properties will be
 * implemented through the specified methods
 */
suspend fun collectLoader(
    classes: Collection<ReactiveLazyObject>,
    injectionClasses: HashMap<KClass<out ReactiveInjectionClass>, Collection<String>> = hashMapOf()
) {
    var injectionClass: KClass<out ReactiveInjectionClass>? = null
    var injectionMethod = ""

    /**
     * Sets the mentee for dependency injection
     *
     * @param injection Information about the injection
     */
    fun setReactiveInjection(injection: ReactiveInjection) {
        if (injection.method.isNotEmpty()) {
            val filteredInjectionClasses = injectionClasses.filterValues {
                it.contains(injection.method)
            }

            val (key, _) = filteredInjectionClasses.entries.iterator().next()

            injectionClass = key
            injectionMethod = injection.method
        }
    }

    classes.forEach { reactiveLazyObject ->
        reactiveLazyObject::class.memberProperties.forEach { kProperty ->
            val annotationLazy = kProperty.findAnnotation<Lazy<Any>>()
            val annotationReactive = kProperty.findAnnotation<Reactive<Any>>()
            if (annotationLazy != null) {
                val propertyNode: Node = annotationLazy.node

                setReactiveInjection(annotationLazy.injection)

                val triggers: Array<ReactiveTrigger<Any>> = annotationLazy.triggers
                val collectedTriggers: MutableMap<String, KClass<out BaseReactiveTrigger<Any>>> = mutableMapOf()
                if (triggers.isNotEmpty()) {
                    triggers.forEach {
                        collectedTriggers[it.event] = it.trigger
                    }
                }

                @Suppress("NAME_SHADOWING")
                val reactiveLazyObject = ReactiveLazy(
                    isLazy = true,
                    isReactive = false,
                    field = reactiveLazyObject::class.java.getDeclaredField(kProperty.name),
                    reactiveLazyObject = reactiveLazyObject,
                    handler = annotationLazy.handler,
                    handlerAfter = annotationLazy.handlerAfter,
                    triggers = collectedTriggers,
                    injectionClass = injectionClass,
                    injectionMethod = injectionMethod
                )
                mapReactiveLazyObjects[kProperty.name] = reactiveLazyObject
                if (propertyNode.type != NodeType.NONE) {
                    collectNodes(propertyNode, reactiveLazyObject)
                }
            } else if (annotationReactive != null) {
                val propertyNode: Node = annotationReactive.node

                setReactiveInjection(annotationReactive.injection)

                val triggers: Array<ReactiveTrigger<Any>> = annotationReactive.triggers
                val collectedTriggers: MutableMap<String, KClass<out BaseReactiveTrigger<Any>>> = mutableMapOf()
                if (triggers.isNotEmpty()) {
                    triggers.forEach {
                        collectedTriggers[it.event] = it.trigger
                    }
                }

                @Suppress("NAME_SHADOWING")
                val reactiveLazyObject = ReactiveLazy(
                    isLazy = false,
                    isReactive = true,
                    field = reactiveLazyObject::class.java.getDeclaredField(kProperty.name),
                    reactiveLazyObject = reactiveLazyObject,
                    handler = annotationReactive.handler,
                    handlerAfter = annotationReactive.handlerAfter,
                    triggers = collectedTriggers,
                    injectionClass = injectionClass,
                    injectionMethod = injectionMethod
                )
                mapReactiveLazyObjects[kProperty.name] = reactiveLazyObject
                if (propertyNode.type != NodeType.NONE) {
                    collectNodes(propertyNode, reactiveLazyObject)
                }
            } else if (kProperty.hasAnnotation<Lazy<Any>>() && kProperty.hasAnnotation<Reactive<Any>>()) {
                throw ReactiveLoaderException("Parameter [${kProperty.name}] must have only one annotation")
            }
        }

        injectionClass = null
        injectionMethod = ""
    }

    loadNode()

    writeLogsToConsoleForCollectObjects()

    setReactiveValues(
        isReactive = true,
        isLazy = false
    )
}

/**
 * Assembling nodes into a map
 */
private fun collectNodes(node: Node, reactiveLazyObject: ReactiveLazy) {
    val reactiveLazyNodeObject = ReactiveLazyNode(
        title = node.unit,
        type = node.type,
        reactiveLazyObject = reactiveLazyObject,
        handler = reactiveLazyObject.handler.objectInstance
    )
    if (!mapNodes.containsKey(node.unit)) {
        mapNodes[node.unit] = mutableListOf(reactiveLazyNodeObject)
    } else {
        mapNodes[node.unit]?.add(reactiveLazyNodeObject)
    }
}

private suspend fun loadNode() {
    mapNodes.forEach { (_, nodes) ->
        val nodesTypeKtor: MutableList<ReactiveLazyNode> = findNodes(nodes = nodes, type = NodeType.KTOR)
        val reactiveLazyObject: MutableList<ReactiveLazy> = mutableListOf()

        nodesTypeKtor.forEach {
            reactiveLazyObject.add(it.reactiveLazyObject)
        }

        if (nodesTypeKtor.isNotEmpty()) {
            nodesTypeKtor[0].handler?.let {
                Ktor.load(reactiveLazyObject = reactiveLazyObject, handler = it)
            }
        }
    }
}

/**
 * Finds all reactive properties with a specific node type
 *
 * @param nodes dirty reactive properties
 * @param type node type
 *
 * @return filtered reactive properties
 */
@Suppress("SameParameterValue")
private fun findNodes(
    nodes: MutableCollection<ReactiveLazyNode>,
    type: NodeType
): MutableList<ReactiveLazyNode> {
    val nodesTypeKtor: MutableList<ReactiveLazyNode> = mutableListOf()

    nodes.forEach { node ->
        if (node.type == type) {
            nodesTypeKtor.add(node)
        }
    }

    return nodesTypeKtor
}

/**
 * Set reactive values via map
 */
private suspend fun setReactiveValues(isReactive: Boolean, isLazy: Boolean) {
    mapReactiveLazyObjects.forEach { (_, value) ->
        if (isReactive && value.isReactive && !value.isLoad) {
            setReactiveValue(reactiveLazyObject = value)
        }
        if (isLazy && value.isLazy && !value.isLoad) {
            setReactiveValue(reactiveLazyObject = value)
        }
    }
}

/**
 * Set reactive value via map
 */
private suspend fun setReactiveValue(reactiveLazyObject: ReactiveLazy) {
    val field = reactiveLazyObject.field
    val methodHandler = reactiveLazyObject.handler.declaredMemberFunctions.find {
        it.name == HANDLER_NAME
    }
    val methodHandlerAfter = reactiveLazyObject.handlerAfter.declaredMemberFunctions.find {
        it.name == HANDLER_AFTER_NAME
    }

    if (methodHandler != null) {
        field.isAccessible = true

        val objectFromHandler = methodHandler.callSuspend(reactiveLazyObject.handler.objectInstance)

        field.set(reactiveLazyObject.reactiveLazyObject, objectFromHandler)

        reactiveLazyObject.isLoad = true

        if (methodHandlerAfter != null
            && reactiveLazyObject.handlerAfter.objectInstance != null) {
            methodHandlerAfter.callSuspend(reactiveLazyObject.handlerAfter.objectInstance)
        }

        if (reactiveLazyObject.injectionClass != null && reactiveLazyObject.injectionMethod.isNotEmpty()) {
            val compObject = reactiveLazyObject.injectionClass.companionObject
            if (compObject != null) {
                compObject.functions.find {
                    it.name == reactiveLazyObject.injectionMethod
                }?.call(reactiveLazyObject.injectionClass.companionObjectInstance, objectFromHandler)
            }
        }
    }
}

private fun writeLogsToConsoleForCollectObjects() {
    if (Environment.IS_DEV) {
        // Classes
        var log = "Assembly of reactive and lazy objects:\n"
        mapReactiveLazyObjects.forEach { (property, clazz) ->
            val isType: String = if (clazz.isReactive) "Reactive" else "Lazy"
            log += "\t[${clazz.reactiveLazyObject.javaClass.canonicalName}] [$property] - $isType\n"
        }
        ReactiveLoader.logger.infoDev(log.trim())

        // Nodes
        log = "Assembly of nodes for reactive and lazy objects:\n"
        mapNodes.forEach { (property, clazz) ->
            log += "\t[node: $property] classes:\n"
            clazz.forEach {
                val className: String = it.reactiveLazyObject.reactiveLazyObject.javaClass.canonicalName
                val propertyName: String = it.reactiveLazyObject.field.name
                log += "\t    [$className] [$propertyName]\n"
            }
        }
        ReactiveLoader.logger.infoDev(log.trim())
    }
}
