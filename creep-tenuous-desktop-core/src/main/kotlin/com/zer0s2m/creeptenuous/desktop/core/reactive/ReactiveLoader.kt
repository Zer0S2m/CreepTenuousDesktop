package com.zer0s2m.creeptenuous.desktop.core.reactive

import com.zer0s2m.creeptenuous.desktop.core.errors.ReactiveLoaderException
import com.zer0s2m.creeptenuous.desktop.core.reactive.backend.Ktor
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
object Loader {

    /**
     * Load a single property of lazy behavior from the map of objects that are collected with [collectLoader]
     *
     * @param nameProperty The name of the lazy behavior property is specified using an annotation [Lazy]
     */
    suspend fun load(nameProperty: String) {
        val lazyObject: ReactiveLazy? = mapReactiveLazyObjects[nameProperty]
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
                val propertyNode: Node = annotationLazy.node

                @Suppress("NAME_SHADOWING")
                val reactiveLazyObject = ReactiveLazy(
                    isLazy = true,
                    isReactive = false,
                    field = reactiveLazyObject::class.java.getDeclaredField(kProperty.name),
                    reactiveLazyObject = reactiveLazyObject,
                    handler = annotationLazy.handler,
                    handlerAfter = annotationLazy.handlerAfter
                )
                mapReactiveLazyObjects[kProperty.name] = reactiveLazyObject
                if (propertyNode.type != NodeType.NONE) {
                    collectNodes(propertyNode, reactiveLazyObject)
                }
            } else if (annotationReactive != null) {
                val propertyNode: Node = annotationReactive.node

                @Suppress("NAME_SHADOWING")
                val reactiveLazyObject = ReactiveLazy(
                    isLazy = false,
                    isReactive = true,
                    field = reactiveLazyObject::class.java.getDeclaredField(kProperty.name),
                    reactiveLazyObject = reactiveLazyObject,
                    handler = annotationReactive.handler,
                    handlerAfter = annotationReactive.handlerAfter,
                )
                mapReactiveLazyObjects[kProperty.name] = reactiveLazyObject
                if (propertyNode.type != NodeType.NONE) {
                    collectNodes(propertyNode, reactiveLazyObject)
                }
            } else if (kProperty.hasAnnotation<Lazy<Any>>() && kProperty.hasAnnotation<Reactive<Any>>()) {
                throw ReactiveLoaderException("Parameter [${kProperty.name}] must have only one annotation")
            }
        }
    }

    loadNode()

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

        field.set(
            reactiveLazyObject.reactiveLazyObject,
            methodHandler.callSuspend(reactiveLazyObject.handler.objectInstance)
        )

        reactiveLazyObject.isLoad = true
    }

    if (methodHandlerAfter != null
        && reactiveLazyObject.handlerAfter.objectInstance != null) {
        methodHandlerAfter.callSuspend(reactiveLazyObject.handlerAfter.objectInstance)
    }
}
