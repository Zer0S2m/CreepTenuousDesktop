package com.zer0s2m.creeptenuous.desktop.reactive.pipelines

import com.zer0s2m.creeptenuous.desktop.common.dto.UserColor
import com.zer0s2m.creeptenuous.desktop.core.pipeline.ReactivePipelineHandler
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveFileObject

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
                it.colorId = null
            }
        }
    }

}
