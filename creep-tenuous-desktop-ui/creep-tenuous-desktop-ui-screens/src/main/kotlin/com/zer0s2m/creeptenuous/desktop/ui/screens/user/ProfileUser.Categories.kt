package com.zer0s2m.creeptenuous.desktop.ui.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zer0s2m.creeptenuous.desktop.common.dto.ConverterColor
import com.zer0s2m.creeptenuous.desktop.common.dto.UserCategory
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.common.utils.colorConvertHexToRgb
import com.zer0s2m.creeptenuous.desktop.core.validation.NotEmptyValidator
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveUser
import com.zer0s2m.creeptenuous.desktop.ui.components.BaseFormState
import com.zer0s2m.creeptenuous.desktop.ui.components.Form
import com.zer0s2m.creeptenuous.desktop.ui.components.FormState
import com.zer0s2m.creeptenuous.desktop.ui.components.IconButtonEdit
import com.zer0s2m.creeptenuous.desktop.ui.components.IconButtonRemove
import com.zer0s2m.creeptenuous.desktop.ui.components.InputSelectColor
import com.zer0s2m.creeptenuous.desktop.ui.components.ModalPopup
import com.zer0s2m.creeptenuous.desktop.ui.components.TextFieldAdvanced
import com.zer0s2m.creeptenuous.desktop.ui.components.DropdownMenuSelectColor
import com.zer0s2m.creeptenuous.desktop.ui.screens.ProfileUser
import kotlinx.coroutines.launch

/**
 * Set the color palette when editing or creating a custom category.
 */
val newColorForCategory: MutableState<String?> = mutableStateOf(null)

/**
 * Rendering part of the user profile screen [Screen.PROFILE_CATEGORY_SCREEN]
 */
@Composable
@Suppress("UnusedReceiverParameter")
fun ProfileUser.ProfileCategories.render() {
    val openModalCreateCategory: MutableState<Boolean> = remember { mutableStateOf(false) }
    val isEditCategory: MutableState<Boolean> = remember { mutableStateOf(false) }
    val currentUserCategory: MutableState<UserCategory> = remember {
        mutableStateOf(UserCategory(title = ""))
    }
    val currentIndexUserCategory: MutableState<Int> = remember { mutableStateOf(-1) }
    val listCategories: MutableList<UserCategory> = remember {
        ReactiveUser.customCategories.toMutableStateList()
    }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    currentIndexUserCategory.value = -1
                    currentUserCategory.value.title = ""
                    newColorForCategory.value = null
                    currentUserCategory.value.color = null
                    isEditCategory.value = false
                    openModalCreateCategory.value = true
                },
                modifier = Modifier
                    .fillMaxHeight()
                    .pointerHoverIcon(PointerIcon.Hand)
            ) {
                Text("Create category")
            }
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(listCategories.size) { index ->
                    ItemCategory(
                        userCategory = listCategories[index],
                        actionEdit = {
                            isEditCategory.value = true
                            currentIndexUserCategory.value = index
                            currentUserCategory.value = UserCategory(
                                id = listCategories[index].id,
                                title = listCategories[index].title,
                                color = listCategories[index].color,
                                colorId = listCategories[index].colorId
                            )
                            openModalCreateCategory.value = true
                            newColorForCategory.value = null
                        },
                        actionDelete = {
                            listCategories.removeAt(index)

                            scope.launch {
                                ReactiveUser.customCategories.removeAtReactive(index)
                            }
                        }
                    )
                }
            }
        }

        ModalCreateCategory(
            isExists = isEditCategory.value,
            stateModal = openModalCreateCategory,
            stateUserCategory = currentUserCategory,
            actionCreate = {
                if (stateForm.value.validateForm()) {
                    openModalCreateCategory.value = false

                    val dataForm = stateForm.value.getData()
                    val newCategory = UserCategory(
                        title = dataForm["title"].toString().trim(),
                        color = newColorForCategory.value,
                        colorId = ReactiveUser.findUserColor(newColorForCategory.value)?.id,
                    )

                    listCategories.add(newCategory)

                    scope.launch {
                        ReactiveUser.customCategories.addReactive(newCategory)
                    }

                    newColorForCategory.value = null
                }
            },
            actionEdit = {
                if (stateForm.value.validateForm()) {
                    openModalCreateCategory.value = false
                    isEditCategory.value = false

                    val dataForm = stateForm.value.getData()
                    val newCategory = UserCategory(
                        id = currentUserCategory.value.id,
                        title = dataForm["title"].toString().trim(),
                        color = newColorForCategory.value,
                        colorId = ReactiveUser.findUserColor(newColorForCategory.value)?.id,
                    )

                    listCategories[currentIndexUserCategory.value] = newCategory

                    scope.launch {
                        ReactiveUser.customCategories.setReactive(currentIndexUserCategory.value, newCategory)
                    }

                    currentUserCategory.value.title = ""
                    currentUserCategory.value.color = null
                    newColorForCategory.value = null
                }
            }
        )
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
 * @param userCategory User category information
 * @param actionEdit The lambda to be invoked when this icon is pressed - event edit
 * @param actionDelete The lambda to be invoked when this icon is pressed - event delete
 */
