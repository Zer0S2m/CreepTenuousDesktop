package core.errors

import core.reactive.Loader

/**
 * Error occurring when assembling the [Loader]
 *
 * @param message Error message
 */
class ReactiveLoaderException (message: String) : Exception(message)
