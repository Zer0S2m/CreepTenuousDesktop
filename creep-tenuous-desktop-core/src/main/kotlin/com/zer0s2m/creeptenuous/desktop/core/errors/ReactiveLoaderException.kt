package com.zer0s2m.creeptenuous.desktop.core.errors

import com.zer0s2m.creeptenuous.desktop.core.reactive.Loader

/**
 * Error occurring when assembling the [Loader]
 *
 * @param message Error message
 */
class ReactiveLoaderException (message: String) : Exception(message)
