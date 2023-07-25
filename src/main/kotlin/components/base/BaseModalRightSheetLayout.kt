package components.base

import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState

/**
 * Modal right sheet. Extends a component [Scaffold]
 */
interface BaseModalRightSheetLayout : BaseComponent {

    /**
     * State of this scaffold widget
     */
    val state: ScaffoldState

}
