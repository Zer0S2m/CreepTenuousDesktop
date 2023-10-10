package com.zer0s2m.creeptenuous.desktop.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import com.zer0s2m.creeptenuous.desktop.common.enums.Resources

/**
 * IconButton is a clickable icon, used to represent actions.
 *
 * @param onClick The lambda to be invoked when this icon is pressed
 * @param contentDescription Text used by accessibility services to describe what this image represents.
 */
@Composable
internal fun IconButtonAdd(
    onClick: () -> Unit,
    contentDescription: String? = contentDescriptionIconAdd
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(resourcePath = Resources.ICON_ADD.path),
            contentDescription = contentDescription,
            modifier = Modifier
                .pointerHoverIcon(PointerIcon.Hand),
            tint = Color.Gray
        )
    }
}

/**
 * Text used by accessibility services to describe what this image represents.
 */
@get:ReadOnlyComposable
private val contentDescriptionIconAdd: String get() = "Add item"
