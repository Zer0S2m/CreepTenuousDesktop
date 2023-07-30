package screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.awtEventOrNull
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import ui.components.misc.Avatar
import java.awt.event.KeyEvent

/**
 * Basic card for user interaction in the system. Extends a component [Card]
 *
 * @param nameUser The text to be displayed
 * @param fractionBaseInfoUser ave the content fill [Modifier.fillMaxHeight] basic user information
 * @param content Map internal content
 */
@Composable
internal fun BaseCardForItemCardUser(
    nameUser: String,
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
                baseInfoForItemCardUser(text = nameUser)
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
 * @param text The text to be displayed
 */
@Composable
private fun baseInfoForItemCardUser(text: String) {
    Avatar(
        modifierIcon = Modifier
            .size(32.dp)
            .pointerHoverIcon(icon = PointerIcon.Default)
            .padding(0.dp)
    ).render()
    Text(
        text = text,
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
