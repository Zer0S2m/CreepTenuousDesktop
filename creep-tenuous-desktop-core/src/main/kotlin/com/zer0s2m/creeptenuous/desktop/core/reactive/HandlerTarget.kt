package com.zer0s2m.creeptenuous.desktop.core.reactive

enum class HandlerTarget(val method: String) {

    /**
     * The name of the method for inverting a reactive property. [ReactiveHandler.handler]
     */
    HANDLER_NAME("handler"),

    /**
     * The name of the method for inverting a reactive property. [ReactiveHandlerAfter.action]
     */
    HANDLER_AFTER_NAME("action")

}