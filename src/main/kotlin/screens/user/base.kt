package screens.user

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import ui.components.misc.Avatar

/**
 * Basic card for user interaction in the system. Extends a component [Card]
 *
 * @param nameUser The text to be displayed
 * @param fractionBaseInfoUser ave the content fill [Modifier.fillMaxHeight] basic user information
 * @param content Map internal content
 */
@Composable
internal fun baseCardForItemCardUser(
    nameUser: String,
    fractionBaseInfoUser: Float = 0.8f,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .height(72.dp)
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fractionBaseInfoUser),
                verticalAlignment = Alignment.CenterVertically
            ) {
                baseInfoForItemCardUser(text = nameUser)
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                content()
            }
        }
    }
}

/**
 * Basic information about the user. Uses component [Avatar], [Text]
 *
 * @param text The text to be displayed
 */
@Composable
private fun baseInfoForItemCardUser(text: String) {
    Avatar(
        modifierIcon = Modifier
            .size(32.dp)
            .pointerHoverIcon(icon = PointerIcon.Default)
            .padding(0.dp)
    ).render()
    Text(
        text = text,
        modifier = Modifier
            .padding(start = 8.dp)
    )
}
