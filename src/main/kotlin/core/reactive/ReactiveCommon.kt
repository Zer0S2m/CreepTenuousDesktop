package core.reactive

import core.handlers.HandlerReactiveCommonUsers
import dto.User

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
