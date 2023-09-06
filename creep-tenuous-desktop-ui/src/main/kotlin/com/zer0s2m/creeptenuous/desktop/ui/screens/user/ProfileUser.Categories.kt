package com.zer0s2m.creeptenuous.desktop.ui.screens.user

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.onClick
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zer0s2m.creeptenuous.desktop.common.dto.ConverterColor
import com.zer0s2m.creeptenuous.desktop.common.dto.UserCategory
import com.zer0s2m.creeptenuous.desktop.common.enums.Resources
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.common.utils.colorConvertHexToRgb
import com.zer0s2m.creeptenuous.desktop.core.validation.NotEmptyValidator
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveUser
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
    val isEditCategory: MutableState<Boolean> = remember { mutableStateOf(false) }
    val currentUserCategory: MutableState<UserCategory> = remember {
        mutableStateOf(UserCategory(title = ""))
    }
    val currentIndexUserCategory: MutableState<Int> = remember { mutableStateOf(-1) }
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
            ButtonCreateCategory {
                currentIndexUserCategory.value = -1
                currentUserCategory.value.title = ""
                isEditCategory.value = false
                openModalCreateCategory.value = true
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
                items(listCategories.size) { index ->
                    ItemCategory(
                        text = listCategories[index].title,
                        actionEdit = {
                            isEditCategory.value = true
                            currentIndexUserCategory.value = index
                            currentUserCategory.value = UserCategory(
                                id = listCategories[index].id,
                                title = listCategories[index].title
                            )
                            openModalCreateCategory.value = true
                        },
                        actionDelete = {
                            listCategories.removeAt(index)
                            ReactiveUser.customCategories.removeAtReactive(index)
                        }
                    )
                }
            }
        }

        ModalCreateCategory(
            isExists = isEditCategory.value,
            stateModal = openModalCreateCategory,
            stateUserCategory = currentUserCategory.value,
            actionCreate = {
                if (stateForm.value.validateForm()) {
                    openModalCreateCategory.value = false

                    val dataForm = stateForm.value.getData()
                    val data = UserCategory(
                        title = dataForm["title"].toString().trim()
                    )
                    val newCategory = UserCategory(title = data.title)

                    listCategories.add(newCategory)
                    ReactiveUser.customCategories.addReactive(newCategory)
                }
            },
            actionEdit = {
                if (stateForm.value.validateForm()) {
                    openModalCreateCategory.value = false
                    isEditCategory.value = false

                    val dataForm = stateForm.value.getData()
                    val newCategory = UserCategory(
                        id = currentUserCategory.value.id,
                        title = dataForm["title"].toString().trim()
                    )

                    listCategories[currentIndexUserCategory.value] = newCategory
                    ReactiveUser.customCategories.setReactive(currentIndexUserCategory.value, newCategory)

                    currentUserCategory.value.title = ""
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
 * @param text Display text
 * @param actionEdit The lambda to be invoked when this icon is pressed - event edit
 * @param actionDelete The lambda to be invoked when this icon is pressed - event delete
 */
@Composable
internal fun ItemCategory(
    text: String,
    actionEdit: () -> Unit,
    actionDelete: () -> Unit
) {
    BaseCardItemGrid {
        Text(
            text = text
        )

        Row {
            IconButtonEdit(onClick = actionEdit)
            IconButtonDelete(onClick = actionDelete)
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
internal fun ModalCreateCategory(
    isExists: Boolean,
    stateModal: MutableState<Boolean>,
    stateUserCategory: UserCategory,
    actionCreate: () -> Unit,
    actionEdit: () -> Unit
) {
    BaseModalPopup(
        stateModal = stateModal
    ) {
        Surface(
            contentColor = contentColorFor(MaterialTheme.colors.surface),
            modifier = Modifier
                .width(360.dp)
                .height(240.dp)
                .shadow(24.dp, RoundedCornerShape(4.dp))
        ) {
            ModalCreateCategoryContent(
                isExists = isExists,
                stateUserCategory = stateUserCategory,
                actionCreate = actionCreate,
                actionEdit = actionEdit
            )
        }
    }
}

/**
 * Content of the modal window for creating and editing a category
 *
 * @param isExists Does an object exist, depending on this, a certain action will occur
 * @param stateUserCategory The current state of the custom category
 * @param actionCreate [Button] click event create
 * @param actionEdit [Button] click event edit
 */
@Composable
private fun ModalCreateCategoryContent(
    isExists: Boolean,
    stateUserCategory: UserCategory,
    actionCreate: () -> Unit,
    actionEdit: () -> Unit
) {
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
            text = if (isExists) "Edit a category" else "Create a category",
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
                        textField = stateUserCategory.title,
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
            Spacer(
                modifier = Modifier
                    .padding(top = 12.dp)
            )
            SelectColorForCategory()
        }
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerHoverIcon(PointerIcon.Hand),
                onClick = if (isExists) actionEdit else actionCreate
            ) {
                Text(if (isExists) "Edit" else "Create")
            }
        }
    }
}

/**
 * Basic button for opening a modal window for creating a category
 *
 * @param action Will be called when the user clicks the [Button]
 */
@Composable
private fun ButtonCreateCategory(action: () -> Unit) {
    Button(
        onClick = action,
        modifier = Modifier
            .fillMaxHeight()
            .pointerHoverIcon(PointerIcon.Hand)
    ) {
        Text("Create category")
    }
}

/**
 * component responsible for choosing a color for binding to a custom category.
 */
@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun SelectColorForCategory() {
    val expandedState: MutableState<Boolean> = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .onClick {
                expandedState.value = true
            }
            .border(0.5.dp, MaterialTheme.colors.secondary, RoundedCornerShape(4.dp))
            .pointerHoverIcon(PointerIcon.Hand)
    ) {
        Row(
            modifier = Modifier
                .pointerHoverIcon(PointerIcon.Hand)
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Color...",
                color = Color(255, 255, 255, 160),
            )

            Icon(
                painter = painterResource(resourcePath = Resources.ICON_ARROW.path),
                contentDescription = contentDescriptionIconArrow,
                tint = Color.White,
                modifier = Modifier
                    .size(16.dp)
            )
        }

        DropdownMenuSelectColorForCategory(
            expandedState = expandedState
        )
    }
}

/**
 * The main component for choosing a color is a dropdown list.
 *
 * Extends a component [DropdownMenu]
 */
@Composable
private fun DropdownMenuSelectColorForCategory(
    expandedState: MutableState<Boolean>
) {
    DropdownMenu(
        expanded = expandedState.value,
        onDismissRequest = {
            expandedState.value = false
        },
        modifier = Modifier
            .width(baseWidthColumnSelectColor)
    ) {
        ReactiveUser.userColors.forEach {
            val convertedColor: ConverterColor = colorConvertHexToRgb(it.color)

            DropdownMenuItem(
                onClick = {
                    expandedState.value = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerHoverIcon(PointerIcon.Hand),
                contentPadding = PaddingValues(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(
                            red = convertedColor.red,
                            green = convertedColor.green,
                            blue = convertedColor.blue
                        ))
                        .padding(16.dp)
                )
            }
        }
    }
}

/**
 * Text used by accessibility services to describe what this image represents
 */
@get:ReadOnlyComposable
private val contentDescriptionIconArrow: String get() = "Open color list"

/**
 * Width of color selection area from list
 */
@get:ReadOnlyComposable
private val baseWidthColumnSelectColor: Dp get() = 328.dp
