package com.zer0s2m.creeptenuous.desktop.core.reactive.collections

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
        get() = null

    /**
     * The trigger is fired when an item is removed from the collection.
     */
    val riggerRemove: ReactiveTrigger<E>?
        get() = null

    /**
     * Adds the specified element to the collection in a reactive way and
     * invokes the specified [triggerAdd].
     *
     * @return `true` if the element has been added, `false` if the collection does not support duplicates
     * and the element is already contained in the collection.
     */
    fun addReactive(element: E): Boolean
    {
        val isAdded = add(element)
        if (isAdded) {
            triggerAdd?.execution(element)
        }
        return isAdded
    }

    /**
     * Removes one instance of the specified element from this collection in a
     * reactive manner, calling the [riggerRemove] trigger, if present.
     *
     * @return `true` if the element has been successfully removed; `false` if it was not present in the collection.
     */
    fun removeReactive(element: E): Boolean
    {
        val isRemoved = remove(element)
        if (isRemoved) {
            triggerAdd?.execution(element)
        }
        return isRemoved
    }

}

/**
 * Reactive collection based on triggers
 *
 * @param triggerAdd The trigger is fired when a new element is added to the collection.
 * @param riggerRemove The trigger is fired when an item is removed from the collection.
 */
class ReactiveArrayList<E>(
    override val triggerAdd: ReactiveTrigger<E>? = null,
    override val riggerRemove: ReactiveTrigger<E>? = null
) :
    ReactiveMutableList<E>,
    RandomAccess,
    MutableList<E> by mutableListOf()

/**
 * Returns an empty new [ReactiveMutableList].
 *
 * @param triggerAdd The trigger is fired when a new element is added to the collection.
 * @param riggerRemove The trigger is fired when an item is removed from the collection.
 */
fun <T> mutableReactiveListOf(
    triggerAdd: ReactiveTrigger<T>? = null,
    riggerRemove: ReactiveTrigger<T>? = null
): ReactiveMutableList<T> = ReactiveArrayList(triggerAdd, riggerRemove)

/**
 * Returns a new [ReactiveMutableList] filled with all elements of this collection.
 */
fun <T> Collection<T>.toReactiveMutableList(): ReactiveMutableList<T> {
    val newList: ReactiveMutableList<T> = ReactiveArrayList()
    newList.addAll(this)
    return newList
}
