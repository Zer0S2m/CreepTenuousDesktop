package com.zer0s2m.creeptenuous.desktop.ui.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zer0s2m.creeptenuous.desktop.common.dto.ConverterColor
import com.zer0s2m.creeptenuous.desktop.common.dto.UserColor
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.common.utils.colorConvertHexToRgb
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveUser
import com.zer0s2m.creeptenuous.desktop.ui.screens.ProfileUser

/**
 * Rendering part of the user profile screen [Screen.PROFILE_COLORS_SCREEN]
 */
@Composable
fun ProfileUser.ProfileColors.render() {
    val stateModal: MutableState<Boolean> = remember { mutableStateOf(false) }
    val listColors: MutableList<Color> = remember {
        getUserColorRgbFromHex().toMutableStateList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ButtonCreateCustomColor(stateModal = stateModal)
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(listColors.size) { index ->
                    ColorItem(listColors[index]) {
                        listColors.removeAt(index)
                        ReactiveUser.userColors.removeAtReactive(index)
                    }
                }
            }
        }
    }

    ModalCreateCustomColor(stateModal = stateModal, listColors = listColors)
}

/**
 * Custom color base card
 *
 * @param color Color
 * @param action Detailed documentation щт [IconButtonDelete] in the argument `onClick`
 */
@Composable
internal fun ColorItem(
    color: Color,
    action: () -> Unit
) {
    BaseCardItemGrid {
        Box(
            modifier = Modifier
                .background(color = color, RoundedCornerShape(4.dp))
                .height(40.dp)
                .width(80.dp)
        )

        Row {
            IconButtonEdit(onClick = {})
            IconButtonDelete(onClick = action)
        }
    }
}

/**
 * Modal window for creating a custom category
 *
 * @param stateModal Modal window states for category creation
 * @param listColors Collection of custom flowers. Required to create a new color
*/
@Composable
internal fun ModalCreateCustomColor(
    stateModal: MutableState<Boolean>,
    listColors: MutableList<Color>
) {
    BaseModalPopup(
        stateModal = stateModal
    ) {
        Surface(
            contentColor = contentColorFor(MaterialTheme.colors.surface),
            modifier = Modifier
                .width(360.dp)
                .height(400.dp)
                .shadow(24.dp, RoundedCornerShape(4.dp))
        ) {
            ModalCreateCustomColorContent {
                stateModal.value = false
                listColors.add(it)
                ReactiveUser.userColors.addReactive(UserColor(
                    color = "#${Integer.toHexString(it.toArgb()).substring(2)}"
                ))
            }
        }
    }
}

/**
 * Filling the custom color creation form with content
 *
 * @param action Actions on button click. Takes on color to elevate the event
 */
@Composable
private fun ModalCreateCustomColorContent(action: (Color) -> Unit) {
    var red by remember { mutableStateOf(150f) }
    var green by remember { mutableStateOf(150f) }
    var blue by remember { mutableStateOf(150f) }

    val color = Color(
        red = red.toInt(),
        green = green.toInt(),
        blue = blue.toInt(),
        alpha = 255
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .pointerInput({}) {
                detectTapGestures(onPress = {
                    // Workaround to disable clicks on Surface background
                    // https://github.com/JetBrains/compose-jb/issues/2581
                })
            },
    ) {
        Text(
            text = "Create a custom color",
            fontSize = 20.sp
        )

        Column(
            modifier = Modifier
                .padding(top = 12.dp)
        ) {
            Text(text = "Red ${red.toInt()}")
            Slider(
                value = red,
                onValueChange = { red = it },
                valueRange = 0f..255f,
                modifier = Modifier
                    .pointerHoverIcon(PointerIcon.Hand)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Green ${green.toInt()}")
            Slider(
                value = green,
                onValueChange = { green = it },
                valueRange = 0f..255f,
                modifier = Modifier
                    .pointerHoverIcon(PointerIcon.Hand)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Blue ${blue.toInt()}")
            Slider(
                value = blue,
                onValueChange = { blue = it },
                valueRange = 0f..255f,
                modifier = Modifier
                    .pointerHoverIcon(PointerIcon.Hand)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = color, RoundedCornerShape(4.dp))
                    .height(40.dp)
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerHoverIcon(PointerIcon.Hand),
                onClick = {
                    action(color)
                }
            ) {
                Text("Create")
            }
        }
    }
}

/**
 * Basic button for opening a modal window for creating a custom color
 *
 * @param stateModal Modal window states for category creation
 */
@Composable
private fun ButtonCreateCustomColor(stateModal: MutableState<Boolean>) {
    Button(
        onClick = {
            stateModal.value = true
        },
        modifier = Modifier
            .fillMaxHeight()
            .pointerHoverIcon(PointerIcon.Hand)
    ) {
        Text("Create color")
    }
}

private fun getUserColorRgbFromHex(): Collection<Color> {
    val colors: MutableList<Color> = mutableListOf()

    ReactiveUser.userColors.forEach {
        val convertedColor: ConverterColor = colorConvertHexToRgb(it.color)

        colors.add(Color(
            red = convertedColor.red,
            green = convertedColor.green,
            blue = convertedColor.blue
        ))
    }

    return colors
}
