package com.zer0s2m.creeptenuous.desktop.reactive.triggers.io

import com.zer0s2m.creeptenuous.desktop.common.data.DataControlFileSystemObject
import com.zer0s2m.creeptenuous.desktop.common.data.DataControlObject
import com.zer0s2m.creeptenuous.desktop.common.data.DataCreateCommentFileSystemObject
import com.zer0s2m.creeptenuous.desktop.common.data.DataCreateFileSystemObjectDirectory
import com.zer0s2m.creeptenuous.desktop.common.data.DataDeleteFileSystemObjectDirectory
import com.zer0s2m.creeptenuous.desktop.common.data.DataEditCommentFileSystemObject
import com.zer0s2m.creeptenuous.desktop.common.data.DataRenameFileObject
import com.zer0s2m.creeptenuous.desktop.common.data.DataSetCategoryInFileObject
import com.zer0s2m.creeptenuous.desktop.common.data.DataSetColorInFieSystemObject
import com.zer0s2m.creeptenuous.desktop.common.dto.CommentFileObject
import com.zer0s2m.creeptenuous.desktop.common.dto.CreatedDirectory
import com.zer0s2m.creeptenuous.desktop.common.dto.FileObject
import com.zer0s2m.creeptenuous.desktop.common.dto.ManagerFileObject
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import com.zer0s2m.creeptenuous.desktop.core.http.HttpClient
import com.zer0s2m.creeptenuous.desktop.core.logging.infoDev
import com.zer0s2m.creeptenuous.desktop.core.logging.logger
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveIndependentTrigger
import com.zer0s2m.creeptenuous.desktop.core.triggers.BaseReactiveTrigger
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.slf4j.Logger

open class ReactiveTriggerCommon {

    internal suspend fun setColorInDirectory(systemName: String, colorId: Int) {
        HttpClient.client.put {
            url("/api/v1/user/customization/directory/color")
            header("Authorization", "Bearer ${HttpClient.accessToken}")
            contentType(ContentType.Application.Json)
            setBody(DataSetColorInFieSystemObject(systemName, colorId))
        }
    }

    internal suspend fun setCategoryInDirectory(systemName: String, categoryId: Int) {
        HttpClient.client.post {
            url("/api/v1/user/category/file-system-object")
            header("Authorization", "Bearer ${HttpClient.accessToken}")
            contentType(ContentType.Application.Json)
            setBody(DataSetCategoryInFileObject(systemName, categoryId))
        }
    }

}

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
    override suspend fun execution(oldValue: ManagerFileObject, newValue: ManagerFileObject) {
        val deletedFileObject = oldValue.objects.minus(newValue.objects.toSet())
        val currentParentsManagerDirectory: Collection<String> = ContextScreen.get(
            Screen.DASHBOARD_SCREEN, "currentParentsManagerDirectory"
        )
        val currentSystemParentsManagerDirectory: Collection<String> = ContextScreen.get(
            Screen.DASHBOARD_SCREEN, "currentSystemParentsManagerDirectory"
        )

        if (deletedFileObject[0].isFile) {
            logger.infoDev("Delete a file\nDATA: ${deletedFileObject[0]}")
        } else {
            logger.infoDev("Delete a directory\nDATA: ${deletedFileObject[0]}")

            HttpClient.client.delete {
                url("/api/v1/directory/delete")
                header("Authorization", "Bearer ${HttpClient.accessToken}")
                contentType(ContentType.Application.Json)
                setBody(DataDeleteFileSystemObjectDirectory(
                    parents = currentParentsManagerDirectory,
                    systemParents = currentSystemParentsManagerDirectory,
                    directoryName = deletedFileObject[0].realName,
                    systemDirectoryName = deletedFileObject[0].systemName,
                ))
            }
        }
    }

}

/**
 * The trigger is called when a file object is set or unset to a custom category.
 */
class ReactiveTriggerReactiveFileObjectSetCategoryInFileObject
    : ReactiveTriggerCommon(), BaseReactiveIndependentTrigger {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution.
     *
     * @param values Arbitrary number of arguments passed regardless of type
     */
    override suspend fun execution(vararg values: Any?) {
        val systemNameFileObject: String? = if (values[0] is String) values[0].toString() else null
        val categoryId: Int? = if (values[1] is Int) values[1].toString().toInt() else null
        logger.infoDev("Set category in file object\nDATA: [$systemNameFileObject, $categoryId]")

        if (categoryId != null && systemNameFileObject != null) {
            setCategoryInDirectory(systemNameFileObject, categoryId)
        } else if (categoryId == null && systemNameFileObject != null) {
            HttpClient.client.delete {
                url("/api/v1/user/category/file-system-object")
                header("Authorization", "Bearer ${HttpClient.accessToken}")
                contentType(ContentType.Application.Json)
                setBody(DataControlFileSystemObject(systemNameFileObject))
            }
        }
    }

}

/**
 * The trigger is called when a file object is set to a custom color.
 */
