package components.misc

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import components.base.BaseComponent
import enums.Resources

/**
 * Base class for creating a component - user avatar
 */
class Avatar : BaseComponent {

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
                println(true)
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