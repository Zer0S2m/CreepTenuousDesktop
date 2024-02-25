package com.zer0s2m.creeptenuous.desktop.reactive.actions

import com.zer0s2m.creeptenuous.desktop.common.dto.BreadCrumbFileObject
import com.zer0s2m.creeptenuous.desktop.common.dto.FileObject
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.core.actions.Action
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * An action to traverse a tree by directory.
 *
 * The following parameters must be set for the screen context [ContextScreen]:
 *
 * - currentLevelManagerDirectory
 * - currentParentsManagerDirectory
 * - currentSystemParentsManagerDirectory
 */
object ActionWalkingThroughDirectories : Action {

    /**
     * Call action.
     *
     * @param scope Defines a scope for new coroutines.
     * @param values Additional data.
     */
    override fun call(scope: CoroutineScope, vararg values: Any?) {
        val directory: FileObject = (if (values[0] is FileObject) values[0] as FileObject else null) ?: return

        val currentLevelManagerDirectory: Int = ContextScreen.get(
            Screen.DASHBOARD_SCREEN, "currentLevelManagerDirectory"
        )
        val currentParentsManagerDirectory: MutableList<String> = ContextScreen.get(
            Screen.DASHBOARD_SCREEN, "currentParentsManagerDirectory"
        )
        val currentSystemParentsManagerDirectory: MutableList<String> = ContextScreen.get(
            Screen.DASHBOARD_SCREEN, "currentSystemParentsManagerDirectory"
        )
        val itemsBreadCrumbs: MutableList<BreadCrumbFileObject> = mutableListOf()

        scope.launch {
            currentParentsManagerDirectory.add(directory.realName)
            currentSystemParentsManagerDirectory.add(directory.systemName)

            currentParentsManagerDirectory.zip(currentSystemParentsManagerDirectory) { parent, systemParent ->
                itemsBreadCrumbs.add(
                    BreadCrumbFileObject(
                        realName = parent,
                        systemName = systemParent
                    )
                )
            }

            ContextScreen.set(
                Screen.DASHBOARD_SCREEN,
                mapOf(
                    "currentLevelManagerDirectory" to currentLevelManagerDirectory + 1,
                    "currentParentsManagerDirectory" to currentParentsManagerDirectory,
                    "currentSystemParentsManagerDirectory" to currentSystemParentsManagerDirectory
                )
            )

            ReactiveLoader.runReactiveIndependentInjection(
                method = "setItemsBreadCrumbs",
                value = itemsBreadCrumbs
            )
            ReactiveLoader.runReactiveIndependentInjection(
                method = "setTitleSwitchPanelDashboard",
                value = currentParentsManagerDirectory.last()
            )
            ReactiveLoader.resetIsLoad("managerFileSystemObjects")
            ReactiveLoader.load("managerFileSystemObjects")
        }
    }

}
