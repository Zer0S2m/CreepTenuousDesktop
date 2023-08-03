package com.zer0s2m.creeptenuous.desktop.common.enums

/**
 * Class that stores error messages
 *
 * @param msg Error message
 */
enum class ErrorFields(val msg: String) {

    REQUIRED_MESSAGE("This field is required"),

    IS_INT("This field must be filled with a number"),

    POSITIVE_INT("This field must be greater than a number"),

    MAX_NUMBER("The number does not match the maximum allowed number")

}