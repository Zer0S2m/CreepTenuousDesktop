package com.zer0s2m.creeptenuous.desktop.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.zer0s2m.creeptenuous.desktop.common.enums.SizeComponents
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.float

/**
 * Autonomous component - directory tree state switch.
 *
 * @param title The text to be displayed.
 * @param modifier The modifier to be applied to the [Row].
 * @param onClickLeft The lambda to be invoked when this icon is pressed. Left the button.
 * @param onClickRight The lambda to be invoked when this icon is pressed. Right the button.
 */
@Composable
fun SwitchPanelDashboard(
    title: MutableState<String>,
    modifier: Modifier = Modifier
        .fillMaxHeight(SizeComponents.UPPER_BLOCK_LEFT_PANEL.float)
        .fillMaxWidth()
        .padding(8.dp, 0.dp),
    onClickLeft: () -> Unit,
    onClickRight: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        IconButtonArrowLeft(
            onClick = onClickLeft,
            modifier = Modifier
                .padding(end = 8.dp)
                .pointerHoverIcon(icon = PointerIcon.Hand),
            modifierIcon = CommonPanel.baseModifierIcon
        )

        Text(
            text = title.value,
            fontWeight = FontWeight.Medium
        )

        IconButtonArrowRight(
            onClick = onClickRight,
            modifier = Modifier
                .padding(end = 8.dp)
                .pointerHoverIcon(icon = PointerIcon.Hand),
            modifierIcon = CommonPanel.baseModifierIcon
        )
    }
}

private object CommonPanel {

    /**
     * Optional Modifier for this [Icon].
     */
    val baseModifierIcon: Modifier = Modifier
        .padding(0.dp)
        .width(28.dp)
        .height(28.dp)

}