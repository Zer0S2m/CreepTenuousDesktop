package com.zer0s2m.creeptenuous.desktop.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.zer0s2m.creeptenuous.desktop.common.enums.Resources

/**
 * Component for rendering a component that will subsequently call a drop-down list.
 *
 * @param isDelete Whether to show the delete object button.
 * @param actionDelete The lambda to be invoked when this icon is pressed.
 * @param onClick Configure component to receive clicks.
 * @param content Content layout.
 */
@Composable
@OptIn(ExperimentalFoundationApi::class)
internal fun InputSelect(
    isDelete: Boolean = true,
    actionDelete: () -> Unit = {},
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .semantics { role = Role.Button }
            .onClick {
                onClick()
            }
            .border(0.5.dp, MaterialTheme.colors.secondary, RoundedCornerShape(4.dp))
            .pointerHoverIcon(PointerIcon.Hand)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .pointerHoverIcon(PointerIcon.Hand),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            content()

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (isDelete) {
                    IconButtonRemove(onClick = actionDelete)
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Icon(
                    painter = painterResource(resourcePath = Resources.ICON_ARROW.path),
                    contentDescription = "Open select property",
                    tint = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .size(20.dp)
                )
            }
        }
    }
}
