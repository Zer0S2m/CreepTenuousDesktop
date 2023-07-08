package screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import enums.SizeComponents
import kotlinx.coroutines.launch

class LoginUser {

    @Composable
    fun LoginUser() {
        val scaffoldState: ScaffoldState = rememberScaffoldState()

        var textFieldLogin by remember {
            mutableStateOf("")
        }
        var textFieldPassword by remember {
            mutableStateOf("")
        }
        val scope = rememberCoroutineScope()

        Scaffold(
            modifier = Modifier.fillMaxSize(), scaffoldState = scaffoldState
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)
            ) {
                TextField(
                    value = textFieldLogin,
                    label = {
                        Text("Enter your Email")
                    },
                    onValueChange = {
                        textFieldLogin = it
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    modifier = Modifier.width(SizeComponents.WIDTH_FIELD.size)
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = textFieldPassword,
                    label = {
                        Text("Enter your password")
                    },
                    onValueChange = {
                        textFieldPassword = it
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    modifier = Modifier.width(SizeComponents.WIDTH_FIELD.size)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Button Clicked $textFieldLogin")
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
