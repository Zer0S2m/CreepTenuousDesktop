package core.validation

import enums.ErrorFields

/**
 * Validator for max number
 *
 * @param maxNumber Maximum allowed number
 * @param inputMsg Message text for error output
 */
open class MaxNumberValidator(
    maxNumber: Int,
    inputMsg: String = ErrorFields.MAX_NUMBER.msg,
) : Validator {

    private val max: Int = maxNumber

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
            val valueFromField = value.toString().toDoubleOrNull()
            if (valueFromField != null) {
                return valueFromField < max
            }
        }
        return true
    }

}