package com.zer0s2m.creeptenuous.desktop.reactive.actions

import com.zer0s2m.creeptenuous.desktop.common.dto.BreadCrumbFileObject
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.core.actions.Action
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Steps to traverse a directory tree using breadcrumbs.
 *
 * The following parameters must be set for the state context:
 *
 * - currentParentsManagerDirectory
 * - currentSystemParentsManagerDirectory
 */
object ActionSwitchBreadCrumbsThroughDirectories : Action {

    /**
     * Call action.
     *
     * @param scope Defines a scope for new coroutines.
     * @param values Additional data.
     */
    override fun call(scope: CoroutineScope, vararg values: Any?) {
        val index: Int = (if (values[0] is Int) values[0].toString().toInt() else null) ?: return

        scope.launch {
            var currentParentsManagerDirectory: MutableList<String> = ContextScreen.get(
                Screen.DASHBOARD_SCREEN, "currentParentsManagerDirectory"
            )
            var currentSystemParentsManagerDirectory: MutableList<String> = ContextScreen.get(
                Screen.DASHBOARD_SCREEN, "currentSystemParentsManagerDirectory"
            )
            currentParentsManagerDirectory = currentParentsManagerDirectory
                .slice(0..<index + 1)
                .toMutableList()
            currentSystemParentsManagerDirectory = currentSystemParentsManagerDirectory
                .slice(0..<index + 1)
                .toMutableList()

            ContextScreen.set(
                Screen.DASHBOARD_SCREEN,
                mapOf(
                    "currentLevelManagerDirectory" to index + 1,
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