class ReactiveTriggerReactiveFileObjectSetColorInFileObject
    : ReactiveTriggerCommon(), BaseReactiveIndependentTrigger {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution.
     *
     * @param values Arbitrary number of arguments passed regardless of type
     */
    override suspend fun execution(vararg values: Any?) {
        val systemNameFileObject: String? = if (values[0] is String) values[0].toString() else null
        val colorId: Int? = if (values[1] is Int) values[1].toString().toInt() else null
        logger.infoDev(
            "Set color in file object - directory\n" +
                    "DATA: [systemNameFileObject:$systemNameFileObject, colorId:$colorId]"
        )

        if (colorId != null && systemNameFileObject != null) {
            setColorInDirectory(systemNameFileObject, colorId)
        } else if (colorId == null && systemNameFileObject != null) {
            HttpClient.client.delete {
                url("/api/v1/user/customization/directory/color")
                header("Authorization", "Bearer ${HttpClient.accessToken}")
                contentType(ContentType.Application.Json)
                setBody(DataControlFileSystemObject(systemNameFileObject))
            }
        }
    }

}

/**
 * The trigger is called when a file object of type directory is created.
 */
class ReactiveTriggerReactiveFileObjectCreateDirectory
    : ReactiveTriggerCommon(), BaseReactiveIndependentTrigger {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution.
     *
     * @param values Arbitrary number of arguments passed regardless of type
     */
    override suspend fun execution(vararg values: Any?) {
        val fileObject: FileObject? = if (values[0] is FileObject) values[0] as FileObject else null
        val colorId: Int? = if (values[1] is Int) values[1].toString().toInt() else null
        val categoryId: Int? = if (values[2] is Int) values[2].toString().toInt() else null
        val currentLevelManagerDirectory: Int = ContextScreen.get(
            Screen.DASHBOARD_SCREEN, "currentLevelManagerDirectory"
        )
        val currentParentsManagerDirectory: Collection<String> = ContextScreen.get(
            Screen.DASHBOARD_SCREEN, "currentParentsManagerDirectory"
        )
        val currentSystemParentsManagerDirectory: Collection<String> = ContextScreen.get(
            Screen.DASHBOARD_SCREEN, "currentSystemParentsManagerDirectory"
        )

        logger.infoDev(
            "Create file object of type - directory\n" +
                    "DATA: [" +
                    "fileObject:$fileObject, " +
                    "colorId:$colorId, " +
                    "categoryId:$categoryId, " +
                    "currentLevelManagerDirectory:$currentLevelManagerDirectory, " +
                    "currentParentsManagerDirectory:$currentParentsManagerDirectory, " +
                    "currentSystemParentsManagerDirectory:$currentSystemParentsManagerDirectory]"
        )

        if (fileObject != null) {
            val createdDirectory: CreatedDirectory = HttpClient.client.post {
                url("/api/v1/directory/create")
                header("Authorization", "Bearer ${HttpClient.accessToken}")
                contentType(ContentType.Application.Json)
                setBody(
                    DataCreateFileSystemObjectDirectory(
                        parents = currentParentsManagerDirectory,
                        systemParents = currentSystemParentsManagerDirectory,
                        directoryName = fileObject.realName,
                    )
                )
            }.body()

            if (colorId != null) {
                setColorInDirectory(createdDirectory.systemDirectoryName, colorId)
            }

            if (categoryId != null && categoryId != -1) {
                setCategoryInDirectory(createdDirectory.systemDirectoryName, categoryId)
            }
        }
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
    override suspend fun execution(vararg values: Any?) {
        val systemName: String? = if (values[0] is String) values[0].toString() else null
        val newTitle: String? = if (values[1] is String) values[1].toString() else null
        logger.infoDev("Rename file object\nDATA: [$systemName, $newTitle]")

        HttpClient.client.put {
            url("/api/v1/file-system-object/rename")
            header("Authorization", "Bearer ${HttpClient.accessToken}")
            contentType(ContentType.Application.Json)
            setBody(DataRenameFileObject(systemName!!, newTitle!!))
        }
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
    override suspend fun execution(value: CommentFileObject) {
        logger.infoDev("Delete a comment for file object\nDATA: $value")

        if (value.id != null) {
            HttpClient.client.delete {
                url("/api/v1/common/comment/file-system-object")
                header("Authorization", "Bearer ${HttpClient.accessToken}")
                contentType(ContentType.Application.Json)
                setBody(DataControlObject(value.id!!))
            }
        }
    }

}

/**
 * A reactive trigger fires when a comment for a file object is deleted.
 */
class ReactiveTriggerReactiveFileObjectEditCommentFileObject : BaseReactiveTrigger<CommentFileObject> {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution.
     *
     * @param value The new value of a property or object.
     */
    override suspend fun execution(value: CommentFileObject) {
        logger.infoDev("Edit a comment for file object\nDATA: $value")

        HttpClient.client.put {
            url("/api/v1/common/comment/file-system-object")
            header("Authorization", "Bearer ${HttpClient.accessToken}")
            contentType(ContentType.Application.Json)
            setBody(value.id?.let { idComment -> DataEditCommentFileSystemObject(idComment, value.comment) })
        }
    }

}

/**
 * A reactive trigger fires when a comment for a file object is created.
 */
class ReactiveTriggerReactiveFileObjectCreateCommentFileObject : BaseReactiveTrigger<CommentFileObject> {

    companion object {
        private val logger: Logger = logger()
    }

    /**
     * Trigger execution.
     *
     * @param value The new value of a property or object.
     */
    override suspend fun execution(value: CommentFileObject) {
        logger.infoDev("Create a comment for file object\nDATA: $value")

        HttpClient.client.post {
            url("/api/v1/common/comment/file-system-object")
            header("Authorization", "Bearer ${HttpClient.accessToken}")
            contentType(ContentType.Application.Json)
            setBody(DataCreateCommentFileSystemObject(value.comment, value.fileSystemObject))
        }
    }

}
