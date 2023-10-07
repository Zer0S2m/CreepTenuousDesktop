package com.zer0s2m.creeptenuous.desktop.core.reactive

import com.zer0s2m.creeptenuous.desktop.core.pipeline.ReactivePipelineType
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveTrigger

/**
 * A generic collection of items that supports adding and removing items in
 * a reactive manner with various triggers [BaseReactiveTrigger].
 *
 * @param E the type of elements contained in the collection.
 *          The mutable collection is invariant in its element type.
 */
interface ReactiveMutableList<E> : MutableList<E> {

    /**
     * The trigger is fired when a new element is added to the collection.
     */
    val triggerAdd: BaseReactiveTrigger<E>?

    /**
     * The trigger is fired when an item is removed from the collection.
     */
    val triggerRemove: BaseReactiveTrigger<E>?

    /**
     * The trigger is called when the element at the specified position in
     * this list is replaced by the specified element.
     */
    val triggerSet: BaseReactiveTrigger<E>?

    /**
     * Name of reactive pipelines that will be executed before or after adding an element.
     */
    val pipelinesAdd: Collection<String>
        get() = listOf()

    /**
     * Name of reactive pipelines that will be executed before or after element removal.
     */
    val pipelinesRemove: Collection<String>
        get() = listOf()

    /**
     * The name of the reactive pipelines that will be executed before or after installation of the element.
     */
    val pipelinesSet: Collection<String>
        get() = listOf()

    /**
     * Adds the specified element to the collection in a reactive way and
     * invokes the specified [triggerAdd].
     *
     * @return `true` if the element has been added, `false` if the collection does not support duplicates
     * and the element is already contained in the collection.
     */
    fun addReactive(element: E): Boolean {
        val isAdded = add(element)
        if (isAdded) {
            ReactiveLoader.pipelineLaunch(pipelinesAdd, element as Any, ReactivePipelineType.BEFORE)
            triggerAdd?.execution(element)
            ReactiveLoader.pipelineLaunch(pipelinesAdd, element as Any, ReactivePipelineType.AFTER)
        }
        return isAdded
    }

    /**
     * Removes one instance of the specified element from this collection in a
     * reactive manner, calling the [triggerRemove] trigger, if present.
     *
     * @return `true` if the element has been successfully removed; `false` if it was not present in the collection.
     */
    fun removeReactive(element: E): Boolean {
        val isRemoved = remove(element)

        if (isRemoved) {
            ReactiveLoader.pipelineLaunch(pipelinesRemove, element as Any, ReactivePipelineType.BEFORE)
            triggerRemove?.execution(element)
            ReactiveLoader.pipelineLaunch(pipelinesRemove, element as Any, ReactivePipelineType.AFTER)
        }

        return isRemoved
    }

    /**
     * Removes the element at the specified [index] from the list in a reactive
     * manner while calling a [triggerRemove].
     *
     * @return the element that has been removed.
     */
    fun removeAtReactive(index: Int): E {
        val element = removeAt(index)

        ReactiveLoader.pipelineLaunch(pipelinesRemove, element as Any, ReactivePipelineType.BEFORE)
        triggerRemove?.execution(element)
        ReactiveLoader.pipelineLaunch(pipelinesRemove, element as Any, ReactivePipelineType.AFTER)

        return element
    }

    /**
     * Replaces the element at the specified position in this list with the
     * specified [element] in a reactive manner by calling a [triggerSet].
     *
     * @return the element previously at the specified position.
     */
    fun setReactive(index: Int, element: E): E {
        val newElement = set(index, element)
        ReactiveLoader.pipelineLaunch(pipelinesSet, element as Any, ReactivePipelineType.BEFORE)
        triggerSet?.execution(element)
        ReactiveLoader.pipelineLaunch(pipelinesSet, element as Any, ReactivePipelineType.AFTER)
        return newElement
    }

}

