package com.zer0s2m.creeptenuous.desktop.core.reactive

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
     * Adds the specified element to the collection in a reactive way and
     * invokes the specified [triggerAdd].
     *
     * @return `true` if the element has been added, `false` if the collection does not support duplicates
     * and the element is already contained in the collection.
     */
    fun addReactive(element: E): Boolean {
        val isAdded = add(element)
        if (isAdded) {
            triggerAdd?.execution(element)
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
            triggerRemove?.execution(element)
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
        triggerRemove?.execution(element)
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
        triggerSet?.execution(element)
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
 */
class ReactiveArrayList<E>(
    override val triggerAdd: BaseReactiveTrigger<E>? = null,
    override val triggerRemove: BaseReactiveTrigger<E>? = null,
    override val triggerSet: BaseReactiveTrigger<E>? = null
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
 */
fun <T> mutableReactiveListOf(
    triggerAdd: BaseReactiveTrigger<T>? = null,
    triggerRemove: BaseReactiveTrigger<T>? = null,
    triggerSet: BaseReactiveTrigger<T>? = null
): ReactiveMutableList<T> = ReactiveArrayList(
    triggerAdd = triggerAdd,
    triggerRemove = triggerRemove,
    triggerSet = triggerSet
)

/**
 * Returns a new [ReactiveMutableList] filled with all elements of this collection.
 *
 * @param triggerAdd The trigger is fired when a new element is added to the collection.
 * @param triggerRemove The trigger is fired when an item is removed from the collection.
 * @param triggerSet The trigger is called when the element at the specified position
 * in this list is replaced by the specified element.
 */
fun <T> Collection<T>.toReactiveMutableList(
    triggerAdd: BaseReactiveTrigger<T>? = null,
    triggerRemove: BaseReactiveTrigger<T>? = null,
    triggerSet: BaseReactiveTrigger<T>? = null
): ReactiveMutableList<T> {
    val newList: ReactiveMutableList<T> = ReactiveArrayList(
        triggerAdd = triggerAdd,
        triggerRemove = triggerRemove,
        triggerSet = triggerSet
    )
    newList.addAll(this)
    return newList
}
