package com.zer0s2m.creeptenuous.desktop.ui.screens.user

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.Avatar

/**
 * Basic card for user interaction in the system. Extends a component [Card]
 *
 * @param nameUser Username
 * @param loginUser Login user
 * @param fractionBaseInfoUser ave the content fill [Modifier.fillMaxHeight] basic user information
 * @param avatar Avatar for user/
 * @param content Map internal content
 */
@Composable
internal fun BaseCardForItemCardUser(
    nameUser: String,
    loginUser: String,
    fractionBaseInfoUser: Float = 0.8f,
    avatar: String? = null,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp, 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fractionBaseInfoUser),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BaseInfoForItemCardUser(
                    name = nameUser,
                    login = loginUser,
                    avatar = avatar
                )
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
    Spacer(modifier = Modifier.height(12.dp))
}

/**
 * Basic information about the user. Uses component [Avatar], [Text]
 *
 * @param name Username.
 * @param login Login user.
 * @param avatar Avatar for user.
 */
@Composable
private fun BaseInfoForItemCardUser(
    name: String,
    login: String,
    avatar: String?
) {
    Avatar(
        modifierIcon = Modifier
            .size(32.dp)
            .pointerHoverIcon(icon = PointerIcon.Default)
            .padding(0.dp),
        avatar = avatar,
        enabled = false
    ).render()
    Text(
        text = "$name ($login)",
        modifier = Modifier
            .padding(start = 8.dp)
    )
}

/**
 * The base component for displaying a basic item grid card. Extends a component [Card]
 *
 * @param modifier The modifier to be applied to the [Row]
 * @param content Inner content of the component
 */
@Composable
internal fun BaseCardItemGrid(
    modifier: Modifier = Modifier
        .padding(8.dp),
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            content()
        }
    }
}
