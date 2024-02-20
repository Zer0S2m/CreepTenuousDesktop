package com.zer0s2m.creeptenuous.desktop.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.zer0s2m.creeptenuous.desktop.common.dto.LoginUserModel
import com.zer0s2m.creeptenuous.desktop.common.enums.SizeComponents
import com.zer0s2m.creeptenuous.desktop.core.validation.NotEmptyValidator
import com.zer0s2m.creeptenuous.desktop.navigation.NavigationController
import com.zer0s2m.creeptenuous.desktop.ui.components.TextFieldAdvanced
import com.zer0s2m.creeptenuous.desktop.ui.components.forms.Form
import com.zer0s2m.creeptenuous.desktop.ui.components.forms.FormState
import com.zer0s2m.creeptenuous.desktop.ui.misc.dp
import kotlinx.coroutines.launch

class LoginUser {

    @Composable
    fun LoginUser(
        navigationController: NavigationController
    ) {
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

//                            ReactiveLoader.setIsBlockLoad(false)
//
//                            scope.launch {
//                                reactiveNavigationScreen.action(
//                                    state = mutableStateOf(navigationController),
//                                    route = Screen.DASHBOARD_SCREEN,
//                                    objects = listOf(
//                                        "managerFileSystemObjects",
//                                        "customCategories",
//                                        "userColors",
//                                        "profileSettings"
//                                    ),
//                                    scope = scope
//                                )
//                            }
                        }
                    },
                    modifier = Modifier
                        .size(SizeComponents.WIDTH_BUTTON.dp, 46.dp)
                        .pointerHoverIcon(icon = PointerIcon.Hand),
                ) {
                    Text("Login")
                }
            }
        }
    }

}
