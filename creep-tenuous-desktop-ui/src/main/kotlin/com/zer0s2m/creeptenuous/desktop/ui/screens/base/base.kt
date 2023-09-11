package com.zer0s2m.creeptenuous.desktop.ui.screens.base

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.awtEventOrNull
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
import androidx.compose.ui.window.PopupProperties
import com.zer0s2m.creeptenuous.desktop.common.dto.ConverterColor
import com.zer0s2m.creeptenuous.desktop.common.enums.Resources
import com.zer0s2m.creeptenuous.desktop.common.utils.colorConvertHexToRgb
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveUser
import java.awt.event.KeyEvent

/**
 * The base layout for the modal window. Extends a component [Popup]
 *
 * @param stateModal Modal window states
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
 * The component responsible for displaying the selected color palette from the component [DropdownMenuSelectColor].
 *
 * @param isSetColor Status: Is any color palette installed.
 * @param currentColor Current set color
 * @param action Configure component to receive clicks [Row].
 */
@Composable
@OptIn(ExperimentalFoundationApi::class)
internal fun InputSelectColor(
    isSetColor: MutableState<Boolean>,
    currentColor: MutableState<Color>,
    action: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .onClick {
                action()
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
            if (!isSetColor.value) {
                Text(
                    text = "Color...",
                    color = Color(255, 255, 255, 160),
                )
            } else {
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(24.dp)
                        .background(currentColor.value, RoundedCornerShape(4.dp))
                )
            }

            Icon(
                painter = painterResource(resourcePath = Resources.ICON_ARROW.path),
                contentDescription = contentDescriptionIconArrow,
                tint = Color.White,
                modifier = Modifier
                    .size(16.dp)
            )
        }
    }
}

/**
 * The main component for choosing a color is a dropdown list.
 *
 * Extends a component [DropdownMenu]
 *
 * @param expandedState Whether the menu is currently open and visible to the user
 * @param modifier Modifiers to decorate or add behavior to elements
 * @param action Call an action when an element is clicked [DropdownMenuItem]
 */
@Composable
internal fun DropdownMenuSelectColor(
    expandedState: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    action: (String, Color) -> Unit
) {
    DropdownMenu(
        expanded = expandedState.value,
        onDismissRequest = {
            expandedState.value = false
        },
        modifier = modifier
    ) {
        ReactiveUser.userColors.forEach {
            val convertedColor: ConverterColor = colorConvertHexToRgb(it.color)
            val color = Color(
                red = convertedColor.red,
                green = convertedColor.green,
                blue = convertedColor.blue
            )

            DropdownMenuItem(
                onClick = {
                    action(it.color, color)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerHoverIcon(PointerIcon.Hand),
                contentPadding = PaddingValues(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color, RoundedCornerShape(4.dp))
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
