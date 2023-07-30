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
import androidx.compose.ui.awt.awtEventOrNull
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import core.validation.NotEmptyValidator
import dto.UserCategory
import enums.Resources
import enums.Screen
import screens.ProfileUser
import ui.components.base.BaseFormState
import ui.components.fields.TextFieldAdvanced
import ui.components.forms.Form
import ui.components.forms.FormState
import java.awt.event.KeyEvent

/**
 * Rendering part of the user profile screen [Screen.PROFILE_CATEGORY_SCREEN]
 */
@Composable
fun ProfileUser.ProfileCategories.render() {
    val openModalCreateCategory: MutableState<Boolean> = remember { mutableStateOf(false) }

    val listCategories: MutableList<String> = remember {
        (1 .. 7).map { "Category $it" }.toMutableStateList()
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
            ButtonCreateCategory(stateModal = openModalCreateCategory)
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
                items(listCategories.size) { index ->
                    ItemCategory(listCategories[index]) {
                        listCategories.removeAt(index)
                    }
                }
            }
        }

        ModalCreateCategory(stateModal = openModalCreateCategory) {
            openModalCreateCategory.value = false

            val dataForm = stateForm.value.getData()
            val data = UserCategory(
                title = dataForm["title"].toString()
            )
            listCategories.add(data.title)
        }
    }
}

/**
 * Form state for modal window create category
 */
@Stable
private val stateForm: MutableState<BaseFormState> = mutableStateOf(FormState())

/**
 * Text used by accessibility services to describe what this image represents
 */
@Stable
private val contentDescriptionDelete: String get() = "Delete category icon"

/**
 * Custom category card. Extends a component [Card]
 *
 * @param text Display text
 * @param action The lambda to be invoked when this icon is pressed
 */
@Composable
internal fun ProfileUser.ProfileCategories.ItemCategory(
    text: String,
    action: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp, 0.dp)
        ) {
            Text(
                text = text
            )

            IconButton(
                onClick = action,
                modifier = Modifier
                    .padding(4.dp)
            ) {
                Icon(
                    painter = painterResource(resourcePath = Resources.ICON_DELETE.path),
                    contentDescription = contentDescriptionDelete,
                    modifier = Modifier
                        .size(24.dp)
                        .pointerHoverIcon(PointerIcon.Hand),
                    tint = Color.Red
                )
            }
        }
    }
}

/**
 * Modal window for creating a category. Extended component [Popup]
 *
 * @param stateModal Modal window states for category creation
 * @param action [Button] click event
 */
@Composable
internal fun ProfileUser.ProfileCategories.ModalCreateCategory(
    stateModal: MutableState<Boolean>,
    action: () -> Unit
) {
    if (stateModal.value) {
        Popup(
            popupPositionProvider = object : PopupPositionProvider {
                override fun calculatePosition(
                    anchorBounds: IntRect,
                    windowSize: IntSize,
                    layoutDirection: LayoutDirection,
                    popupContentSize: IntSize
                ): IntOffset = IntOffset.Zero
            },
            focusable = true,
            onDismissRequest = { stateModal.value = false },
            onKeyEvent = {
                if (it.type == KeyEventType.KeyDown && it.awtEventOrNull?.keyCode == KeyEvent.VK_ESCAPE) {
                    stateModal.value = false
                    true
                } else {
                    false
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.32f))
                    .pointerInput({ stateModal.value = false }) {
                        detectTapGestures(onPress = { stateModal.value = false })
                    },
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    contentColor = contentColorFor(MaterialTheme.colors.surface),
                    modifier = Modifier
                        .width(360.dp)
                        .height(200.dp)
                        .shadow(24.dp, RoundedCornerShape(4.dp))
                ) {
                    ModalCreateCategoryContent(action = action)
                }
            }
        }
    }
}

@Composable
private fun ModalCreateCategoryContent(action: () -> Unit) {
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
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Create a category",
            fontSize = 20.sp
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Form(
                state = stateForm.value,
                fields = listOf(
                    TextFieldAdvanced(
                        nameField = "title",
                        labelField = "Enter category title",
                        validators = listOf(
                            NotEmptyValidator()
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                )
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerHoverIcon(PointerIcon.Hand),
                onClick = action
            ) {
                Text("Create")
            }
        }
    }
}

/**
 * Basic button for opening a modal window for creating a category
 *
 * @param stateModal Modal window states for category creation
 */
@Composable
private fun ButtonCreateCategory(stateModal: MutableState<Boolean>) {
    Button(
        onClick = {
            stateModal.value = true
        },
        modifier = Modifier
            .fillMaxHeight()
            .pointerHoverIcon(PointerIcon.Hand)
    ) {
        Text("Create category")
    }
}
