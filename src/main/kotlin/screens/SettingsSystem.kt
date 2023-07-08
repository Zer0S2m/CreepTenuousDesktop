package screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import enums.SizeComponents

class SettingsSystem {

    @Composable
    fun SettingsSystem() {
        val scaffoldState: ScaffoldState = rememberScaffoldState()

        var textFieldHost by remember {
            mutableStateOf("")
        }
        var textFieldPort by remember {
            mutableStateOf("80")
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(), scaffoldState = scaffoldState
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)
            ) {
                TextField(
                    value = textFieldHost,
                    label = {
                        Text("Main system host")
                    },
                    onValueChange = {
                        textFieldHost = it
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
                    value = textFieldPort,
                    label = {
                        Text("Main system port")
                    },
                    onValueChange = {
                        textFieldPort = it
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    modifier = Modifier.width(SizeComponents.WIDTH_FIELD.size)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {

                    },
                    modifier = Modifier.size(SizeComponents.WIDTH_BUTTON.size, 46.dp)
                ) {
                    Text("Connection")
                }
            }
        }
    }

}