package com.zer0s2m.creeptenuous.desktop.ui.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.zer0s2m.creeptenuous.desktop.common.enums.Resources
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.Avatar
import java.awt.event.KeyEvent

/**
 * Basic card for user interaction in the system. Extends a component [Card]
 *
 * @param nameUser Username
 * @param loginUser Login user
 * @param fractionBaseInfoUser ave the content fill [Modifier.fillMaxHeight] basic user information
 * @param content Map internal content
 */
@Composable
internal fun BaseCardForItemCardUser(
    nameUser: String,
    loginUser: String,
    fractionBaseInfoUser: Float = 0.8f,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .height(72.dp)
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fractionBaseInfoUser),
                verticalAlignment = Alignment.CenterVertically
            ) {
                baseInfoForItemCardUser(name = nameUser, login = loginUser)
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                content()
            }
        }
    }
}

/**
 * Basic information about the user. Uses component [Avatar], [Text]
 *
 * @param name Username
 * @param login Login user
 */
@Composable
private fun baseInfoForItemCardUser(name: String, login: String) {
    Avatar(
        modifierIcon = Modifier
            .size(32.dp)
            .pointerHoverIcon(icon = PointerIcon.Default)
            .padding(0.dp)
    ).render()
    Text(
        text = "$name ($login)",
        modifier = Modifier
            .padding(start = 8.dp)
    )
}

/**
 * The base layout for the modal window. Extends a component [Popup]
 *
 * @param stateModal Modal window states for category creation
 * @param content The inner content of the modal window
 */
@Composable
internal fun BaseModalPopup(
    stateModal: MutableState<Boolean>,
    content: @Composable () -> Unit
) {
    if (stateModal.value) {
        Popup(popupPositionProvider = object : PopupPositionProvider {
            override fun calculatePosition(
                anchorBounds: IntRect,
                windowSize: IntSize,
                layoutDirection: LayoutDirection,
                popupContentSize: IntSize
            ): IntOffset = IntOffset.Zero
        },
            onDismissRequest = { stateModal.value = false },
            properties = PopupProperties(focusable = true), onPreviewKeyEvent = { false }, onKeyEvent = {
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
                content()
            }
        }
    }
}

/**
 * Text used by accessibility services to describe what this image represents
 */
@Stable
internal val contentDescriptionDelete: String get() = "Delete item icon"

/**
 * Text used by accessibility services to describe what this image represents
 */
@Stable
internal val contentDescriptionEdit: String get() = "Edit item icon"

/**
 * The base component for displaying a basic item grid card. Extends a component [Card]
 *
 * @param modifier The modifier to be applied to the [Row]
 * @param content Inner content of the component
 */
@Composable
internal fun BaseCardItemGrid(
    modifier: Modifier = Modifier
        .padding(8.dp),
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            content()
        }
    }
}

/**
 * Base element removal component. Extends a component [IconButton]
 *
 * @param onClick The lambda to be invoked when this icon is pressed [IconButton]
 */
@Composable
internal fun IconButtonDelete(onClick: () -> Unit) {
    BaseIconButton(
        resourcePath = Resources.ICON_DELETE.path,
        contentDescription = contentDescriptionDelete,
        tint = Color.Red,
        onClick = onClick
    )
}

/**
 * Base element edit component. Extends a component [IconButton]
 *
 * @param onClick The lambda to be invoked when this icon is pressed [IconButton]
 */
@Composable
internal fun IconButtonEdit(onClick: () -> Unit) {
    BaseIconButton(
        resourcePath = Resources.ICON_EDIT.path,
        contentDescription = contentDescriptionEdit,
        onClick = onClick
    )
}

/**
 * The basic component for drawing an icon that performs some [onClick]
 *
 * @param resourcePath Painter to draw inside this Icon
 * @param contentDescription Text used by accessibility services to describe what this icon represents.
 * @param tint Tint to be applied to painter.
 * @param enabled Controls the enabled state.
 * @param interactionSource [MutableInteractionSource] that will be used to dispatch [PressInteraction.Press] when this clickable is pressed.
 * @param onClick The lambda to be invoked when this icon is pressed [IconButton]
 */
@Composable
private fun BaseIconButton(
    resourcePath: String,
    contentDescription: String? = null,
    tint: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(32.dp)
            .clickable(
                onClick = onClick,
                enabled = enabled,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = rememberRipple(bounded = false, radius = 20.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        val contentAlpha = if (enabled) LocalContentAlpha.current else ContentAlpha.disabled
        CompositionLocalProvider(LocalContentAlpha provides contentAlpha) {
            Icon(
                painter = painterResource(resourcePath = resourcePath),
                contentDescription = contentDescription,
                modifier = Modifier
                    .size(24.dp)
                    .pointerHoverIcon(PointerIcon.Hand),
                tint = tint
            )
        }
    }
}
