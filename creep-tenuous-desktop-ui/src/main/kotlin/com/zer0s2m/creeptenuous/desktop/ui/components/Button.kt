package com.zer0s2m.creeptenuous.desktop.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zer0s2m.creeptenuous.desktop.common.enums.Resources

/**
 * Text used by accessibility services to describe what this image represents.
 */
@get:ReadOnlyComposable
private val contentDescriptionIconAdd: String get() = "Add item"

/**
 * Text used by accessibility services to describe what this image represents.
 */
@get:ReadOnlyComposable
private val contentDescriptionIconRemove: String get() = "Remove item"

/**
 * Text used by accessibility services to describe what this image represents.
 */
@get:ReadOnlyComposable
private val contentDescriptionIconEdit: String get() = "Edit item"

@get:ReadOnlyComposable
private val baseComponentForButton: Modifier get() = Modifier
    .pointerHoverIcon(PointerIcon.Hand)
    .size(24.dp)

/**
 * IconButton is a clickable icon, used to represent actions. Event - create.
 *
 * @param onClick The lambda to be invoked when this icon is pressed.
 * @param modifier Modifier to be applied to the button.
 * @param contentDescription Text used by accessibility services to describe what this image represents.
 */
@Composable
internal fun IconButtonAdd(
    onClick: () -> Unit,
    modifier: Modifier = baseComponentForButton,
    contentDescription: String? = contentDescriptionIconAdd
) {
    OutlinedButton(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        onClick = onClick,
        contentPadding = PaddingValues(4.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = MaterialTheme.colors.secondary
        )
    ) {
        Icon(
            painter = painterResource(resourcePath = Resources.ICON_ADD.path),
            contentDescription = contentDescription,
            modifier = Modifier
                .fillMaxSize(),
            tint = Color.White
        )
    }
}

/**
 * IconButton is a clickable icon, used to represent actions. Event - edit.
 *
 * @param onClick The lambda to be invoked when this icon is pressed.
 * @param modifier Modifier to be applied to the button.
 * @param contentDescription Text used by accessibility services to describe what this image represents.
 */
@Composable
internal fun IconButtonEdit(
    onClick: () -> Unit,
    modifier: Modifier = baseComponentForButton,
    contentDescription: String? = contentDescriptionIconAdd
) {
    OutlinedButton(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        onClick = onClick,
        contentPadding = PaddingValues(4.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = MaterialTheme.colors.secondary
        )
    ) {
        Icon(
            painter = painterResource(resourcePath = Resources.ICON_EDIT.path),
            contentDescription = contentDescription,
            modifier = Modifier
                .fillMaxSize(),
            tint = Color.White
        )
    }
}

/**
 * IconButton is a clickable icon, used to represent actions. Event - delete.
 *
 * @param onClick The lambda to be invoked when this icon is pressed.
 * @param modifier Modifier to be applied to the button.
 * @param contentDescription Text used by accessibility services to describe what this image represents.
 */
@Composable
internal fun IconButtonRemove(
    onClick: () -> Unit,
    modifier: Modifier = baseComponentForButton,
    contentDescription: String? = contentDescriptionIconEdit
) {
    OutlinedButton(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        onClick = onClick,
        contentPadding = PaddingValues(4.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.Red
        )
    ) {
        Icon(
            painter = painterResource(resourcePath = Resources.ICON_ADD.path),
            contentDescription = contentDescription,
            modifier = Modifier
                .rotate(45f)
                .fillMaxSize(),
            tint = Color.White
        )
    }
}
