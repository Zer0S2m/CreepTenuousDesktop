package screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import components.fields.TextFieldAdvanced
import components.forms.Form
import components.forms.FormState
import core.navigation.NavigationController
import core.validation.MaxNumberValidator
import core.validation.NotEmptyValidator
import core.validation.PositiveNumberValidator
import dto.SettingsSystemModel
import enums.Screen
import enums.SizeComponents
import enums.dp
import kotlinx.coroutines.launch

class SettingsSystem {

    @Composable
    fun SettingsSystem(
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
                            nameField = "host",
                            labelField = "Main system host",
                            validators = listOf(NotEmptyValidator())
                        ),
                        TextFieldAdvanced(
                            nameField = "port",
                            labelField = "Main system port",
                            textField = "80",
                            validators = listOf(
                                NotEmptyValidator(),
                                PositiveNumberValidator(),
                                MaxNumberValidator(maxNumber = 65535)
                            )
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
                            val dataClass = SettingsSystemModel(
                                data["host"].toString(),
                                data["port"].toString().toInt()
                            )
                            println(dataClass)
                            navigationController.navigate(Screen.LOGIN_SCREEN.name)
                        }
                    },
                    modifier = Modifier
                        .size(SizeComponents.WIDTH_BUTTON.dp, 46.dp)
                        .pointerHoverIcon(icon = PointerIcon.Hand),
                ) {
                    Text("Connection")
                }
            }
        }
    }

}