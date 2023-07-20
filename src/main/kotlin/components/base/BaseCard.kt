package components.base

/**
 * The base interface for implementing work with file objects.
 * Support types: catalogs and files
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
