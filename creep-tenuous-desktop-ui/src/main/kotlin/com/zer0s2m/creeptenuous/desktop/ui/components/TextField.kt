package com.zer0s2m.creeptenuous.desktop.ui.components

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.zer0s2m.creeptenuous.desktop.common.enums.SizeComponents
import com.zer0s2m.creeptenuous.desktop.core.validation.Validator
import com.zer0s2m.creeptenuous.desktop.ui.components.base.BaseField
import com.zer0s2m.creeptenuous.desktop.ui.components.forms.FormState
import com.zer0s2m.creeptenuous.desktop.ui.misc.dp

/**
 * Text field. Renders the component [TextField]
 *
 * @param nameField Unique field name
 * @param textField Value used in the field
 * @param labelField Value to use when throwing a validation error
 * @param keyboardOptions The keyboard configuration options for TextFields [TextField]
 * @param visualTransformation Visual output of the input field.
 * @param validators Validators used to validate a field when calling a method from form state [FormState] or directly
 * @param modifier Modifiers to decorate or add behavior to elements
 * @param shape The shape of the text field's container
 */
class TextFieldAdvanced(
    nameField: String,
    val textField: String = "",
    val labelField: String = "",
    val keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    val visualTransformation: VisualTransformation = VisualTransformation.None,
    override val validators: List<Validator> = listOf(),
    private val modifier: Modifier = Modifier
        .width(SizeComponents.WIDTH_FIELD.dp),
    private val shape: Shape = RoundedCornerShape(4.dp)
) : BaseField {

    /**
     * Unique field name
     */
    override val name: String = nameField

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
        if (label.isNotEmpty()) {
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
                keyboardOptions = keyboardOptions,
                singleLine = true,
                minLines = 1,
                modifier = modifier,
                visualTransformation = visualTransformation,
                shape = shape
            )
        } else {
            TextField(
                value = text,
                isError = hasError,
                onValueChange = { value ->
                    hideError()
                    text = value
                },
                keyboardOptions = keyboardOptions,
                singleLine = true,
                minLines = 1,
                modifier = modifier,
                visualTransformation = visualTransformation,
                shape = shape
            )
        }
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
