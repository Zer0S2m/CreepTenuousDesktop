package com.zer0s2m.creeptenuous.desktop.reactive.models

import com.zer0s2m.creeptenuous.desktop.common.dto.ManagerFileObject
import com.zer0s2m.creeptenuous.desktop.core.reactive.Lazy
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLazyObject
import com.zer0s2m.creeptenuous.desktop.reactive.handlers.HandlerReactiveFileObjectManagerFileSystemObjects

/**
 * Reactive file object data model
 */
object ReactiveFileObject : ReactiveLazyObject {

    /**
     * Information about the directory at a certain segment of the nesting level
     */
    @Lazy<ManagerFileObject>(
        handler = HandlerReactiveFileObjectManagerFileSystemObjects::class
    )
    var managerFileSystemObjects: ManagerFileObject? = null

}
