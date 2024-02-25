package com.zer0s2m.creeptenuous.desktop.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zer0s2m.creeptenuous.desktop.common.enums.Resources

/**
 * IconButton is a clickable icon, used to represent actions. Event - create.
 *
 * @param onClick The lambda to be invoked when this icon is pressed.
 * @param modifier Modifier to be applied to the button.
 * @param contentDescription Text used by accessibility services to describe what this image represents.
 */
@Composable
fun IconButtonAdd(
    onClick: () -> Unit,
    modifier: Modifier = CommonButtons.baseComponentForButton,
    contentDescription: String? = CommonButtons.contentDescriptionIconAdd
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
 * IconButton is a clickable icon, used to represent actions. Event - upload.
 *
 * @param onClick The lambda to be invoked when this icon is pressed.
 * @param modifier Modifier to be applied to the button.
 * @param contentDescription Text used by accessibility services to describe what this image represents.
 */
@Composable
fun IconButtonUpload(
    onClick: () -> Unit,
    modifier: Modifier = CommonButtons.baseComponentForButton,
    contentDescription: String? = CommonButtons.contentDescriptionIconUpload
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
            painter = painterResource(resourcePath = Resources.ICON_UPLOAD.path),
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
fun IconButtonEdit(
    onClick: () -> Unit,
    modifier: Modifier = CommonButtons.baseComponentForButton,
    contentDescription: String? = CommonButtons.contentDescriptionIconUpload
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
fun IconButtonRemove(
    onClick: () -> Unit,
    modifier: Modifier = CommonButtons.baseComponentForButton,
    contentDescription: String? = CommonButtons.contentDescriptionIconEdit
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

/**
 * An autonomous component is a button with a magnifying glass (search).
 *
 * @param onClick The lambda to be invoked when this icon is pressed.
 * @param modifier Optional [Modifier] for this [IconButton].
 * @param contentDescription Text used by accessibility services to describe what this icon represents.
 * @param widthIcon Icon width.
 * @param heightIcon Icon height.
 */
@Composable
fun IconButtonSearch(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = CommonButtons.contentDescriptionIconSearch,
    widthIcon: Dp = 28.dp,
    heightIcon: Dp = 28.dp
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(resourcePath = Resources.ICON_SEARCH.path),
            contentDescription = contentDescription,
            modifier = Modifier
                .padding(0.dp)
                .width(widthIcon)
                .height(heightIcon)
        )
    }
}

/**
 * A standalone component is an arrow button. Left arrow.
 *
 * @param onClick The lambda to be invoked when this icon is pressed.
 * @param modifier Optional [Modifier] for this [IconButton].
 * @param modifierIcon optional [Modifier] for this [Icon].
 * @param contentDescription Text used by accessibility services to describe what this icon represents.
 */
@Composable
fun IconButtonArrowLeft(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    modifierIcon: Modifier = Modifier,
    contentDescription: String = CommonButtons.contentDescriptionIconArrow
) {
    IconButtonArrow(
        onClick = onClick,
        modifier = modifier,
        modifierIcon = modifierIcon,
        rotate = 90f,
        contentDescription = contentDescription
    )
}

/**
 * A standalone component is an arrow button. Right arrow.
 *
 * @param onClick The lambda to be invoked when this icon is pressed.
 * @param modifier Optional [Modifier] for this [IconButton].
 * @param modifierIcon optional [Modifier] for this [Icon].
 * @param contentDescription Text used by accessibility services to describe what this icon represents.
 */
@Composable
fun IconButtonArrowRight(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    modifierIcon: Modifier = Modifier,
    contentDescription: String = CommonButtons.contentDescriptionIconArrow
) {
    IconButtonArrow(
        onClick = onClick,
        modifier = modifier,
        modifierIcon = modifierIcon,
        rotate = -90f,
        contentDescription = contentDescription
    )
}

/**
 * A standalone component is an arrow button.
 *
 * @param onClick The lambda to be invoked when this icon is pressed.
 * @param modifier Optional [Modifier] for this [IconButton].
 * @param modifierIcon optional [Modifier] for this [Icon].
 * @param rotate Sets the degrees the view is rotated around the center of the composable.
 * @param contentDescription Text used by accessibility services to describe what this icon represents.
 */
@Composable
private fun IconButtonArrow(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    modifierIcon: Modifier = Modifier,
    rotate: Float,
    contentDescription: String
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(resourcePath = Resources.ICON_ARROW.path),
            contentDescription = contentDescription,
            modifier = modifierIcon.rotate(rotate)
        )
    }
}

/**
 * Stand-alone component - element block button.
 *
 * @param onClick The lambda to be invoked when this icon is pressed.
 * @param modifier Optional [Modifier] for this [IconButton].
 * @param modifierIcon optional [Modifier] for this [Icon].
 */
@Composable
fun IconButtonBlock(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    modifierIcon: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(resourcePath = Resources.ICON_BLOCK.path),
            contentDescription = CommonButtons.contentDescriptionIconBlock,
            modifier = modifierIcon,
            tint = Color.Red
        )
    }
}

/**
 * A standalone component is an element unlock button.
 *
 * @param onClick The lambda to be invoked when this icon is pressed.
 * @param modifier Optional [Modifier] for this [IconButton].
 * @param modifierIcon optional [Modifier] for this [Icon].
 */
@Composable
fun IconButtonUnblock(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    modifierIcon: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(resourcePath = Resources.ICON_UNBLOCK.path),
            contentDescription = CommonButtons.contentDescriptionIconUnblock,
            modifier = modifierIcon,
            tint = Color.Green
        )
    }
}

private object CommonButtons {

    /**
     * Text used by accessibility services to describe what this image represents.
     */
    @get:ReadOnlyComposable
    val contentDescriptionIconAdd: String get() = "Add item"

    /**
     * Text used by accessibility services to describe what this image represents.
     */
    @get:ReadOnlyComposable
    val contentDescriptionIconRemove: String get() = "Remove item"

    /**
     * Text used by accessibility services to describe what this image represents.
     */
    @get:ReadOnlyComposable
    val contentDescriptionIconEdit: String get() = "Edit item"

    /**
     * Text used by accessibility services to describe what this image represents
     */
    @get:ReadOnlyComposable
    val contentDescriptionIconSearch: String get() = "Search icon"

    /**
     * Text used by accessibility services to describe what this image represents
     */
    @get:ReadOnlyComposable
    val contentDescriptionIconArrow: String get() = "Past state switch icon"

    /**
     * Text used by accessibility services to describe what this image represents
     */
    @get:ReadOnlyComposable
    val contentDescriptionIconBlock: String get() = "Lock icon"

    /**
     * Text used by accessibility services to describe what this image represents
     */
    @get:ReadOnlyComposable
    val contentDescriptionIconUnblock: String get() = "Unlock icon"

    /**
     * Text used by accessibility services to describe what this image represents.
     */
    @get:ReadOnlyComposable
    val contentDescriptionIconUpload: String get() = "Upload item"

    @get:ReadOnlyComposable
    val baseComponentForButton: Modifier
        get() = Modifier
            .pointerHoverIcon(PointerIcon.Hand)
            .size(24.dp)

}