/**
 * Reactive collection based on triggers
 *
 * @param triggerAdd The trigger is fired when a new element is added to the collection.
 * @param triggerRemove The trigger is fired when an item is removed from the collection.
 * @param triggerSet The trigger is called when the element at the specified position
 * in this list is replaced by the specified element.
 * @param pipelinesAdd Name of reactive pipelines that will be executed before or after adding an element.
 * @param pipelinesRemove Name of reactive pipelines that will be executed before or after element removal.
 * @param pipelinesSet The name of the reactive pipelines that will be executed before or after installation
 * of the element.
 */
class ReactiveArrayList<E>(
    override val triggerAdd: BaseReactiveTrigger<E>? = null,
    override val triggerRemove: BaseReactiveTrigger<E>? = null,
    override val triggerSet: BaseReactiveTrigger<E>? = null,
    override val pipelinesAdd: Collection<String> = listOf(),
    override val pipelinesRemove: Collection<String> = listOf(),
    override val pipelinesSet: Collection<String> = listOf()
) :
    ReactiveMutableList<E>,
    RandomAccess,
    MutableList<E> by mutableListOf()

/**
 * Returns an empty new [ReactiveMutableList].
 *
 * @param triggerAdd The trigger is fired when a new element is added to the collection.
 * @param triggerRemove The trigger is fired when an item is removed from the collection.
 * @param triggerSet The trigger is called when the element at the specified position
 * in this list is replaced by the specified element.
 * @param pipelinesAdd Name of reactive pipelines that will be executed before or after adding an element.
 * @param pipelinesRemove Name of reactive pipelines that will be executed before or after element removal.
 * @param pipelinesSet The name of the reactive pipelines that will be executed before or after installation
 * of the element.
 */
fun <T> mutableReactiveListOf(
    triggerAdd: BaseReactiveTrigger<T>? = null,
    triggerRemove: BaseReactiveTrigger<T>? = null,
    triggerSet: BaseReactiveTrigger<T>? = null,
    pipelinesAdd: Collection<String> = listOf(),
    pipelinesRemove: Collection<String> = listOf(),
    pipelinesSet: Collection<String> = listOf()
): ReactiveMutableList<T> = ReactiveArrayList(
    triggerAdd = triggerAdd,
    triggerRemove = triggerRemove,
    triggerSet = triggerSet,
    pipelinesAdd = pipelinesAdd,
    pipelinesRemove = pipelinesRemove,
    pipelinesSet = pipelinesSet
)

/**
 * Returns a new [ReactiveMutableList] filled with all elements of this collection.
 *
 * @param triggerAdd The trigger is fired when a new element is added to the collection.
 * @param triggerRemove The trigger is fired when an item is removed from the collection.
 * @param triggerSet The trigger is called when the element at the specified position
 * in this list is replaced by the specified element.
 * @param pipelinesAdd Name of reactive pipelines that will be executed before or after adding an element.
 * @param pipelinesRemove Name of reactive pipelines that will be executed before or after element removal.
 * @param pipelinesSet The name of the reactive pipelines that will be executed before or after installation
 * of the element.
 */
fun <T> Collection<T>.toReactiveMutableList(
    triggerAdd: BaseReactiveTrigger<T>? = null,
    triggerRemove: BaseReactiveTrigger<T>? = null,
    triggerSet: BaseReactiveTrigger<T>? = null,
    pipelinesAdd: Collection<String> = listOf(),
    pipelinesRemove: Collection<String> = listOf(),
    pipelinesSet: Collection<String> = listOf()
): ReactiveMutableList<T> {
    val newList: ReactiveMutableList<T> = ReactiveArrayList(
        triggerAdd = triggerAdd,
        triggerRemove = triggerRemove,
        triggerSet = triggerSet,
        pipelinesAdd = pipelinesAdd,
        pipelinesRemove = pipelinesRemove,
        pipelinesSet = pipelinesSet
    )
    newList.addAll(this)
    return newList
}
