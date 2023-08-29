package com.zer0s2m.creeptenuous.desktop.core.reactive

import com.zer0s2m.creeptenuous.desktop.core.triggers.ReactiveTrigger

/**
 * A generic collection of items that supports adding and removing items in
 * a reactive manner with various triggers [ReactiveTrigger].
 *
 * @param E the type of elements contained in the collection.
 *          The mutable collection is invariant in its element type.
 */
interface ReactiveMutableList<E> : MutableList<E> {

    /**
     * The trigger is fired when a new element is added to the collection.
     */
    val triggerAdd: ReactiveTrigger<E>?

    /**
     * The trigger is fired when an item is removed from the collection.
     */
    val triggerRemove: ReactiveTrigger<E>?

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
        return element;
    }

}

/**
 * Reactive collection based on triggers
 *
 * @param triggerAdd The trigger is fired when a new element is added to the collection.
 * @param triggerRemove The trigger is fired when an item is removed from the collection.
 */
class ReactiveArrayList<E>(
    override val triggerAdd: ReactiveTrigger<E>? = null,
    override val triggerRemove: ReactiveTrigger<E>? = null
) :
    ReactiveMutableList<E>,
    RandomAccess,
    MutableList<E> by mutableListOf()

/**
 * Returns an empty new [ReactiveMutableList].
 *
 * @param triggerAdd The trigger is fired when a new element is added to the collection.
 * @param triggerRemove The trigger is fired when an item is removed from the collection.
 */
fun <T> mutableReactiveListOf(
    triggerAdd: ReactiveTrigger<T>? = null,
    triggerRemove: ReactiveTrigger<T>? = null
): ReactiveMutableList<T> = ReactiveArrayList(triggerAdd, triggerRemove)

/**
 * Returns a new [ReactiveMutableList] filled with all elements of this collection.
 *
 * @param triggerAdd The trigger is fired when a new element is added to the collection.
 * @param triggerRemove The trigger is fired when an item is removed from the collection.
 */
fun <T> Collection<T>.toReactiveMutableList(
    triggerAdd: ReactiveTrigger<T>? = null,
    triggerRemove: ReactiveTrigger<T>? = null
): ReactiveMutableList<T> {
    val newList: ReactiveMutableList<T> = ReactiveArrayList(triggerAdd, triggerRemove)
    newList.addAll(this)
    return newList
}
