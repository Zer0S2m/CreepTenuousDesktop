package com.zer0s2m.creeptenuous.desktop.ui.components.modals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.zer0s2m.creeptenuous.desktop.ui.components.base.BaseModalRightSheetLayout

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