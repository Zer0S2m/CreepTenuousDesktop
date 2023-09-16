package com.zer0s2m.creeptenuous.desktop.ui.components.misc

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.zer0s2m.creeptenuous.desktop.common.enums.Resources
import com.zer0s2m.creeptenuous.desktop.common.enums.SizeComponents
import com.zer0s2m.creeptenuous.desktop.ui.components.base.BaseComponent
import com.zer0s2m.creeptenuous.desktop.ui.misc.float

/**
 * The modifier to be applied to the [Row]
 *
 * @param modifier The modifier to be applied to the [Row].
 */
class SwitchPanelDashboard(
    private val modifier: Modifier = Modifier
        .fillMaxHeight(SizeComponents.UPPER_BLOCK_LEFT_PANEL.float)
        .fillMaxWidth()
        .padding(12.dp, 0.dp)
) : BaseComponent {

    /**
     * Text used by accessibility services to describe what this image represents
     */
    private val contentDescriptionIconArrow: String = "Past state switch icon"

    /**
     * Optional Modifier for this [Icon]
     */
    private val baseModifierIcon: Modifier = Modifier
        .padding(0.dp)
        .width(28.dp)
        .height(28.dp)

    /**
     * Component rendering
     */
    @Composable
    override fun render() {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            IconButton(
                onClick = {
                    println(true)
                },
                modifier = Modifier
                    .padding(end = 8.dp)
                    .pointerHoverIcon(icon = PointerIcon.Hand)
            ) {
                Icon(
                    painter = painterResource(resourcePath = Resources.ICON_ARROW.path),
                    contentDescription = contentDescriptionIconArrow,
                    modifier = baseModifierIcon.rotate(90f)
                )
            }

            Text(
                text = "Folder 1",
                fontWeight = FontWeight.Medium
            )

            IconButton(
                onClick = {
                    println(true)
                },
                modifier = Modifier
                    .padding(end = 8.dp)
                    .pointerHoverIcon(icon = PointerIcon.Hand)
            ) {
                Icon(
                    painter = painterResource(resourcePath = Resources.ICON_ARROW.path),
                    contentDescription = contentDescriptionIconArrow,
                    modifier = baseModifierIcon.rotate(-90f)
                )
            }
        }
    }

}
