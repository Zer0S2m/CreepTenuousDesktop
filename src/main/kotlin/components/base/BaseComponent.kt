package components.base

import androidx.compose.runtime.Composable

/**
 * Basic interface for implementing extended components
 */
interface BaseComponent {

    /**
     * Component rendering
     */
    @Composable
    fun render()

}
