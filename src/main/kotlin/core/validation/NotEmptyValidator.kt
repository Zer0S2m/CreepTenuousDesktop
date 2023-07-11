package core.validation

import enums.ErrorFields

/**
 * Validator for an empty value in a field
 *
 * @param inputMsg Message text for error output
 */
open class NotEmptyValidator(
    inputMsg: String = ErrorFields.REQUIRED_MESSAGE.msg
) : Validator {

    /**
     * Validation error message
     */
    final override val msg: String = inputMsg

    /**
     * Field Validation
     *
     * @param value The string value that must be validated
     */
    override fun validate(value: Any): Boolean {
        if (value is String) {
            return value.toString().isNotEmpty()
        }
        return true
    }

}