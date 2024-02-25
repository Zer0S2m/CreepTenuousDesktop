package com.zer0s2m.creeptenuous.desktop.ui.components

import androidx.compose.runtime.Composable

/**
 * Basic interface for implementing extended components
 */
interface BaseComponent {

    /**
     * Component rendering
     *
     * @param content Content of your screen
     */
    @Composable
    fun render(content: @Composable () -> Unit = {}) {
        content()
    }

    /**
     * Component rendering
     */
    @Composable
    fun render() {}

}