package com.zer0s2m.creeptenuous.desktop.core.context

import com.zer0s2m.creeptenuous.desktop.common.enums.Screen

/**
 * The base illusion context class for passing the new screen state to the next
 *
 * @param screen The new screen state obtained from the previous screen (state)
 */
class ContextScreen(
    override val screen: Screen? = null
) : BaseContextScreen
