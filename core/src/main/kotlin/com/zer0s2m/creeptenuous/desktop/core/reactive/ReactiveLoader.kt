package com.zer0s2m.creeptenuous.desktop.core.reactive

import com.zer0s2m.creeptenuous.desktop.core.env.Environment
import com.zer0s2m.creeptenuous.desktop.core.errors.ReactiveLoaderException
import com.zer0s2m.creeptenuous.desktop.core.errors.ReactiveLoaderIndependentInjectionException
import com.zer0s2m.creeptenuous.desktop.core.injection.ReactiveIndependentInjection
import com.zer0s2m.creeptenuous.desktop.core.injection.ReactiveInjection
import com.zer0s2m.creeptenuous.desktop.core.injection.ReactiveInjectionClass
import com.zer0s2m.creeptenuous.desktop.core.logging.infoDev
import com.zer0s2m.creeptenuous.desktop.core.logging.logger
import com.zer0s2m.creeptenuous.desktop.core.pipeline.ReactivePipeline
import com.zer0s2m.creeptenuous.desktop.core.pipeline.ReactivePipelineHandler
import com.zer0s2m.creeptenuous.desktop.core.pipeline.ReactivePipelineType
import com.zer0s2m.creeptenuous.desktop.core.reactive.backend.Ktor
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveIndependentTrigger
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveTrigger
import org.slf4j.Logger
import kotlin.reflect.KClass
import kotlin.reflect.full.callSuspend
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.findAnnotations
import kotlin.reflect.full.functions
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.memberProperties

private object ReactiveLoaderStorage {

    /**
     * The main storage of values that will be loaded or will be loaded
     */
    val mapReactiveLazyObjects: MutableMap<String, ReactiveLazy> = mutableMapOf()

    /**
     * Core node storage for reactive and lazy properties
     */
    val mapNodes: MutableMap<String, MutableCollection<ReactiveLazyNode>> = mutableMapOf()

    /**
     * Reactive pipelines map.
     */
    val mapPipelines: MutableMap<String, InfoPipeline> = mutableMapOf()

    /**
     * Independent objects for managing injection into classes for mounting data.
     */
    val mapInjectionIndependent: MutableMap<KClass<out ReactiveInjectionClass>, Collection<String>> = mutableMapOf()

}

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
 * Main repository of pipeline information.
 */
private data class InfoPipeline(

    val type: ReactivePipelineType,

    val handler: KClass<out ReactivePipelineHandler<Any>>,

    val namePropertyReactiveLazy: String

)

/**
 * Information about reactive injections.
 */
private data class InfoInjectionClass(

    val method: String = "",

    val injectionClass: KClass<out ReactiveInjectionClass>? = null

)

/**
 * Main class for regulating reactive and lazy properties.
 */
object ReactiveLoader {

    internal val logger: Logger = logger()

    private var isBlockLoad: Boolean = false

    /**
     * Collect a map of jet triggers.
     *
     * @param triggers Reaction triggers.
     * @return Reaction trigger map.
     */
    private fun collectTriggers(triggers: Array<ReactiveTrigger<Any>>):
            MutableMap<String, KClass<out BaseReactiveTrigger<Any>>> {
        val collectedTriggers: MutableMap<String, KClass<out BaseReactiveTrigger<Any>>> = mutableMapOf()
        if (triggers.isNotEmpty()) {
            triggers.forEach { collectedTriggers[it.event] = it.trigger }
        }
        return collectedTriggers
    }

    /**
     * Collect a map of independent jet triggers.
     *
     * @param triggers Independent reaction triggers.
     * @return Independent reaction trigger map.
     */
    private fun collectIndependentTriggers(triggers: Array<ReactiveIndependentTrigger>):
            MutableMap<String, KClass<out BaseReactiveIndependentTrigger>> {
        val collectedTriggers: MutableMap<String, KClass<out BaseReactiveIndependentTrigger>> = mutableMapOf()
        if (triggers.isNotEmpty()) {
            triggers.forEach { collectedTriggers[it.event] = it.trigger }
        }
        return collectedTriggers
    }

