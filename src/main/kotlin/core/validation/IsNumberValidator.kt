package core.validation

import enums.ErrorFields

/**
 * Numeric field validator
 *
 * @param inputMsg Message text for error output
 */
open class IsNumberValidator(
    inputMsg: String = ErrorFields.IS_INT.msg
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
            return value.toString().toDoubleOrNull() != null
        }
        return true
    }

}