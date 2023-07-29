package screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import enums.Screen
import screens.ProfileUser

/**
 * Rendering part of the user profile screen [Screen.PROFILE_FILE_OBJECT_DISTRIBUTION]
 */
@Composable
fun ProfileUser.ProfileFileObjectDistribution.render() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                baseTitle("Delete them if necessary (has the highest priority)")
                switch()
            }
        }

        Row(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                baseTitle("Passing objects to an assigned user")
                selectUserDropMenu()
            }
        }
    }
}

/**
 * Base title for a particular setting item
 *
 * @param text The text to be displayed
 */
@Composable
private fun baseTitle(text: String) = Text(
    text = text,
    color = Color.Black,
    fontSize = 16.sp,
    fontWeight = FontWeight.SemiBold
)

/**
 * Distribution setting switch
 */
@Composable
internal fun ProfileUser.ProfileFileObjectDistribution.switch() {
    val checkedState = remember { mutableStateOf(true) }
    Switch(
        checked = checkedState.value,
        modifier = Modifier
            .pointerHoverIcon(icon = PointerIcon.Hand),
        onCheckedChange = { checkedState.value = it }
    )
}

/**
 * Selecting a user to set up a distribution
 */
@Composable
internal fun ProfileUser.ProfileFileObjectDistribution.selectUserDropMenu() {
    Column(
        modifier = Modifier
            .width(200.dp)
            .padding(top = 12.dp)
    ) {
        UserLoginTextField(text = "User login 1")
    }
}

/**
 * Base field for displaying the user's login when its value is selected
 *
 * @param text the input [String] text to be shown in the text field
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun UserLoginTextField(text: String) = BasicTextField(
    value = text,
    onValueChange = {},
    readOnly = true,
    singleLine = true,
    modifier = Modifier
        .fillMaxWidth()
        .background(
            color = Color.Gray,
            shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
        )
        .padding(12.dp),
    decorationBox = @Composable { innerTextField ->
        TextFieldDefaults.TextFieldDecorationBox(
            value = text,
            enabled = false,
            innerTextField = innerTextField,
            singleLine = true,
            interactionSource = remember { MutableInteractionSource() },
            visualTransformation = VisualTransformation.None,
            contentPadding = PaddingValues()
        )
    }
)
