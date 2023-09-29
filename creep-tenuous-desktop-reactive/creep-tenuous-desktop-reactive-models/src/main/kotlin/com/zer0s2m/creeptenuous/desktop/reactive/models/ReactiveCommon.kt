package com.zer0s2m.creeptenuous.desktop.reactive.models

import com.zer0s2m.creeptenuous.desktop.common.dto.User
import com.zer0s2m.creeptenuous.desktop.core.reactive.*
import com.zer0s2m.creeptenuous.desktop.reactive.handlers.HandlerReactiveCommonUsers
import com.zer0s2m.creeptenuous.desktop.reactive.triggers.common.ReactiveTriggerReactiveSystemUserRemove
import com.zer0s2m.creeptenuous.desktop.reactive.triggers.common.ReactiveTriggerReactiveSystemUserUnblock

/**
 * General data of reactive behavior of the system
 */
object ReactiveCommon : ReactiveLazyObject {

    /**
     * All users in the system
     */
    @Reactive<ReactiveMutableList<User>>(
        handler = HandlerReactiveCommonUsers::class,
        independentTriggers = [
            ReactiveIndependentTrigger(
                event = "unblockSystemUser",
                trigger = ReactiveTriggerReactiveSystemUserUnblock::class
            )
        ]
    )
    var systemUsers: ReactiveMutableList<User> = mutableReactiveListOf(
        triggerRemove = ReactiveTriggerReactiveSystemUserRemove()
    )

}
