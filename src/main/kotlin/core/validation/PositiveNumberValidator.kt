package core.validation

import enums.ErrorFields

/**
 * Validator for validating a positive number
 *
 * @param inputMsg Message text for error output
 */
open class PositiveNumberValidator(
    inputMsg: String = ErrorFields.POSITIVE_INT.msg
) : Validator {

    /**
     * Validation error message
     */
    final override val msg: String = inputMsg

    /**
     * Field Validation
     *
     * @param value Value to be verified
     */
    override fun validate(value: Any): Boolean {
        if (value is String) {
            val regex = "^[^0]\\d+\$".toRegex()
            return value.toString().matches(regex)
        }
        return true
    }

}
