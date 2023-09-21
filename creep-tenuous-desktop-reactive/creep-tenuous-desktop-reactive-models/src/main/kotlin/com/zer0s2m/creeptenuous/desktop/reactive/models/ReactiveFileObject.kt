package com.zer0s2m.creeptenuous.desktop.reactive.models

import com.zer0s2m.creeptenuous.desktop.common.dto.ManagerFileObject
import com.zer0s2m.creeptenuous.desktop.core.injection.ReactiveInjection
import com.zer0s2m.creeptenuous.desktop.core.reactive.Reactive
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLazyObject
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveTrigger
import com.zer0s2m.creeptenuous.desktop.reactive.handlers.HandlerReactiveFileObjectManagerFileSystemObjects
import com.zer0s2m.creeptenuous.desktop.reactive.triggers.ReactiveTriggerReactiveFileObjectDeleteFileObject

/**
 * Reactive file object data model
 */
object ReactiveFileObject : ReactiveLazyObject {

    /**
     * Information about the directory at a certain segment of the nesting level
     */
    @Reactive<ManagerFileObject>(
        handler = HandlerReactiveFileObjectManagerFileSystemObjects::class,
        injection = ReactiveInjection(
            method = "setManagerFileObject"
        ),
        triggers = [
            ReactiveTrigger(
                event = "deleteFileObject",
                trigger = ReactiveTriggerReactiveFileObjectDeleteFileObject::class
            )
        ]
    )
    var managerFileSystemObjects: ManagerFileObject = ManagerFileObject(
        systemParents = listOf(),
        level = 0,
        objects = mutableListOf()
    )

}
