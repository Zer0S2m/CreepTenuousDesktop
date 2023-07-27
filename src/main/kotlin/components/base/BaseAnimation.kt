package components.base

/**
 * Basic interface for implementing animation setup to a component
 */
interface BaseAnimation {

    /**
     * Set background change animation for a component
     */
    val isAnimation: Boolean
        get() = true

}
