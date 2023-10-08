package com.zer0s2m.creeptenuous.desktop.reactive.models

import com.zer0s2m.creeptenuous.desktop.common.dto.CommentFileObject
import com.zer0s2m.creeptenuous.desktop.common.dto.ManagerFileObject
import com.zer0s2m.creeptenuous.desktop.core.injection.ReactiveInjection
import com.zer0s2m.creeptenuous.desktop.core.reactive.*
import com.zer0s2m.creeptenuous.desktop.reactive.handlers.HandlerReactiveCommentsFileObject
import com.zer0s2m.creeptenuous.desktop.reactive.handlers.HandlerReactiveFileObjectManagerFileSystemObjects
import com.zer0s2m.creeptenuous.desktop.reactive.triggers.io.*

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
        ],
        independentTriggers = [
            ReactiveIndependentTrigger(
                event = "setColorInFileObject",
                trigger = ReactiveTriggerReactiveFileObjectSetColorInFileObject::class
            ),
            ReactiveIndependentTrigger(
                event = "setCategoryInFileObject",
                trigger = ReactiveTriggerReactiveFileObjectSetCategoryInFileObject::class
            ),
            ReactiveIndependentTrigger(
                event = "createFileObjectOfTypeDirectory",
                trigger = ReactiveTriggerReactiveFileObjectCreateDirectory::class
            ),
            ReactiveIndependentTrigger(
                event = "renameFileObject",
                trigger = ReactiveTriggerReactiveFileObjectRenameFileObject::class
            )
        ]
    )
    var managerFileSystemObjects: ManagerFileObject = ManagerFileObject(
        systemParents = listOf(),
        level = 0,
        objects = mutableListOf()
    )

    /**
     * File object comments.
     */
    @Lazy<ReactiveMutableList<CommentFileObject>>(
        event = "Fires when it opens information about comments on a file object",
        handler = HandlerReactiveCommentsFileObject::class,
        injection = ReactiveInjection(
            method = "setCommentsInFileObject"
        )
    )
    var commentsFileSystemObject: ReactiveMutableList<CommentFileObject> = mutableReactiveListOf()

}
