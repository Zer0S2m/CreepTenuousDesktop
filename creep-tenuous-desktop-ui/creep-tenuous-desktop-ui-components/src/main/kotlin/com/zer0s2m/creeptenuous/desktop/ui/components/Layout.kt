package com.zer0s2m.creeptenuous.desktop.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zer0s2m.creeptenuous.desktop.common.dto.UserCategory
import com.zer0s2m.creeptenuous.desktop.common.enums.Resources

/**
 * Component responsible for minimal information about the user category.
 *
 * @param userCategory User category information.
 */
@Composable
fun LayoutUserCategory(userCategory: UserCategory) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (userCategory.color != null) {
            CircleCategoryBox(userCategory.color!!, 16.dp)
        }
        Text(
            text = userCategory.title,
            modifier = if (userCategory.color != null) Modifier
                .padding(start = 8.dp) else Modifier
        )
    }
}

/**
 * Field action call layout - delete and open.
 *
 * @param actionDelete Configure component to receive clicks [Icon] (action delete).
 * @param isDelete Whether to show the delete object button.
 */
@Composable
fun LayoutDeleteAndOpenInputSelect(
    actionDelete: () -> Unit = {},
    isDelete: Boolean = true
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (isDelete) {
            IconButtonRemove(
                onClick = actionDelete
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Icon(
            painter = painterResource(resourcePath = Resources.ICON_ARROW.path),
            contentDescription = "Open menu",
            tint = MaterialTheme.colors.secondary,
            modifier = Modifier.size(20.dp)
        )
    }
}