@Composable
private fun ItemCategory(
    userCategory: UserCategory,
    actionEdit: () -> Unit,
    actionDelete: () -> Unit
) {
    BaseCardItemGrid(height = 60.dp) {
        Text(text = userCategory.title)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (userCategory.color != null) {
                val convertedColor: ConverterColor = colorConvertHexToRgb(userCategory.color!!)
                Column(
                    modifier = Modifier
                        .padding(end = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(40.dp)
                            .background(
                                color = Color(
                                    red = convertedColor.red,
                                    green = convertedColor.green,
                                    blue = convertedColor.blue
                                ),
                                shape = RoundedCornerShape(4.dp)
                            )
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
            IconButtonEdit(onClick = actionEdit)
            Spacer(modifier = Modifier.width(8.dp))
            IconButtonRemove(onClick = actionDelete)
        }
    }
}

/**
 * Modal window for creating and editing a category
 *
 * @param isExists Does an object exist, depending on this, a certain action will occur
 * @param stateModal Modal window states for category creation
 * @param stateUserCategory The current state of the custom category
 * @param actionCreate [Button] click event create
 * @param actionEdit [Button] click event edit
 */
@Composable
private fun ModalCreateCategory(
    isExists: Boolean,
    stateModal: MutableState<Boolean>,
    stateUserCategory: MutableState<UserCategory>,
    actionCreate: () -> Unit,
    actionEdit: () -> Unit
) {
    ModalPopup(
        stateModal = stateModal,
        modifierLayout = Modifier
            .width(360.dp)
            .height(250.dp)
    ) {
        Text(
            text = if (isExists) "Edit a category" else "Create a category",
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            // TODO: Make form state for all kinds of components
            Form(
                state = stateForm.value,
                fields = listOf(
                    TextFieldAdvanced(
                        textField = stateUserCategory.value.title,
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
            Spacer(modifier = Modifier.height(12.dp))
            SelectColorForCategory(stateUserCategory = stateUserCategory)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.Center) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerHoverIcon(PointerIcon.Hand),
                onClick = if (isExists) actionEdit else actionCreate
            ) {
                Text(text = if (isExists) "Edit" else "Create")
            }
        }
    }
}

/**
 * component responsible for choosing a color for binding to a custom category.
 *
 * @param stateUserCategory The current state of the custom category.
 */
@Composable
private fun SelectColorForCategory(stateUserCategory: MutableState<UserCategory>) {
    val expandedState: MutableState<Boolean> = remember { mutableStateOf(false) }
    val currentColor: MutableState<Color?> = remember {
        mutableStateOf(Color(0, 0, 0))
    }
    val isSetColor: MutableState<Boolean> = remember { mutableStateOf(false) }

    if (stateUserCategory.value.color != null && !isSetColor.value) {
        val convertedColor: ConverterColor = colorConvertHexToRgb(stateUserCategory.value.color!!)
        isSetColor.value = true
        currentColor.value = Color(
            red = convertedColor.red,
            green = convertedColor.green,
            blue = convertedColor.blue
        )
        newColorForCategory.value = stateUserCategory.value.color
    }

    InputSelectColor(
        isSetColor = isSetColor,
        currentColor = currentColor,
        actionDelete = {
            stateUserCategory.value = UserCategory(
                id = stateUserCategory.value.id,
                color = null,
                title = stateUserCategory.value.title
            )
            currentColor.value = null
            isSetColor.value = false
            newColorForCategory.value = null
        },
        action = {
            expandedState.value = true
        }
    )
    DropdownMenuSelectColor(
        expandedState = expandedState,
        modifier = Modifier
            .width(baseWidthColumnSelectColor),
        action = { colorStr, color, _ ->
            expandedState.value = false
            currentColor.value = color
            isSetColor.value = true
            newColorForCategory.value = colorStr
        }
    )
}

/**
 * Width of color selection area from list
 */
@get:ReadOnlyComposable
private val baseWidthColumnSelectColor: Dp get() = 328.dp
