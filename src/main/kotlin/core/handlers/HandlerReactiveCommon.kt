package core.handlers

import core.http.HttpClient
import core.reactive.ReactiveHandler
import dto.User
import io.ktor.client.call.*
import io.ktor.client.request.get

/**
 * Reactive behavior handler to get list of system users
 */
object HandlerReactiveCommonUsers : ReactiveHandler<List<User>> {

    /**
     * Process reactive property
     *
     * @return users
     */
    override suspend fun handler(): List<User> {
        return HttpClient.client.get("/api/v1/user/control/list").body()
    }

}
