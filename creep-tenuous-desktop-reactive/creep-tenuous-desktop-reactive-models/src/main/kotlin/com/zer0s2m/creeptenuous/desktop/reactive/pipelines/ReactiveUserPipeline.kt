package com.zer0s2m.creeptenuous.desktop.reactive.pipelines

import com.zer0s2m.creeptenuous.desktop.common.dto.UserCategory
import com.zer0s2m.creeptenuous.desktop.common.dto.UserColor
import com.zer0s2m.creeptenuous.desktop.core.pipeline.ReactivePipelineHandler
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveFileObject
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveUser

/**
 * The Reactive Pipeline is called when a custom color palette is removed
 * and cleans up the file objects from it
 */
class ReactivePipelineHandlerDeleteUserColorCleanInFileObject : ReactivePipelineHandler<UserColor> {

    /**
     * Jet pipeline launch.
     *
     * @param value Data.
     */
    override fun launch(value: UserColor) {
        ReactiveFileObject.managerFileSystemObjects.objects.forEach {
            if (it.color == value.color) {
                it.color = null
            }
        }
    }

}

/**
 * The reactive pipeline is called when a custom color palette is deleted
 * and removes the custom categories from it.
 */
class ReactivePipelineHandlerDeleteUserColorCleanInUserCategory : ReactivePipelineHandler<UserColor> {

    /**
     * Jet pipeline launch.
     *
     * @param value Data.
     */
    override fun launch(value: UserColor) {
        ReactiveUser.customCategories.forEach {
            if (it.color == value.color) {
                it.color = null
            }
        }
    }

}

/**
 * The reactive pipeline is called when a user's category is deleted, and file
 * objects are cleaned up after the category is deleted.
 */
class ReactivePipelineHandlerDeleteUserCategoryCleanInFileObject : ReactivePipelineHandler<UserCategory> {

    /**
     * Jet pipeline launch.
     *
     * @param value Data.
     */
    override fun launch(value: UserCategory) {
        ReactiveFileObject.managerFileSystemObjects.objects.forEach {
            if (it.categoryId == value.id) {
                it.categoryId = null
            }
        }
    }

}
