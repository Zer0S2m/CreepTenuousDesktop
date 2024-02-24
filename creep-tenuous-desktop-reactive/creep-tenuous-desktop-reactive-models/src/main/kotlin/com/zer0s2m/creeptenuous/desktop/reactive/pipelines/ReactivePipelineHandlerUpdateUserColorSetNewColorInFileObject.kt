package com.zer0s2m.creeptenuous.desktop.reactive.pipelines

import com.zer0s2m.creeptenuous.desktop.common.dto.UserColor
import com.zer0s2m.creeptenuous.desktop.core.pipeline.ReactivePipelineHandler
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveFileObject

/**
 * The reactive pipeline is called when the user's color palette is updated
 * and file objects are set to the new color.
 */
class ReactivePipelineHandlerUpdateUserColorSetNewColorInFileObject : ReactivePipelineHandler<UserColor> {

    /**
     * Jet pipeline launch.
     *
     * @param value Data.
     */
    override fun launch(value: UserColor) {
        ReactiveFileObject.managerFileSystemObjects.objects.forEach {
            if ((it.colorId == value.id) && it.colorId != null) {
                it.color = value.color
            }
        }
    }

}
