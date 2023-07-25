package components.misc

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import components.base.BaseComponent
import enums.Resources
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Base class for creating a component - user avatar
 *
 * @param stateScaffold State of this scaffold widget
 * @param scope Defines a scope for new coroutines
 */
class Avatar(
    private val stateScaffold: ScaffoldState? = null,
    private val scope: CoroutineScope? = null
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
            modifier = Modifier
                .padding(0.dp)
        ) {
            Icon(
                painter = painterResource(resourcePath = Resources.ICON_USER_AVATAR.path),
                contentDescription = contentDescriptionAvatar,
                tint = Color.Gray,
                modifier = Modifier
                    .padding(0.dp)
                    .pointerHoverIcon(icon = PointerIcon.Hand)
            )
        }
    }

}