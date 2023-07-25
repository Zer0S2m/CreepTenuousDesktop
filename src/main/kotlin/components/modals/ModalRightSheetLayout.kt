package components.modals

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import components.base.BaseModalRightSheetLayout

/**
 * Modal right sheet. Extends a component [Scaffold]
 *
 * @param state State of this scaffold widget
 */
class ModalRightSheetLayout(
    override val state: ScaffoldState
) : BaseModalRightSheetLayout {

    /**
     * Component rendering
     */
    @Composable
    override fun render(content: @Composable () -> Unit) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Scaffold(
                scaffoldState = state,
                drawerContent = {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Menu 1")
                            Text("Menu 2")
                            Text("Menu 3")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    content()
                }
            }
        }
    }

}