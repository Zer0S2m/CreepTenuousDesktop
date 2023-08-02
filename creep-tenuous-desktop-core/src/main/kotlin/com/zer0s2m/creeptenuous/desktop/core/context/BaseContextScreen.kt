package com.zer0s2m.creeptenuous.desktop.core.context

import com.zer0s2m.creeptenuous.desktop.common.enums.Screen

/**
 * Basic interface to create the illusion of a context to pass the new screen state to the next
 */
interface BaseContextScreen {

    /**
     * The new screen state obtained from the previous screen (`state`)
     */
    val screen: Screen?

}
