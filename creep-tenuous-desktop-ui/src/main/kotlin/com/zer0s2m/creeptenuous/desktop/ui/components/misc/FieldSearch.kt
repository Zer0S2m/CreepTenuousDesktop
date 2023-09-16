package com.zer0s2m.creeptenuous.desktop.ui.components.misc

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zer0s2m.creeptenuous.desktop.common.enums.Resources
import com.zer0s2m.creeptenuous.desktop.ui.components.base.BaseFieldSearch

/**
 * Search text field class. Extends the component [BasicTextField]
 *
 * @param text Value used in the field
 */
class FieldSearch(
    private val text: String = ""
) : BaseFieldSearch {

    /**
     * Text used by accessibility services to describe what this image represents
     */
    private val contentDescriptionIconSearch: String = "Search icon"

    /**
     * Component rendering
     */
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun render() {
        var textField by remember { mutableStateOf(text) }
        val colors: TextFieldColors = TextFieldDefaults.textFieldColors()

        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            IconButton(
                onClick = {
                    println(textField)
                },
                modifier = Modifier
                    .padding(end = 8.dp)
                    .pointerHoverIcon(icon = PointerIcon.Hand)
            ) {
                Icon(
                    painter = painterResource(resourcePath = Resources.ICON_SEARCH.path),
                    contentDescription = contentDescriptionIconSearch,
                    modifier = Modifier
                        .padding(0.dp)
                        .width(28.dp)
                        .height(28.dp)
                )
            }
            BasicTextField(
                value = textField,
                onValueChange = { value ->
                    textField = value
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.None
                ),
                textStyle = LocalTextStyle
                    .current
                    .merge(TextStyle(
                        color = colors.textColor(true).value,
                        fontSize = 14.sp
                    )),
                cursorBrush = SolidColor(TextFieldDefaults
                    .textFieldColors()
                    .cursorColor(false).value),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp)
                    .background(
                        colors.backgroundColor(true).value, RoundedCornerShape(4.dp)
                    ),
                singleLine = true,
                decorationBox = @Composable { innerTextField ->
                    TextFieldDefaults.TextFieldDecorationBox(
                        value = textField,
                        enabled = true,
                        innerTextField = innerTextField,
                        singleLine = true,
                        interactionSource = remember { MutableInteractionSource() },
                        visualTransformation = VisualTransformation.None,
                        contentPadding = PaddingValues(start = 8.dp)
                    )
                }
            )
        }
    }

}