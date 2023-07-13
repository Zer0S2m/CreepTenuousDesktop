package screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import components.fields.TextFieldAdvanced
import components.forms.Form
import components.forms.FormState
import core.validation.NotEmptyValidator
import dto.LoginUserModel
import enums.SizeComponents
import kotlinx.coroutines.launch

class LoginUser {

    @Composable
    fun LoginUser() {
        val scaffoldState: ScaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()

        val stateForm by remember {
            mutableStateOf(FormState())
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(), scaffoldState = scaffoldState
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)
            ) {
                Form(
                    state = stateForm,
                    fields = listOf(
                        TextFieldAdvanced(
                            nameField = "login",
                            labelField = "Enter your login",
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.None,
                                autoCorrect = false,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            validators = listOf(NotEmptyValidator())
                        ),
                        TextFieldAdvanced(
                            nameField = "password",
                            labelField = "Enter your password",
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.None,
                                autoCorrect = false,
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Next
                            ),
                            visualTransformation = PasswordVisualTransformation(),
                            validators = listOf(NotEmptyValidator())
                        )
                    ),
                    modifierSpacer = Modifier.height(16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (!stateForm.validateForm()) {
                            scope.launch {
                                scaffoldState.snackbarHostState
                                    .showSnackbar("Please fill in all required fields")
                            }
                        } else {
                            val data = stateForm.getData()
                            val dataClass = LoginUserModel(
                                data["login"].toString(),
                                data["password"].toString()
                            )
                        }
                    },
                    modifier = Modifier.size(SizeComponents.WIDTH_BUTTON.size, 46.dp)
                ) {
                    Text("Login")
                }
            }
        }
    }

}
