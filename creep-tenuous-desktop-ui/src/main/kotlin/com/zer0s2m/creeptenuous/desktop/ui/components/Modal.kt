package com.zer0s2m.creeptenuous.desktop.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.awtEventOrNull
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.zer0s2m.creeptenuous.desktop.ui.components.base.BaseModalRightSheetLayout
import java.awt.event.KeyEvent

/**
 * Modal right sheet. Extends a component [Scaffold]
 *
 * @param state State of this scaffold widget
 * @param modifier Modifiers to decorate or add behavior to elements
 * @param modifierDrawerInternal Modifiers to decorate or add behavior to elements content of the Drawer
 * @param modifierDrawerExternal Modifiers to decorate or add behavior to elements for wrapping content
 * @param drawerContentColor Color of the content to use inside the drawer sheet
 */
class ModalRightSheetLayout(
    override val state: ScaffoldState,
    override val modifier: Modifier = Modifier,
    override val modifierDrawerInternal: Modifier = Modifier
        .fillMaxSize()
        .padding(12.dp),
    override val modifierDrawerExternal: Modifier = Modifier
        .background(Color.White),
    override val drawerContentColor: Color = Color.Black
) : BaseModalRightSheetLayout {

    /**
     * Component rendering
     *
     * @param drawerContent Content of the Drawer sheet that can be pulled from the left side
     * @param content Content of your screen
     */
    @Composable
    fun render(
        drawerContent: @Composable () -> Unit = {},
        content: @Composable () -> Unit = {}
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Scaffold(
                scaffoldState = state,
                modifier = modifier,
                drawerContentColor = drawerContentColor,
                drawerContent = {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        Box(
                            modifier = modifierDrawerExternal
                        ) {
                            Column(
                                modifier = modifierDrawerInternal
                            ) {
                                drawerContent()
                            }
                        }
                    }
                },
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    content()
                }
            }
        }
    }

}

/**
 * The base layout for the modal window. Extends a component [Popup].
 *
 * @param stateModal Modal window states.
 * @param modifierLayout Modifier to be applied to the layout corresponding to the [Surface].
 * @param modifierLayoutContent The modifier to be applied to the [Column].
 * @param content The inner content of the modal window.
 * @param onDismissRequest Executes when the user clicks outside the popup.
 */
@Composable
internal fun ModalPopup(
    stateModal: MutableState<Boolean>,
    modifierLayout: Modifier = Modifier
        .fillMaxSize(),
    modifierLayoutContent: Modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
    onDismissRequest: () -> Unit = {},
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
            onDismissRequest = {
                stateModal.value = false
                onDismissRequest()
            },
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
                Surface(
                    contentColor = contentColorFor(MaterialTheme.colors.surface),
                    modifier = modifierLayout
                        .shadow(24.dp, RoundedCornerShape(4.dp))
                ) {
                    Column(
                        modifier = modifierLayoutContent
                            .pointerInput({}) {
                                detectTapGestures(onPress = {
                                    // Workaround to disable clicks on Surface background
                                    // https://github.com/JetBrains/compose-jb/issues/2581
                                })
                            },
                    ) {
                        content()
                    }
                }

            }
        }
    }
}
