package com.zer0s2m.creeptenuous.desktop.reactive.actions

import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.core.actions.Action
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Action to traverse the directory tree to the home page.
 */
object ActionSwitchMainThroughDirectories : Action {

    /**
     * Call action.
     *
     * @param scope Defines a scope for new coroutines.
     * @param values Additional data.
     */
    override fun call(scope: CoroutineScope, vararg values: Any?) {
        scope.launch {
            ContextScreen.set(
                Screen.DASHBOARD_SCREEN,
                mapOf(
                    "currentLevelManagerDirectory" to 0,
                    "currentParentsManagerDirectory" to mutableListOf<String>(),
                    "currentSystemParentsManagerDirectory" to mutableListOf<String>()
                )
            )

            ReactiveLoader.runReactiveIndependentInjection(
                method = "setItemsBreadCrumbs",
                value = mutableListOf<String>()
            )
            ReactiveLoader.runReactiveIndependentInjection(
                method = "setTitleSwitchPanelDashboard",
                value = "Main"
            )

            ReactiveLoader.resetIsLoad("managerFileSystemObjects")
            ReactiveLoader.load("managerFileSystemObjects")
        }
    }

}