package com.zer0s2m.creeptenuous.desktop.reactive.triggers.io

import com.zer0s2m.creeptenuous.desktop.common.dto.CommentFileObject
import com.zer0s2m.creeptenuous.desktop.common.dto.FileObject
import com.zer0s2m.creeptenuous.desktop.common.dto.ManagerFileObject
import com.zer0s2m.creeptenuous.desktop.core.logging.infoDev
import com.zer0s2m.creeptenuous.desktop.core.logging.logger
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveIndependentTrigger
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveTrigger
import org.slf4j.Logger

/**
 * Trigger fires when a file object is deleted.
 */
class ReactiveTriggerReactiveFileObjectDeleteFileObject : BaseReactiveTrigger<ManagerFileObject> {

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

/**
 * The trigger is called when a file object is set or unset to a custom category.
 */
class ReactiveTriggerReactiveFileObjectSetCategoryInFileObject : BaseReactiveIndependentTrigger {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution.
     *
     * @param values Arbitrary number of arguments passed regardless of type
     */
    override fun execution(vararg values: Any?) {
        val systemNameFileObject: String? = if (values[0] is String) values[0].toString() else null
        val categoryId: Int? = if (values[1] is Int) values[1].toString().toInt() else null
        logger.infoDev("Set category in file object\nDATA: [$systemNameFileObject, $categoryId]")
    }

}

/**
 * The trigger is called when a file object is set to a custom color.
 */
class ReactiveTriggerReactiveFileObjectSetColorInFileObject : BaseReactiveIndependentTrigger {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution.
     *
     * @param values Arbitrary number of arguments passed regardless of type
     */
    override fun execution(vararg values: Any?) {
        val systemNameFileObject: String? = if (values[0] is String) values[0].toString() else null
        val colorId: Int? = if (values[1] is Int) values[1].toString().toInt() else null
        logger.infoDev("Set color in file object - directory\nDATA: [$systemNameFileObject, $colorId]")
    }

}

/**
 * The trigger is called when a file object of type directory is created.
 */
class ReactiveTriggerReactiveFileObjectCreateDirectory : BaseReactiveIndependentTrigger {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution.
     *
     * @param values Arbitrary number of arguments passed regardless of type
     */
    override fun execution(vararg values: Any?) {
        val fileObject: FileObject? = if (values[0] is FileObject) values[0] as FileObject else null
        val colorId: Int? = if (values[1] is Int) values[1].toString().toInt() else null
        logger.infoDev("Create file object of type - directory\nDATA: [$fileObject, $colorId]")
    }

}

/**
 * The trigger fires when the name of the file object changes.
 */
class ReactiveTriggerReactiveFileObjectRenameFileObject : BaseReactiveIndependentTrigger {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution.
     *
     * @param values Arbitrary number of arguments passed regardless of type
     */
    override fun execution(vararg values: Any?) {
        val systemName: String? = if (values[0] is String) values[0].toString() else null
        val newTitle: String? = if (values[1] is String) values[1].toString() else null
        logger.infoDev("Rename file object\nDATA: [$systemName, $newTitle]")
    }

}

/**
 * A reactive trigger fires when a comment for a file object is deleted.
 */
class ReactiveTriggerReactiveFileObjectRemoveCommentFileObject : BaseReactiveTrigger<CommentFileObject> {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution.
     *
     * @param value The new value of a property or object.
     */
    override fun execution(value: CommentFileObject) {
        logger.infoDev("Delete a comment for file object\nDATA: $value")
    }

}