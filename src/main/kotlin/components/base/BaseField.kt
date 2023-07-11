package components.base

import androidx.compose.runtime.Composable
import core.validation.Validator

/**
 * Basic interface for implementing simple fields
 */
interface BaseField {

    /**
     * Value used in the field
     */
    var text: String

    /**
     * Value to use when throwing a validation error
     */
    var label: String

    /**
     * Whether to show errors or not is regulated by the validation method [validate]
     */
    var hasError: Boolean

    val validators: List<Validator>
        get() = listOf()

    /**
     * Component rendering
     */
    @Composable
    fun render()

    /**
     * Validate the field. Used validator [Validator]
     *
     * @return Is validated field
     */
    fun validate(): Boolean {
        return true
    }

}