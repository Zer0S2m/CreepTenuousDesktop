package components.base

import components.fields.TextFieldAdvanced

interface BaseFormState {

    /**
     * Setting fields in form state
     *
     * @param fields Form fields
     */
    fun setFields(fields: List<BaseField>)

    /**
     * Getting fields in form state
     *
     * @return Form fields
     */
    fun getFields(): List<BaseField>

    /**
     * Validate all form components
     *
     * @return Is validated form
     */
    fun validateForm(): Boolean {
        return true
    }

    /**
     * Get data from a form. Format: [TextFieldAdvanced.name] -> [TextFieldAdvanced.text]
     *
     * @return data from fields
     */
    fun getData(): Map<String, Any> {
        return HashMap()
    }

}