package com.zer0s2m.creeptenuous.desktop.core.reactive

/**
 * Interface for defining lazy and reactive objects.
 * Non-lazy objects will be loaded instantly when the application starts, otherwise lazy objects will wait their turn to load (for example, when changing state from one screen to another).
 *
 * Provides the following features:
 * 1) Creating dependent reactive triggers
 * 2) Creating independent response triggers
 * 3) Load lazy and reactive properties at specific times
 * 4) Jet conveyors
 * 5) Ease of data injection
 * 6) Sending changes indicating that the data has been downloaded
 * 7) Node-based data injection
 *
 * ## Examples:
 *
 * ### Simple example of loading a reactive property:
 *
 * ```
 * object ReactiveClass : ReactiveLazyObject {
 *      @Reactive<Collection<String>>(
 *          handler = Handler::class
 *      )
 *      val reactiveProperty: Collection<String> = listOf()
 * }
 *
 * object Handler : ReactiveHandler<Collection<String>> {
 *      override suspend fun handler(): Collection<String> {
 *          return listOf("Test 1", "Test 2")
 *      }
 * }
 *
 * coroutineScope {
 *      ReactiveLoader.collectLoader(
 *          classes = listOf(ReactiveClass),
 *          injectionClasses = listOf()
 *      )
 * }
 * ```
 *
 * ### A simple example if you want to load data at some specific moment:
 *
 * ```
 * object ReactiveClass : ReactiveLazyObject {
 *      @Lazy<Collection<String>>(
 *          handler = Handler::class
 *      )
 *      val reactiveProperty: Collection<String> = listOf()
 * }
 *
 * object Handler : ReactiveHandler<Collection<String>> {
 *      override suspend fun handler(): Collection<String> {
 *          return listOf("Test 1", "Test 2")
 *      }
 * }
 *
 * coroutineScope {
 *      ReactiveLoader.collectLoader(
 *          classes = listOf(ReactiveClass),
 *          injectionClasses = listOf()
 *      )
 * }
 *
 * // The name of the property that is marked as reactive or lazy.
 * coroutineScope {
 *      ReactiveLoader.load("lazyProperty")
 * }
 * ```
 *
 * ### A simple example if you want to know when a reactive or lazy property will be loaded and inject it into a class:
 *
 * ```
 * object ReactiveClass : ReactiveLazyObject {
 *      @Reactive<Collection<String>>(
 *          handler = Handler::class,
 *          injection = ReactiveInjection(
 *              // The name of the injection methods must match the main names specified in main class.
 *              method = "setData"
 *          ),
 *          sendIsLoad = ReactiveSendIsLoad(
 *              isSend = true,
 *              injection = ReactiveInjection(
 *                  method = "setIsLoad"
 *              )
 *          )
 *      )
 *      val reactiveProperty: Collection<String> = listOf()
 * }
 *
 * object Handler : ReactiveHandler<Collection<String>> {
 *      override suspend fun handler(): Collection<String> {
 *          return listOf("Test 1", "Test 2")
 *      }
 * }
 *
 * class MainClass : ReactiveInjectionClass {
 *      internal companion object {
 *      // The names of methods for injection must match those specified in the main class for reactive and lazy properties.
 *          @ReactiveInjection
 *          internal fun setIsLoad(isLoad: Boolean) {
 *              println("Is load : $isLoad")
 *          }
 *
 *          @ReactiveInjection
 *          internal fun setData(data: Collection<String>) {
 *              println("Load data: $data")
 *          }
 *      }
 * }
 *
 * coroutineScope {
 *      ReactiveLoader.collectLoader(
 *          classes = listOf(ReactiveClass),
 *          injectionClasses = listOf(MainClass::class)
 *      )
 * }
 * ```
 *
 * ### A simple example using reactive collections:
 *
 * ```
 * object ReactiveClass : ReactiveLazyObject {
 *      @Reactive<ReactiveMutableList<String>>(
 *          handler = Handler::class
 *      )
 *      // You will definitely need to specify all triggers for the react collection in the handler since it overwrites past data
 *      val reactiveProperty: ReactiveMutableList<String> = mutableReactiveListOf(
 *          triggerAdd = ReactiveTriggerAdd(),
 *          triggerRemove = ReactiveTriggerRemove()
 *      )
 * }
 *
 * object Handler : ReactiveHandler<ReactiveMutableList<String>> {
 *      override suspend fun handler(): ReactiveMutableList<String> {
 *          return listOf("Test 1", "Test 2").toReactiveMutableList(
 *              triggerAdd = ReactiveTriggerAdd(),
 *              triggerRemove = ReactiveTriggerRemove()
 *          )
 *      }
 * }
 *
 * class ReactiveTriggerAdd : BaseReactiveTrigger<String> {
 *      override fun execution(value: String) {
 *          println("Add value: $value")
 *      }
 * }
 *
 * class ReactiveTriggerRemove : BaseReactiveTrigger<String> {
 *      override fun execution(value: String) {
 *          println("Remove value: $value")
 *      }
 * }
 *
 * coroutineScope {
 *      ReactiveLoader.collectLoader(
 *          classes = listOf(ReactiveClass),
 *          injectionClasses = listOf()
 *      )
 * }
 *
 * ReactiveClass.reactiveProperty.addReactive("Test 3") // Add value Test 3
 * ReactiveClass.reactiveProperty.removeAtReactive("Test 1") // Remove value Test 1
 * ```
 *
 * ### A simple example of using reactive collections with reactive pipelines:
 *
 * ```
 * object ReactiveClass : ReactiveLazyObject {
 *      @Reactive<ReactiveMutableList<String>>(
 *          handler = Handler::class,
 *          pipelines = [
 *              ReactivePipeline(
 *                  title = "addValue",
 *                  type = ReactivePipelineType.AFTER,
 *                  pipeline = ReactivePipelineAdd::class
 *              )
 *          ]
 *      )
 *      // You will definitely need to specify all triggers for the react collection in the handler since it overwrites past data
 *      val reactiveProperty: ReactiveMutableList<String> = mutableReactiveListOf(
 *          triggerAdd = ReactiveTriggerAdd(),
 *          pipelinesAdd = listOf("addValue")
 *      )
 * }
 *
 * object Handler : ReactiveHandler<ReactiveMutableList<String>> {
 *      override suspend fun handler(): ReactiveMutableList<String> {
 *          return listOf("Test 1", "Test 2").toReactiveMutableList(
 *              triggerAdd = ReactiveTriggerAdd()
 *              pipelinesAdd = listOf("addValue")
 *          )
 *      }
 * }
 *
 * class ReactiveTriggerAdd : BaseReactiveTrigger<String> {
 *      override fun execution(value: String) {
 *          println("Add value: $value")
 *      }
 * }
 *
 * // You should understand that reactive pipelines do not change or set data,
 * // but are just an additional abstraction block for triggering some events.
 * class ReactivePipelineAdd : ReactivePipelineHandler<String> {
 *      override fun launch(value: String) {
 *          println("Launch pipeline $value")
 *      }
 * }
 *
 * coroutineScope {
 *      ReactiveLoader.collectLoader(
 *          classes = listOf(ReactiveClass),
 *          injectionClasses = listOf()
 *      )
 * }
 *
 * ReactiveClass.reactiveProperty.addReactive("Test 3") // Add value Test 3 && Launch pipeline Test 3
 * ```
 *
 * ### A simple example of using dependent triggers based on input types and a reactive or lazy property:
 *
 * ```
 * data class DataClass(
 *      val id: Int? = null,
 *      val title: String? = null
 * )
 *
 * object ReactiveClass : ReactiveLazyObject {
 *      @Reactive<DataClass>(
 *          handler = Handler::class,
 *          triggers = [
 *              ReactiveTrigger(
 *                  event = "updateData",
 *                  trigger = ReactiveTriggerUpdateData::class
 *              )
 *          ]
 *      )
 *      val reactiveProperty: DataClass = DataClass()
 * }
 *
 * object Handler : ReactiveHandler<DataClass> {
 *      override suspend fun handler(): DataClass {
 *          return DataClass(1, "Title 1")
 *      }
 * }
 *
 * class ReactiveTriggerUpdateData : BaseReactiveTrigger<DataClass> {
 *      override fun execution(value: DataClass) {
 *          println("Update data")
 *      }
 * }
 *
 * coroutineScope {
 *      ReactiveLoader.collectLoader(
 *          classes = listOf(ReactiveClass),
 *          injectionClasses = listOf()
 *      )
 * }
 *
 * // The property name must be specified as in the reactive class.
 * // To call a trigger, you need to specify the name of the event that is specified in the react trigger.
 * ReactiveLoader.setReactiveValue(
 *      nameProperty = "reactiveProperty",
 *      event = "updateData",
 *      data = DataClass(1, "New title 1")
 * ) // Update data
 * ```
 */
interface ReactiveLazyObject
