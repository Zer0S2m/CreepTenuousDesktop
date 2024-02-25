package com.zer0s2m.creeptenuous.desktop.ui.screens.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * The base component for displaying a basic item grid card. Extends a component [Card].
 *
 * @param modifier The modifier to be applied to the [Row].
 * @param height Component height.
 * @param content Inner content of the component.
 */
@Composable
internal fun BaseCardItemGrid(
    modifier: Modifier = Modifier
        .padding(8.dp),
    height: Dp? = null,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (height != null) Modifier.height(height) else Modifier)
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
