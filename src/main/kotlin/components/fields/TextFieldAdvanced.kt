package components.fields

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import components.base.BaseField
import components.forms.FormState
import core.validation.Validator
import enums.SizeComponents

/**
 * Text field. Renders the component [TextField]
 *
 * @param textField Value used in the field
 * @param labelField Value to use when throwing a validation error
 * @param validators Validators used to validate a field when calling a method from form state [FormState] or directly
 */
class TextFieldAdvanced(
    val textField: String = "",
    val labelField: String = "",
    override val validators: List<Validator> = listOf()
) : BaseField {

    /**
     * Value used in the field
     */
    override var text: String by mutableStateOf(textField)

    /**
     * Value to use when throwing a validation error
     */
    override var label: String by mutableStateOf(labelField)

    /**
     * Whether to show errors or not is regulated by the validation method [validate]
     */
    override var hasError: Boolean by mutableStateOf(false)

    /**
     * Component rendering. [TextField]
     */
    @Composable
    override fun render() {
        TextField(
            value = text,
            isError = hasError,
            label = {
                Text(text = label)
            },
            onValueChange = { value ->
                hideError()
                text = value
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            minLines = 1,
            modifier = Modifier.width(SizeComponents.WIDTH_FIELD.size)
        )
    }

    private fun showError(error: String) {
        hasError = true
        label = error
    }

    private fun hideError() {
        label = labelField
        hasError = false
    }

    /**
     * Validate the field. Used validator [Validator].
     * Called directly or via form states [FormState.validateForm]
     *
     * @return Is validated field
     */
    override fun validate(): Boolean {
        var isValid = true
        for (validator in validators) {
            if (!validator.validate(text)) {
                showError(validator.msg)
                isValid = false
            }
        }
        return isValid
    }

}