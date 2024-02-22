package com.zer0s2m.creeptenuous.desktop.reactive.actions

import com.zer0s2m.creeptenuous.desktop.common.dto.BreadCrumbFileObject
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.core.actions.Action
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * An action to traverse a directory tree to change the directory's previous state.
 *
 * The following parameters must be set for the screen context:
 *
 * - currentLevelManagerDirectory
 * - currentParentsManagerDirectory
 * - currentSystemParentsManagerDirectory
 */
object ActionsSwitchLastThroughDirectories : Action {

    /**
     * Call action.
     *
     * @param scope Defines a scope for new coroutines.
     * @param values Additional data.
     */
    override fun call(scope: CoroutineScope, vararg values: Any?) {
        scope.launch {
            val currentLevelManagerDirectory: Int = ContextScreen.get(
                Screen.DASHBOARD_SCREEN, "currentLevelManagerDirectory"
            )

            if (currentLevelManagerDirectory != 0 && currentLevelManagerDirectory > 0) {
                val currentParentsManagerDirectory: MutableList<String> = ContextScreen.get(
                    Screen.DASHBOARD_SCREEN, "currentParentsManagerDirectory"
                )
                val currentSystemParentsManagerDirectory: MutableList<String> = ContextScreen.get(
                    Screen.DASHBOARD_SCREEN, "currentSystemParentsManagerDirectory"
                )

                currentParentsManagerDirectory.removeLast()
                currentSystemParentsManagerDirectory.removeLast()

                ContextScreen.set(
                    Screen.DASHBOARD_SCREEN,
                    mapOf(
                        "currentLevelManagerDirectory" to currentLevelManagerDirectory - 1,
                        "currentParentsManagerDirectory" to currentParentsManagerDirectory,
                        "currentSystemParentsManagerDirectory" to currentSystemParentsManagerDirectory
                    )
                )

                val itemsBreadCrumbs: MutableList<BreadCrumbFileObject> = mutableListOf()
                currentParentsManagerDirectory.zip(currentSystemParentsManagerDirectory) { parent, systemParent ->
                    itemsBreadCrumbs.add(
                        BreadCrumbFileObject(
                            realName = parent,
                            systemName = systemParent
                        )
                    )
                }

                if (currentLevelManagerDirectory - 1 == 0) {
                    ReactiveLoader.runReactiveIndependentInjection(
                        method = "setTitleSwitchPanelDashboard",
                        value = "Main"
                    )
                } else {
                    ReactiveLoader.runReactiveIndependentInjection(
                        method = "setTitleSwitchPanelDashboard",
                        value = currentParentsManagerDirectory.last()
                    )
                }

                ReactiveLoader.runReactiveIndependentInjection(
                    method = "setItemsBreadCrumbs",
                    value = itemsBreadCrumbs
                )

                ReactiveLoader.resetIsLoad("managerFileSystemObjects")
                ReactiveLoader.load("managerFileSystemObjects")
            }
        }
    }

}