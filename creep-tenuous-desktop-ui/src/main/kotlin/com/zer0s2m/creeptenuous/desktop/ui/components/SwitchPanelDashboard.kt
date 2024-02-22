package com.zer0s2m.creeptenuous.desktop.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
        .padding(8.dp, 0.dp),
    private val title: MutableState<String>,
    private val onClickLeft: () -> Unit,
    private val onClickRight: () -> Unit
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
                onClick = onClickLeft,
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
                text = title.value,
                fontWeight = FontWeight.Medium
            )

            IconButton(
                onClick = onClickRight,
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
