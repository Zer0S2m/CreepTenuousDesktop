package com.zer0s2m.creeptenuous.desktop.core.reactive

import com.zer0s2m.creeptenuous.desktop.core.handlers.HandlerReactiveCommonUsers
import com.zer0s2m.creeptenuous.desktop.common.dto.User

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
