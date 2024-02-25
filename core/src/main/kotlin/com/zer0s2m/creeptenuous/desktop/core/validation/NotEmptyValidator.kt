package com.zer0s2m.creeptenuous.desktop.core.validation

import com.zer0s2m.creeptenuous.desktop.common.enums.ErrorFields

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
            return value.toString().trim().isNotEmpty()
        }
        return true
    }

}