package com.zer0s2m.creeptenuous.desktop.core.logging

import com.zer0s2m.creeptenuous.desktop.core.env.Environment
import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified R : Any> R.logger(): Logger =
    LoggerFactory.getLogger(this::class.java.name.substringBefore("\$Companion"))

/**
 * Log a message at the INFO level if development mode is enabled.
 *
 * @param message The message string to be logged.
 */
fun Logger.infoDev(message: String) {
    if (Environment.IS_DEV) {
        this.info(message)
    }
}
