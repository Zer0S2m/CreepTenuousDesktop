package com.zer0s2m.creeptenuous.desktop.ui.screens.user

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
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.*
import com.zer0s2m.creeptenuous.desktop.core.reactive.ReactiveUser
import com.zer0s2m.creeptenuous.desktop.core.validation.NotEmptyValidator
import com.zer0s2m.creeptenuous.desktop.common.dto.UserCategory
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.ui.components.base.BaseFormState
import com.zer0s2m.creeptenuous.desktop.ui.components.fields.TextFieldAdvanced
import com.zer0s2m.creeptenuous.desktop.ui.components.forms.Form
import com.zer0s2m.creeptenuous.desktop.ui.components.forms.FormState
import com.zer0s2m.creeptenuous.desktop.ui.screens.ProfileUser

/**
 * Rendering part of the user profile screen [Screen.PROFILE_CATEGORY_SCREEN]
 */
@Composable
fun ProfileUser.ProfileCategories.render() {
    val openModalCreateCategory: MutableState<Boolean> = remember { mutableStateOf(false) }

    val listCategories: MutableList<UserCategory> = remember {
        ReactiveUser.customCategories.toMutableStateList()
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
                    ItemCategory(listCategories[index].title) {
                        listCategories.removeAt(index)
                    }
                }
            }
        }

        ModalCreateCategory(stateModal = openModalCreateCategory) {
            if (stateForm.value.validateForm()) {
                openModalCreateCategory.value = false

                val dataForm = stateForm.value.getData()
                val data = UserCategory(
                    title = dataForm["title"].toString()
                )
                listCategories.add(UserCategory(title = data.title))
            }
        }
    }
}

/**
 * Form state for modal window create category
 */
@Stable
private val stateForm: MutableState<BaseFormState> = mutableStateOf(FormState())

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
    BaseCardItemGrid {
        Text(
            text = text
        )
        IconButtonDelete(onClick = action)
    }
}

/**
 * Modal window for creating a category
 *
 * @param stateModal Modal window states for category creation
 * @param action [Button] click event
 */
@Composable
internal fun ProfileUser.ProfileCategories.ModalCreateCategory(
    stateModal: MutableState<Boolean>,
    action: () -> Unit
) {
    BaseModalPopup(
        stateModal = stateModal
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
