package com.zer0s2m.creeptenuous.desktop.reactive.pipelines

import com.zer0s2m.creeptenuous.desktop.common.dto.UserCategory
import com.zer0s2m.creeptenuous.desktop.core.pipeline.ReactivePipelineHandler
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveFileObject

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
