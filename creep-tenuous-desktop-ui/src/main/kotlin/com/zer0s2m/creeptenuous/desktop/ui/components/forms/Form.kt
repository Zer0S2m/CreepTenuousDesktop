package com.zer0s2m.creeptenuous.desktop.ui.components.forms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zer0s2m.creeptenuous.desktop.ui.components.base.BaseField
import com.zer0s2m.creeptenuous.desktop.ui.components.base.BaseFormState
import com.zer0s2m.creeptenuous.desktop.ui.components.fields.TextFieldAdvanced

/**
 * Basic form for drawing components and their subsequent validation
 *
 * @param state Form State
 * @param fields Fields for rendering
 * @param modifierSpacer Modifiers to decorate or add behavior to elements
 */
@Composable
fun Form(
    state: BaseFormState,
    fields: List<BaseField>,
    modifierSpacer: Modifier = Modifier.height(0.dp)
) {
    state.setFields(fields)
    val fieldsInternal = state.getFields()

    Column {
        fieldsInternal.forEachIndexed { index, baseField ->
            baseField.render()
            if (index != fieldsInternal.size - 1) {
                Spacer(modifierSpacer)
            }
        }
    }
}

/**
 * Form state for data validation
 */
class FormState : BaseFormState {

    /**
     * Field storage
     */
    private lateinit var fields: List<BaseField>

    /**
     * Setting fields in form state
     *
     * @param fields Form fields
     */
    override fun setFields(fields: List<BaseField>) {
        this.fields = fields
    }

    /**
     * Getting fields in form state
     *
     * @return Form fields
     */
    override fun getFields(): List<BaseField> {
        return fields
    }

    /**
     * Validate all form components
     *
     * @return Is validated form
     */
    override fun validateForm(): Boolean {
        var valid = true
        for (field in getFields()) {
            if (!field.validate()) {
                valid = false
            }
        }
        return valid
    }

    /**
     * Get data from a form. Format: [TextFieldAdvanced.name] -> [TextFieldAdvanced.text]
     *
     * @return data from fields
     */
    override fun getData(): Map<String, Any> {
        return fields.associate {
            it.name to it.text
        }
    }

}
