package com.zer0s2m.creeptenuous.desktop.reactive.triggers

import com.zer0s2m.creeptenuous.desktop.common.dto.ManagerFileObject
import com.zer0s2m.creeptenuous.desktop.core.logging.infoDev
import com.zer0s2m.creeptenuous.desktop.core.logging.logger
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveTrigger
import org.slf4j.Logger

/**
 * Trigger fires when a file object is deleted.
 */
open class ReactiveTriggerReactiveFileObjectDeleteFileObject : BaseReactiveTrigger<ManagerFileObject> {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution. Delete a file object.
     *
     * @param oldValue The old value of a property or object.
     * @param newValue The new value of a property or object.
     */
    override fun execution(oldValue: ManagerFileObject, newValue: ManagerFileObject) {
        val deletedFileObject = oldValue.objects.minus(newValue.objects.toSet())
        if (deletedFileObject[0].isFile) {
            logger.infoDev("Delete a file\nDATA: ${deletedFileObject[0]}")
        } else {
            logger.infoDev("Delete a directory\nDATA: ${deletedFileObject[0]}")
        }
    }

}
