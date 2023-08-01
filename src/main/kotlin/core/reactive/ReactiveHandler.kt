package core.reactive

/**
 * Base interface for a reactive behavior handler
 *
 * @param T Return type
 */
fun interface ReactiveHandler<T> {

    /**
     * Process reactive property
     *
     * @return result
     */
    suspend fun handler(): T

}
