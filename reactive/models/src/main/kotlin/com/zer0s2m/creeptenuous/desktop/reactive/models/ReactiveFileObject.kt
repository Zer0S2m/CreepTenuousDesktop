package com.zer0s2m.creeptenuous.desktop.reactive.models

import com.zer0s2m.creeptenuous.desktop.common.dto.CommentFileObject
import com.zer0s2m.creeptenuous.desktop.common.dto.InfoFileObject
import com.zer0s2m.creeptenuous.desktop.common.dto.ManagerFileObject
import com.zer0s2m.creeptenuous.desktop.core.injection.ReactiveInjection
import com.zer0s2m.creeptenuous.desktop.core.reactive.Lazy
import com.zer0s2m.creeptenuous.desktop.core.reactive.Reactive
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveIndependentTrigger
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLazyObject
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveMutableList
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveSendIsLoad
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveTrigger
import com.zer0s2m.creeptenuous.desktop.core.reactive.mutableReactiveListOf
import com.zer0s2m.creeptenuous.desktop.reactive.handlers.HandlerReactiveCommentsFileObject
import com.zer0s2m.creeptenuous.desktop.reactive.handlers.HandlerReactiveFileObjectManagerFileSystemObjects
import com.zer0s2m.creeptenuous.desktop.reactive.handlers.HandlerReactiveInfoFileObject
import com.zer0s2m.creeptenuous.desktop.reactive.triggers.ReactiveTriggerReactiveFileObjectCreateCommentFileObject
import com.zer0s2m.creeptenuous.desktop.reactive.triggers.ReactiveTriggerReactiveFileObjectCreateDirectory
import com.zer0s2m.creeptenuous.desktop.reactive.triggers.ReactiveTriggerReactiveFileObjectDeleteFileObject
import com.zer0s2m.creeptenuous.desktop.reactive.triggers.ReactiveTriggerReactiveFileObjectEditCommentFileObject
import com.zer0s2m.creeptenuous.desktop.reactive.triggers.ReactiveTriggerReactiveFileObjectRemoveCommentFileObject
import com.zer0s2m.creeptenuous.desktop.reactive.triggers.ReactiveTriggerReactiveFileObjectRenameFileObject
import com.zer0s2m.creeptenuous.desktop.reactive.triggers.ReactiveTriggerReactiveFileObjectSetCategoryInFileObject
import com.zer0s2m.creeptenuous.desktop.reactive.triggers.ReactiveTriggerReactiveFileObjectSetColorInFileObject

/**
 * Reactive file object data model
 */
object ReactiveFileObject : ReactiveLazyObject {

    /**
     * Information about the directory at a certain segment of the nesting level
     */
    @Reactive<ManagerFileObject>(
        handler = HandlerReactiveFileObjectManagerFileSystemObjects::class,
        priority = 10,
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
        ],
        sendIsLoad = ReactiveSendIsLoad(
            isSend = true,
            injection = ReactiveInjection(
                method = "setManagerFileObjectIsLoad"
            )
        )
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
    var commentsFileSystemObject: ReactiveMutableList<CommentFileObject> = mutableReactiveListOf(
        triggerAdd = ReactiveTriggerReactiveFileObjectCreateCommentFileObject(),
        triggerRemove = ReactiveTriggerReactiveFileObjectRemoveCommentFileObject(),
        triggerSet = ReactiveTriggerReactiveFileObjectEditCommentFileObject()
    )

    /**
     * File object information.
     */
    @Lazy<InfoFileObject?>(
        event = "Fires when a request for information occurs",
        handler = HandlerReactiveInfoFileObject::class
    )
     var infoFileObject: InfoFileObject? = null

}
