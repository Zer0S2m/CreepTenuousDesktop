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
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveUser
import com.zer0s2m.creeptenuous.desktop.ui.screens.ProfileUser
import com.zer0s2m.creeptenuous.desktop.ui.screens.base.BaseModalPopup

/**
 * Rendering part of the user profile screen [Screen.PROFILE_COLORS_SCREEN]
 */
@Composable
@Suppress("UnusedReceiverParameter")
fun ProfileUser.ProfileColors.render() {
    val stateModal: MutableState<Boolean> = remember { mutableStateOf(false) }
    val currentUserColor: MutableState<Color> = remember {
        mutableStateOf(Color(150, 150, 150))
    }
    val currentIndexUserColor: MutableState<Int> = remember { mutableStateOf(-1) }
    val isEditUserColor: MutableState<Boolean> = remember { mutableStateOf(false) }
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
            ButtonCreateCustomColor {
                isEditUserColor.value = false
                stateModal.value = true
                currentUserColor.value = Color(150, 150, 150)
            }
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
                    ColorItem(
                        color = listColors[index],
                        actionEdit = {
                            isEditUserColor.value = true
                            stateModal.value = true
                            currentIndexUserColor.value = index
                            currentUserColor.value = listColors[index]
                        },
                        actionDelete = {
                            listColors.removeAt(index)
                            ReactiveUser.userColors.removeAtReactive(index)
                        }
                    )
                }
            }
        }
    }

    ModalCreateCustomColor(
        isExists = isEditUserColor,
        stateColor = currentUserColor.value,
        stateModal = stateModal,
        currentIndexColor = currentIndexUserColor,
        listColors = listColors
    )
}

/**
 * Custom color base card
 *
 * @param color Color
 * @param actionEdit Actions on button click. Takes on color to elevate the event - editing
 * @param actionDelete Action detailed documentation щт [IconButtonDelete] in the argument `onClick`
 */
@Composable
internal fun ColorItem(
    color: Color,
    actionEdit: () -> Unit,
    actionDelete: () -> Unit
) {
    BaseCardItemGrid {
        Box(
            modifier = Modifier
                .background(color = color, RoundedCornerShape(4.dp))
                .height(40.dp)
                .width(80.dp)
        )

        Row {
            IconButtonEdit(onClick = actionEdit)
            IconButtonDelete(onClick = actionDelete)
        }
    }
}

/**
 * Modal window for creating a custom category
 *
 * @param isExists Does an object exist, depending on this, a certain action will occur
 * @param stateColor The current state of the custom color when manipulated
 * @param stateModal Modal window states for category creation
 * @param listColors Collection of custom flowers. Required to create a new color
*/
@Composable
internal fun ModalCreateCustomColor(
    isExists: MutableState<Boolean>,
    stateColor: Color,
    currentIndexColor: MutableState<Int>,
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
            ModalCreateCustomColorContent(
                isExists = isExists.value,
                stateColor = stateColor,
                actionCreate = {
                    stateModal.value = false
                    isExists.value = false
                    listColors.add(it)
                    ReactiveUser.userColors.addReactive(UserColor(
                        color = "#${Integer.toHexString(it.toArgb()).substring(2)}"
                    ))
                },
                actionEdit = {
                    stateModal.value = false
                    isExists.value = false
                    listColors[currentIndexColor.value] = it
                    ReactiveUser.userColors.setReactive(
                        index = currentIndexColor.value,
                        element = UserColor(
                            color = "#${Integer.toHexString(it.toArgb()).substring(2)}"
                        )
                    )
                }
            )
        }
    }
}

/**
 * Filling the custom color creation form with content
 *
 * @param isExists Does an object exist, depending on this, a certain action will occur
 * @param stateColor The current state of the custom color when manipulated
 * @param actionCreate Actions on button click. Takes on color to elevate the event - creating
 * @param actionEdit Actions on button click. Takes on color to elevate the event - editing
 */
@Composable
private fun ModalCreateCustomColorContent(
    isExists: Boolean,
    stateColor: Color,
    actionCreate: (Color) -> Unit,
    actionEdit: (Color) -> Unit
) {
    var red by remember { mutableStateOf(255 *stateColor.red) }
    var green by remember { mutableStateOf(255 *stateColor.green) }
    var blue by remember { mutableStateOf(255 *stateColor.blue) }

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
            text = if (isExists) "Edit a custom color" else "Create a custom color",
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
                    if (isExists) actionEdit(color) else actionCreate(color)
                }
            ) {
                Text(if (isExists) "Edit" else "Create")
            }
        }
    }
}

/**
 * Basic button for opening a modal window for creating a custom color
 *
 * @param action Will be called when the user clicks the [Button]
 */
@Composable
private fun ButtonCreateCustomColor(action: () -> Unit) {
    Button(
        onClick = action,
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
