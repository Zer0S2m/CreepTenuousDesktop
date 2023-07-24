package components.base

import androidx.compose.material.Card

/**
 * Base class for implementing work with file objects.
 * Main storage support types: directories and files.
 * Extending components [Card]
 */
interface BaseCard : BaseComponent {

    /**
     * File object type designation - directory
     */
    val isDirectory: Boolean

    /**
     * File object type designation - file
     */
    val isFile: Boolean

    /**
     * Display text when rendering
     */
    val text: String
        get() = ""

}
