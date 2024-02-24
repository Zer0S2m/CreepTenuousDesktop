package com.zer0s2m.creeptenuous.desktop.reactive.pipelines

import com.zer0s2m.creeptenuous.desktop.common.dto.UserColor
import com.zer0s2m.creeptenuous.desktop.core.pipeline.ReactivePipelineHandler
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveUser

/**
 * The reactive pipeline is called when the user's color palette changes,
 * and categories apply the new color palette.
 */
class ReactivePipelineHandlerUpdateUserColorSetNewColorInUserCategory : ReactivePipelineHandler<UserColor> {

    /**
     * Jet pipeline launch.
     *
     * @param value Data.
     */
    override fun launch(value: UserColor) {
        ReactiveUser.customCategories.forEach {
            if ((it.colorId == value.id) && it.colorId != null) {
                it.color = value.color
            }
        }
    }

}
