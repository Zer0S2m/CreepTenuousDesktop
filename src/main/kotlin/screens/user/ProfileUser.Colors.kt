package screens.user

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
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import screens.ProfileUser
import enums.Screen

/**
 * Rendering part of the user profile screen [Screen.PROFILE_COLORS_SCREEN]
 */
@Composable
fun ProfileUser.ProfileColors.render() {
    val stateModal: MutableState<Boolean> = remember { mutableStateOf(false) }

    val listColors: MutableList<Color> = remember {
        listOf(
            Color(140, 150, 33),
            Color(3, 2, 33),
            Color(33, 1, 33),
            Color(2, 22, 23),
            Color(12, 150, 123),
            Color(12, 22, 33),
            Color(32, 123, 33)
        ).toMutableStateList()
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
                columns = GridCells.Fixed(5),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(listColors.size) { index ->
                    ColorItem(listColors[index]) {
                        listColors.removeAt(index)
                    }
                }
            }
        }
    }

    ModalCreateCustomColor(stateModal = stateModal)
}

/**
 * Custom color base card
 *
 * @param color Color
 */
@Composable
internal fun ProfileUser.ProfileColors.ColorItem(
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

        IconButtonDelete(onClick = action)
    }
}

/**
 * Modal window for creating a custom category
 *
 * @param stateModal Modal window states for category creation
*/
@Composable
internal fun ProfileUser.ProfileColors.ModalCreateCustomColor(
    stateModal: MutableState<Boolean>
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
            ModalCreateCustomColorContent(stateModal = stateModal)
        }
    }
}

@Composable
private fun ModalCreateCustomColorContent(stateModal: MutableState<Boolean>) {
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
                    stateModal.value = false
                    println("Create color")
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