    /**
     * Collect all reactive classes for loading data and building an auxiliary map for loading data
     *
     * @param classes Reactive classes for collecting data
     * @param injectionClasses collection of classes in which static methods are marked as
     * reactive injections in which a future value will be set.
     */
    suspend fun collectLoader(
        classes: Collection<ReactiveLazyObject>,
        injectionClasses: Iterable<KClass<out ReactiveInjectionClass>> = listOf(),
        injectionIndependentClasses: Iterable<KClass<out ReactiveInjectionClass>> = listOf(),
    ) {
        val mapInjectionClasses: MutableMap<KClass<out ReactiveInjectionClass>, Collection<String>> = mutableMapOf()
        val mapInjectionIndependentClasses: MutableMap<KClass<out ReactiveInjectionClass>, Collection<String>> =
            mutableMapOf()

        injectionClasses.forEach { klass ->
            val injectionMethods = klass.companionObject?.functions?.filter {
                it.findAnnotations<ReactiveInjection>().isNotEmpty()
            }
            if (injectionMethods != null) {
                mapInjectionClasses[klass] = injectionMethods.map { it.name }
            }
        }

        injectionIndependentClasses.forEach { klass ->
            val injectionMethods = klass.companionObject?.functions?.filter {
                it.findAnnotations<ReactiveIndependentInjection>().isNotEmpty()
            }
            if (injectionMethods != null) {
                mapInjectionIndependentClasses[klass] = injectionMethods.map { it.name }
            }
        }

        buildReactiveAndLazyClasses(classes, mapInjectionClasses, mapInjectionIndependentClasses)
        writeLogsToConsoleForCollectObjects()

        if (!getIsBlockLoad()) {
            loadNode()
            setReactiveValues(
                isReactive = true,
                isLazy = false
            )
        }
    }

    /**
     * Collect all reactive classes to load data for use.
     *
     * @param classes Reactive classes for collecting data.
     * @param injectionClasses Map of classes to which reactive and lazy properties will be
     * implemented through the specified methods.
     */
    private fun buildReactiveAndLazyClasses(
        classes: Collection<ReactiveLazyObject>,
        injectionClasses: Map<KClass<out ReactiveInjectionClass>, Collection<String>>,
        injectionIndependentClasses: Map<KClass<out ReactiveInjectionClass>, Collection<String>>
    ) {
        ReactiveLoaderStorage.mapInjectionIndependent.putAll(injectionIndependentClasses)

        classes.forEach { reactiveLazyObject ->
            reactiveLazyObject::class.memberProperties.forEach { kProperty ->
                val annotationLazy = kProperty.findAnnotation<Lazy<Any>>()
                val annotationReactive = kProperty.findAnnotation<Reactive<Any>>()
                if (annotationLazy != null) {
                    val propertyNode: Node = annotationLazy.node

                    val injectionData = setReactiveInjection(annotationLazy.injection, injectionClasses)
                    val injectionIsLoad = setReactiveInjection(annotationLazy.sendIsLoad.injection, injectionClasses)

                    @Suppress("NAME_SHADOWING")
                    val reactiveLazyObject = ReactiveLazy(
                        isLazy = true,
                        isReactive = false,
                        field = reactiveLazyObject::class.java.getDeclaredField(kProperty.name),
                        reactiveLazyObject = reactiveLazyObject,
                        handler = annotationLazy.handler,
                        handlerAfter = annotationLazy.handlerAfter,
                        triggers = collectTriggers(annotationLazy.triggers),
                        independentTriggers = collectIndependentTriggers(annotationLazy.independentTriggers),
                        injectionClass = injectionData.injectionClass,
                        injectionMethod = injectionData.method,
                        sendIsLoad = annotationLazy.sendIsLoad.isSend,
                        sendIsLoadInjectionClass = injectionIsLoad.injectionClass,
                        sendIsLoadInjectionMethod = injectionIsLoad.method
                    )
                    ReactiveLoaderStorage.mapReactiveLazyObjects[kProperty.name] = reactiveLazyObject
                    if (propertyNode.type != NodeType.NONE) {
                        collectNodes(propertyNode, reactiveLazyObject)
                    }
                    if (annotationLazy.pipelines.isNotEmpty()) {
                        collectPipelines(annotationLazy.pipelines, kProperty.name)
                    }
                } else if (annotationReactive != null) {
                    val propertyNode: Node = annotationReactive.node

                    val injectionData = setReactiveInjection(annotationReactive.injection, injectionClasses)
                    val injectionIsLoad = setReactiveInjection(
                        annotationReactive.sendIsLoad.injection, injectionClasses
                    )

                    @Suppress("NAME_SHADOWING")
                    val reactiveLazyObject = ReactiveLazy(
                        isLazy = false,
                        isReactive = true,
                        priority = annotationReactive.priority,
                        field = reactiveLazyObject::class.java.getDeclaredField(kProperty.name),
                        reactiveLazyObject = reactiveLazyObject,
                        handler = annotationReactive.handler,
                        handlerAfter = annotationReactive.handlerAfter,
                        triggers = collectTriggers(annotationReactive.triggers),
                        independentTriggers = collectIndependentTriggers(annotationReactive.independentTriggers),
                        injectionClass = injectionData.injectionClass,
                        injectionMethod = injectionData.method,
                        sendIsLoad = annotationReactive.sendIsLoad.isSend,
                        sendIsLoadInjectionClass = injectionIsLoad.injectionClass,
                        sendIsLoadInjectionMethod = injectionIsLoad.method
                    )
                    ReactiveLoaderStorage.mapReactiveLazyObjects[kProperty.name] = reactiveLazyObject
                    if (propertyNode.type != NodeType.NONE) {
                        collectNodes(propertyNode, reactiveLazyObject)
                    }
                    if (annotationReactive.pipelines.isNotEmpty()) {
                        collectPipelines(annotationReactive.pipelines, kProperty.name)
                    }
                } else if (kProperty.hasAnnotation<Lazy<Any>>() && kProperty.hasAnnotation<Reactive<Any>>()) {
                    throw ReactiveLoaderException("Parameter [${kProperty.name}] must have only one annotation")
                }
            }
        }

        sortByPriority()
    }

