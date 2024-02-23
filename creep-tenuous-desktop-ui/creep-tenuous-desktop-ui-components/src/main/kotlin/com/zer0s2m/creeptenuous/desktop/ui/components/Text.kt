package com.zer0s2m.creeptenuous.desktop.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.Colors

/**
 * Base title for file object category.
 *
 * @param text The text to be displayed.
 * @param size Count objects.
 */
@Composable
fun TitleCategoryFileObject(text: String, size: Int = 0): Unit = Text(
    text = "$text ($size)",
    fontWeight = FontWeight.SemiBold,
    color = Color.Black
)

/**
 * Base title for the section collection menu.
 *
 * @param text The text to be displayed [Text].
 */
@Composable
fun TitleMenuSectionProfileUser(
    text: String,
    modifierText: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = Colors.TEXT.color,
            fontWeight = FontWeight.Medium,
            modifier = modifierText
        )
    }
}