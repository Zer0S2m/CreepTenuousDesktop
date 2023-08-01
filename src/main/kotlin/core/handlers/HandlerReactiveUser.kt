package core.handlers

import core.http.HttpClient
import core.reactive.ReactiveHandler
import dto.User
import dto.UserCategory
import io.ktor.client.call.*
import io.ktor.client.request.*

object HandlerLazyUserSettingsDeleteFiles : ReactiveHandler<Boolean?> {

    /**
     * Process reactive property
     *
     * @return result
     */
    override suspend fun handler(): Boolean {
        return true
    }

}

object HandlerLazyUserSettingsPassingFiles : ReactiveHandler<User?> {

    /**
     * Process reactive property
     *
     * @return result
     */
    override suspend fun handler(): User? {
        return null
    }

}

object HandlerReactiveUserCustomCategories : ReactiveHandler<MutableCollection<UserCategory>> {

    /**
     * Process reactive property
     *
     * @return result
     */
    override suspend fun handler(): MutableCollection<UserCategory> {
        return HttpClient.client.get("/api/v1/user/category").body()
    }

}
