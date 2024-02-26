package com.zer0s2m.creeptenuous.desktop.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.zer0s2m.creeptenuous.desktop.common.enums.SizeComponents
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.float
import kotlinx.coroutines.CoroutineScope

/**
 * Autonomous component - directory tree state switch.
 *
 * @param title The text to be displayed.
 * @param modifier The modifier to be applied to the [Row].
 */
@Composable
fun SwitchPanelDashboard(
    title: MutableState<String>,
    modifier: Modifier = Modifier
        .fillMaxHeight(SizeComponents.UPPER_BLOCK_LEFT_PANEL.float)
        .fillMaxWidth()
        .padding(8.dp, 0.dp)
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = title.value,
            fontWeight = FontWeight.Medium
        )
    }
}

/**
 * Top panel render - search for file objects and avatar to open profile menu
 *
 * @param scaffoldState State for [Scaffold] composable component.
 * @param scope Defines a scope for new coroutines.
 * @param avatar User avatar URL.
 */
@Composable
fun TopPanelDashboard(
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    avatar: MutableState<String?> = mutableStateOf(null)
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .padding(0.dp, 12.dp, 12.dp, 12.dp)
        ) {
            FieldSearch(
                onClick = {
                    println("SEARCH")
                }
            ).render()
        }
        Column(
            modifier = Modifier
                .height(40.dp)
                .width(52.dp)
                .padding(end = 12.dp)
        ) {
            Avatar(
                stateScaffold = scaffoldState,
                scope = scope,
                avatar = avatar.value
            ).render()
        }
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