    /**
     * Sort reactive and lazy objects by priority.
     */
    private fun sortByPriority() {
        val newMap: MutableMap<String, ReactiveLazy> = mutableMapOf()

        ReactiveLoaderStorage.mapReactiveLazyObjects.entries
            .sortedWith(compareByDescending { it.value.priority })
            .map {
                newMap.put(it.key, it.value)
            }

        ReactiveLoaderStorage.mapReactiveLazyObjects.clear()
        ReactiveLoaderStorage.mapReactiveLazyObjects.putAll(newMap)
    }

    /**
     * Sets the mentee for dependency injection.
     *
     * @param injection Information about the injection.
     * @param injection Map of classes to which reactive and lazy properties will be
     * implemented through the specified methods.
     */
    private fun setReactiveInjection(
        injection: ReactiveInjection,
        injectionClasses: Map<KClass<out ReactiveInjectionClass>, Collection<String>>
    ): InfoInjectionClass {
        if (injection.method.isNotEmpty()) {
            val filteredInjectionClasses = injectionClasses.filterValues {
                it.contains(injection.method)
            }

            if (filteredInjectionClasses.isNotEmpty()) {
                val (key, _) = filteredInjectionClasses.entries.iterator().next()
                return InfoInjectionClass(injection.method, key)
            }
        }

        return InfoInjectionClass()
    }

