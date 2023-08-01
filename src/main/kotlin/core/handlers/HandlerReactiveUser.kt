package core.handlers

import core.reactive.ReactiveHandler
import dto.User

object HandlerLazyUserSettingsDeleteFiles : ReactiveHandler<Boolean?> {

    /**
     * Process reactive property
     *
     * @return result
     */
    override fun handler(): Boolean {
        return true
    }

}

object HandlerLazyUserSettingsPassingFiles : ReactiveHandler<User?> {

    /**
     * Process reactive property
     *
     * @return result
     */
    override fun handler(): User? {
        return null
    }

}
