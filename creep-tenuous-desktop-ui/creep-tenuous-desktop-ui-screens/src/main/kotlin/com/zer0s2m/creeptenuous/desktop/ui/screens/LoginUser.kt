package com.zer0s2m.creeptenuous.desktop.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.zer0s2m.creeptenuous.desktop.common.data.DataLoginUser
import com.zer0s2m.creeptenuous.desktop.common.dto.ConfigState
import com.zer0s2m.creeptenuous.desktop.common.dto.JwtTokens
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.common.enums.SizeComponents
import com.zer0s2m.creeptenuous.desktop.common.utils.saveStorageConfigStateDesktop
import com.zer0s2m.creeptenuous.desktop.core.auth.AuthorizationHandler
import com.zer0s2m.creeptenuous.desktop.core.navigation.actions.reactiveNavigationScreen
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveLoader
import com.zer0s2m.creeptenuous.desktop.core.state.SystemSettings
import com.zer0s2m.creeptenuous.desktop.core.validation.NotEmptyValidator
import com.zer0s2m.creeptenuous.desktop.navigation.NavigationController
import com.zer0s2m.creeptenuous.desktop.ui.components.Form
import com.zer0s2m.creeptenuous.desktop.ui.components.FormState
import com.zer0s2m.creeptenuous.desktop.ui.components.TextFieldAdvanced
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.dp
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
                            val dataClass = DataLoginUser(
                                data["login"].toString(),
                                data["password"].toString()
                            )

                            scope.launch {
                                try {
                                    val tokens: JwtTokens = AuthorizationHandler.login(
                                        login = dataClass.login,
                                        password = dataClass.password
                                    )

                                    SystemSettings.accessToken = tokens.accessToken
                                    SystemSettings.refreshToken = tokens.refreshToken

                                    saveStorageConfigStateDesktop(
                                        data = ConfigState(
                                            host = SystemSettings.host,
                                            port = SystemSettings.port,
                                            login = dataClass.login,
                                            password = dataClass.password,
                                            accessToken = null,
                                            refreshToken = null
                                        )
                                    )

                                    ReactiveLoader.setIsBlockLoad(false)

                                    reactiveNavigationScreen.action(
                                        state = mutableStateOf(navigationController),
                                        route = Screen.DASHBOARD_SCREEN,
                                        objects = listOf(
                                            "managerFileSystemObjects",
                                            "customCategories",
                                            "userColors",
                                            "profileSettings",
                                            "systemUsers"
                                        ),
                                        scope = scope
                                    )
                                } catch (e: Exception) {
                                    scaffoldState.snackbarHostState
                                        .showSnackbar("Unauthorized")
                                }
                            }
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