    /**
     * Load a single property of lazy behavior from the map of objects that are collected with [collectLoader]
     *
     * @param nameProperty The name of the lazy behavior property is specified using an annotation
     * [Lazy] or [Reactive]
     */
    suspend fun load(nameProperty: String) {
        if (getIsBlockLoad()) {
            return
        }

        val lazyObject: ReactiveLazy? = ReactiveLoaderStorage.mapReactiveLazyObjects[nameProperty]
        if (lazyObject != null && !lazyObject.isLoad) {
            setReactiveValue(reactiveLazyObject = lazyObject)
            lazyObject.isLoad = true

            logger.infoDev(
                "Loading a lazy o reactive property:\n\t[" +
                        "${lazyObject.reactiveLazyObject.javaClass.canonicalName}] [$nameProperty]"
            )
        }
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun <T> loadNotSet(nameProperty: String): Result<T> {
        val lazyObject: ReactiveLazy? = ReactiveLoaderStorage.mapReactiveLazyObjects[nameProperty]

        if (lazyObject != null) {
            val loadedData: Any? = setReactiveValue(reactiveLazyObject = lazyObject, isSetData = false)
            if (loadedData != null) {
                return Result.success(loadedData as T)
            }
        }

        return Result.failure(ReactiveLoaderException("Reactive or lazy property not found"))
    }

    /**
     * Reactive property purification.
     *
     * @param nameProperty The name of the lazy behavior property is specified using an annotation
     * [Lazy] or [Reactive]
     */
    fun resetIsLoad(nameProperty: String) {
        val reactiveLazyObject: ReactiveLazy? = ReactiveLoaderStorage.mapReactiveLazyObjects[nameProperty]
        if (reactiveLazyObject != null) {
            reactiveLazyObject.isLoad = false
            sendIsLoadData(reactiveLazyObject, false)

            logger.infoDev(
                "Reactive property purification:\n\t[" +
                        "${reactiveLazyObject.reactiveLazyObject.javaClass.canonicalName}] [$nameProperty]"
            )
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
    suspend fun <T : Any> setReactiveValue(nameProperty: String, event: String, data: T, useOldData: Boolean = false) {
        val reactiveLazyObject: ReactiveLazy? = ReactiveLoaderStorage.mapReactiveLazyObjects[nameProperty]
        if (reactiveLazyObject != null && reactiveLazyObject.triggers.containsKey(event)) {
            val trigger: KClass<out BaseReactiveTrigger<Any>>? = reactiveLazyObject.triggers[event]

            val pipelines: Iterable<String> = gePipelinesByNameProperty(nameProperty)

            pipelineLaunch(pipelines, data, ReactivePipelineType.BEFORE)

            if (!useOldData) {
                reactiveLazyObject.field.set(reactiveLazyObject.reactiveLazyObject, data)
                trigger?.createInstance()?.execution(data)
            } else {
                trigger?.createInstance()?.execution(
                    reactiveLazyObject.field.get(reactiveLazyObject.reactiveLazyObject), data
                )
                reactiveLazyObject.field.set(reactiveLazyObject.reactiveLazyObject, data)
            }

            pipelineLaunch(pipelines, data, ReactivePipelineType.AFTER)
        }
    }

    /**
     * Call an independent trigger [BaseReactiveIndependentTrigger].
     *
     * @param nameProperty The name of the lazy behavior property is specified using an annotation
     * [Lazy] or [Reactive]
     * @param event Name of the event at which the reactive trigger should be executed.
     * @param data Data.
     */
    suspend fun executionIndependentTrigger(nameProperty: String, event: String, vararg data: Any?) {
        val reactiveLazyObject: ReactiveLazy? = ReactiveLoaderStorage.mapReactiveLazyObjects[nameProperty]
        if (reactiveLazyObject != null && reactiveLazyObject.independentTriggers.containsKey(event)) {
            val trigger: KClass<out BaseReactiveIndependentTrigger>? = reactiveLazyObject.independentTriggers[event]

            val pipelines: Iterable<String> = gePipelinesByNameProperty(nameProperty)

            pipelineLaunch(pipelines, data, ReactivePipelineType.BEFORE)
            trigger?.createInstance()?.execution(*data)
            pipelineLaunch(pipelines, data, ReactivePipelineType.AFTER)
        }
    }

    /**
     * Call the pipeline by its name.
     *
     * @param pipeline The name of the pipeline indicated in [ReactivePipeline.title].
     * @param value The transmitted value specified in [ReactivePipelineHandler.launch].
     */
    fun pipelineLaunch(pipeline: String, value: Any) {
        val infoPipeline = ReactiveLoaderStorage.mapPipelines[pipeline]
        infoPipeline?.handler?.createInstance()?.launch(value)
    }

    /**
     * Call the pipeline by its name.
     *
     * @param pipeline The name of the pipeline indicated in [ReactivePipeline.title].
     * @param value The transmitted value specified in [ReactivePipelineHandler.launch].
     */
    fun pipelineLaunch(pipeline: Iterable<String>, value: Any) {
        pipeline.forEach {
            val infoPipeline = ReactiveLoaderStorage.mapPipelines[it]
            infoPipeline?.handler?.createInstance()?.launch(value)
        }
    }

    /**
     * Call the pipeline by its name.
     *
     * @param pipeline The name of the pipeline indicated in [ReactivePipeline.title].
     * @param value The transmitted value specified in [ReactivePipelineHandler.launch].
     * @param type Pipeline type.
     */
    fun pipelineLaunch(pipeline: Iterable<String>, value: Any, type: ReactivePipelineType) {
        pipeline.forEach {
            val infoPipeline = ReactiveLoaderStorage.mapPipelines[it]
            if (infoPipeline != null && infoPipeline.type == type) {
                infoPipeline.handler.createInstance().launch(value)
            }
        }
    }

    /**
     * Check the type of pipeline specified in [ReactivePipeline.type].
     *
     * @param pipeline The name of the pipeline indicated in [ReactivePipeline.title].
     * @param type Pipeline type.
     */
    internal fun checkTypePipeline(pipeline: String, type: ReactivePipelineType): Boolean {
        if (ReactiveLoaderStorage.mapPipelines.containsKey(pipeline)) {
            return ReactiveLoaderStorage.mapPipelines[pipeline]?.type == type
        }
        return false
    }

    private fun gePipelinesByNameProperty(property: String): Iterable<String> {
        val pipelines: MutableList<String> = mutableListOf()
        ReactiveLoaderStorage.mapPipelines.forEach { (title, info) ->
            if (info.namePropertyReactiveLazy == property) {
                pipelines.add(title)
            }
        }
        return pipelines
    }

    /**
     * Get reactive bootloader locked.
     *
     * @return Is blocked.
     */
    fun getIsBlockLoad(): Boolean {
        return isBlockLoad
    }

    /**
     * Determine whether the reactive bootloader is private.
     *
     * @param isBlockLoad Is blocked.
     */
    fun setIsBlockLoad(isBlockLoad: Boolean) {
        this.isBlockLoad = isBlockLoad
    }

    /**
     * Run independent injection settings based on the method name and argument type.
     *
     * @param method The name of the method where the injection will be applied.
     * @param value Embedded data.
     */
    fun runReactiveIndependentInjection(method: String, value: Any) {
        ReactiveLoaderStorage.mapInjectionIndependent.forEach { (kClass: KClass<out ReactiveInjectionClass>,
                                                                    methods: Collection<String>) ->
            if (methods.contains(method)) {
                val compObject = kClass.companionObject
                if (compObject != null) {
                    compObject.functions.find {
                        it.name == method
                    }?.call(kClass.companionObjectInstance, value)
                    return
                }
            }
        }

        throw ReactiveLoaderIndependentInjectionException("Method not found")
    }

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
    if (!ReactiveLoaderStorage.mapNodes.containsKey(node.unit)) {
        ReactiveLoaderStorage.mapNodes[node.unit] = mutableListOf(reactiveLazyNodeObject)
    } else {
        ReactiveLoaderStorage.mapNodes[node.unit]?.add(reactiveLazyNodeObject)
    }
}

/**
 * Jet piping assembly.
 */
private fun collectPipelines(pipelines: Array<ReactivePipeline<Any>>, namePropertyReactiveLazy: String) {
    pipelines.forEach {
        ReactiveLoaderStorage.mapPipelines[it.title] = InfoPipeline(
            type = it.type,
            handler = it.pipeline,
            namePropertyReactiveLazy = namePropertyReactiveLazy
        )
    }
}

private suspend fun loadNode() {
    ReactiveLoaderStorage.mapNodes.forEach { (_, nodes) ->
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
    ReactiveLoaderStorage.mapReactiveLazyObjects.forEach { (_, value) ->
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
private suspend fun setReactiveValue(
    reactiveLazyObject: ReactiveLazy,
    isSetData: Boolean = true
): Any? {
    val field = reactiveLazyObject.field
    val methodHandler = reactiveLazyObject.handler.declaredMemberFunctions.find {
        it.name == HandlerTarget.HANDLER_NAME.method
    }
    val methodHandlerAfter = reactiveLazyObject.handlerAfter.declaredMemberFunctions.find {
        it.name == HandlerTarget.HANDLER_AFTER_NAME.method
    }

    if (methodHandler != null) {
        field.isAccessible = true

        val objectFromHandler = methodHandler.callSuspend(reactiveLazyObject.handler.objectInstance)

        if (isSetData) {
            field.set(reactiveLazyObject.reactiveLazyObject, objectFromHandler)

            reactiveLazyObject.isLoad = true

            if (methodHandlerAfter != null
                && reactiveLazyObject.handlerAfter.objectInstance != null
            ) {
                methodHandlerAfter.callSuspend(reactiveLazyObject.handlerAfter.objectInstance)
            }

            runInjectionMethod(reactiveLazyObject, objectFromHandler)
        }

        return objectFromHandler
    }

    return null
}

internal fun runInjectionMethod(reactiveLazyObject: ReactiveLazy, objectFromHandler: Any?) {
    if (reactiveLazyObject.injectionClass != null && reactiveLazyObject.injectionMethod.isNotEmpty()) {
        val compObject = reactiveLazyObject.injectionClass.companionObject
        if (compObject != null) {
            compObject.functions.find {
                it.name == reactiveLazyObject.injectionMethod
            }?.call(reactiveLazyObject.injectionClass.companionObjectInstance, objectFromHandler)
        }

        sendIsLoadData(reactiveLazyObject)
    }
}

internal fun sendIsLoadData(reactiveLazyObject: ReactiveLazy, isLoad: Boolean = true) {
    if (reactiveLazyObject.sendIsLoadInjectionClass != null
        && reactiveLazyObject.sendIsLoadInjectionMethod.isNotEmpty()
        && reactiveLazyObject.sendIsLoad
    ) {
        val compObject = reactiveLazyObject.sendIsLoadInjectionClass.companionObject
        if (compObject != null) {
            compObject.functions.find {
                it.name == reactiveLazyObject.sendIsLoadInjectionMethod
            }?.call(reactiveLazyObject.sendIsLoadInjectionClass.companionObjectInstance, isLoad)
        }
    }
}

private fun writeLogsToConsoleForCollectObjects() {
    if (Environment.IS_DEV) {
        // Classes
        var log = "Assembly of reactive and lazy objects:\n"
        ReactiveLoaderStorage.mapReactiveLazyObjects.forEach { (property, clazz) ->
            val isType: String = if (clazz.isReactive) "Reactive" else "Lazy"
            log += "\t[${clazz.reactiveLazyObject.javaClass.canonicalName}] [$property] - $isType\n"
        }
        ReactiveLoader.logger.infoDev(log.trim())

        // Nodes
        log = "Assembly of nodes for reactive and lazy objects:\n"
        ReactiveLoaderStorage.mapNodes.forEach { (property, clazz) ->
            log += "\t[node: $property] classes:\n"
            clazz.forEach {
                val className: String = it.reactiveLazyObject.reactiveLazyObject.javaClass.canonicalName
                val propertyName: String = it.reactiveLazyObject.field.name
                log += "\t    [$className] [$propertyName]\n"
            }
        }
        ReactiveLoader.logger.infoDev(log.trim())

        // Pipelines
        log = "Jet piping assembly:\n"
        ReactiveLoaderStorage.mapPipelines.forEach { (title, info) ->
            log += "\t[pipeline: $title] \n\t    [handler: ${info.handler}]\n\t    [property: " +
                    info.namePropertyReactiveLazy + "]\n"
        }

        ReactiveLoader.logger.infoDev(log.trim())
    }
}
