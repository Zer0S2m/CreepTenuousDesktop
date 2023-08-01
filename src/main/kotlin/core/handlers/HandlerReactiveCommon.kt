package core.handlers

import core.reactive.ReactiveHandler
import dto.User

/**
 * Reactive behavior handler to get list of system users
 */
object HandlerReactiveCommonUsers : ReactiveHandler<List<User>> {

    /**
     * Process reactive property
     *
     * @return users
     */
    override fun handler(): List<User> {
        return listOf(
            User("User name 1", null, "user_name_1"),
            User("User name 2", null, "user_name_2")
        )
    }

}