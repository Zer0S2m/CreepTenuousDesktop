package com.zer0s2m.creeptenuous.desktop.core.reactive

/**
 * An interface for defining a `lazy` and `reactive` objects.
 *
 * Objects that are not lazy will be loaded instantly when the application starts,
 * otherwise `lazy` objects will wait for their turn to be loaded
 * (for example, when changing state from one screen to another)
 */
interface ReactiveLazyObject
