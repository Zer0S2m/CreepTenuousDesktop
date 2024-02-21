package com.zer0s2m.creeptenuous.desktop.core.errors

import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLoader

/**
 * Error occurring when assembling the [ReactiveLoader]
 *
 * @param message Error message
 */
class ReactiveLoaderException (message: String) : ReactiveException(message)
