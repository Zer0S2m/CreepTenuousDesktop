package components.base

import core.validation.Validator
import components.forms.FormState

/**
 * Basic interface for implementing simple fields
 */
interface BaseField: BaseComponent {

    /**
     * Unique field name
     */
    val name: String
        get() = ""

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

    /**
     * Validators used to validate a field when calling a method from form state [FormState] or directly
     */
    val validators: List<Validator>
        get() = listOf()

    /**
     * Validate the field. Used validator [Validator]
     *
     * @return Is validated field
     */
    fun validate(): Boolean {
        return true
    }

}