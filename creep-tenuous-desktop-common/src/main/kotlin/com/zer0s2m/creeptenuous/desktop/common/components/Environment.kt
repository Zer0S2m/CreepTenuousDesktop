package com.zer0s2m.creeptenuous.desktop.common.components

/**
 * Primary storage for running the application with environment variables enabled.
 */
object Environment {

    /**
     * Development mode.
     */
    val MODE: String = System.getenv("CTD_MODE")

    /**
     * Is development mode enabled.
     */
    val IS_DEV: Boolean = MODE == "dev"

}
