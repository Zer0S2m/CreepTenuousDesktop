package com.zer0s2m.creeptenuous.desktop.reactive.models

import com.zer0s2m.creeptenuous.desktop.reactive.handlers.HandlerReactiveCommonUsers
import com.zer0s2m.creeptenuous.desktop.common.dto.User
import com.zer0s2m.creeptenuous.desktop.core.reactive.Reactive
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLazyObject

/**
 * General data of reactive behavior of the system
 */
object ReactiveCommon : ReactiveLazyObject {

    /**
     * All users in the system
     */
    @Reactive<List<User>>(handler = HandlerReactiveCommonUsers::class)
    var systemUsers: List<User> = listOf()

}
