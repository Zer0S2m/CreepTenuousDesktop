package core.validation

/**
 * Basic interface for implementing field validation
 */
sealed interface Validator {

    /**
     * Validation error message
     */
    val msg: String

    /**
     * Field Validation
     *
     * @param value Value to be verified
     */
    fun validate(value: Any): Boolean

}