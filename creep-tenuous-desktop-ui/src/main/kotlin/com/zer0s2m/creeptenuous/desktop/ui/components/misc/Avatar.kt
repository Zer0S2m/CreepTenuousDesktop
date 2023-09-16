package com.zer0s2m.creeptenuous.desktop.ui.components.misc

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zer0s2m.creeptenuous.desktop.common.enums.Resources
import com.zer0s2m.creeptenuous.desktop.ui.components.base.BaseComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Base class for creating a component - user avatar
 *
 * @param stateScaffold State of this scaffold widget
 * @param scope Defines a scope for new coroutines
 * @param modifierIcon Optional [Modifier] for this [Icon]
 * @param modifierIconButton Optional [Modifier] for this [IconButton]
 * @param enabled whether this [IconButton] will handle input events and appear enabled for semantics purposes
 */
class Avatar(
    private val stateScaffold: ScaffoldState? = null,
    private val scope: CoroutineScope? = null,
    private var modifierIcon: Modifier = Modifier
        .padding(0.dp)
        .pointerHoverIcon(icon = PointerIcon.Hand),
    private val modifierIconButton: Modifier = Modifier
        .padding(0.dp),
    private val enabled: Boolean = true
) : BaseComponent {

    /**
     * Text used by accessibility services to describe what this image represents
     */
    private val contentDescriptionAvatar: String = "Basic user avatar"

    /**
     * Component rendering
     */
    @Composable
    override fun render() {
        if (!enabled) {
            modifierIcon = modifierIcon
                .pointerHoverIcon(icon = PointerIcon.Default)
        }

        IconButton(
            onClick = {
                if (stateScaffold != null && scope != null) {
                    if (stateScaffold.drawerState.isClosed) {
                        scope.launch {
                            stateScaffold.drawerState.open()
                        }
                    }
                }
            },
            modifier = modifierIconButton,
            enabled = enabled
        ) {
            Icon(
                painter = painterResource(resourcePath = Resources.ICON_USER_AVATAR.path),
                contentDescription = contentDescriptionAvatar,
                tint = Color.Gray,
                modifier = modifierIcon
            )
        }
    }

}
