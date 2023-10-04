package com.zer0s2m.creeptenuous.desktop.ui.components.misc

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zer0s2m.creeptenuous.desktop.common.enums.Resources
import com.zer0s2m.creeptenuous.desktop.core.utils.loadImage
import com.zer0s2m.creeptenuous.desktop.ui.components.base.BaseComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image as ComposeImage

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
    private val enabled: Boolean = true,
    private val avatar: String? = null
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
            val isLoading: MutableState<Boolean> = remember { mutableStateOf(false) }
            val hasFail: MutableState<Boolean> = remember { mutableStateOf(false) }
            val imageBitmap: MutableState<ImageBitmap?> = remember { mutableStateOf(null) }

            if (avatar != null) {
                LaunchedEffect(avatar) {
                    isLoading.value = true
                    loadImage(avatar)
                        .onSuccess {
                            imageBitmap.value = it
                        }
                        .onFailure {
                            hasFail.value = true
                        }
                    isLoading.value = false
                }
                when {
                    isLoading.value -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colors.secondaryVariant,
                                modifier = Modifier
                                    .size(28.dp)
                            )
                        }
                    }
                    hasFail.value -> {

                    }
                    else -> {
                        imageBitmap.value?.let { bitmap ->
                            ComposeImage(
                                bitmap = bitmap,
                                contentDescription = "Avatar for user",
                                contentScale = ContentScale.Inside,
                                alpha = DefaultAlpha,
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                        }
                    }
                }
            } else {
                Icon(
                    painter = painterResource(resourcePath = Resources.ICON_USER_AVATAR.path),
                    contentDescription = contentDescriptionAvatar,
                    tint = Color.Gray,
                    modifier = modifierIcon
                )
            }
        }
    }

}
