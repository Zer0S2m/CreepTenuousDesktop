package core.context

import enums.Screen

/**
 * The base illusion context class for passing the new screen state to the next
 *
 * @param screen The new screen state obtained from the previous screen (state)
 */
class ContextScreen(
    override val screen: Screen? = null
) : BaseContextScreen
