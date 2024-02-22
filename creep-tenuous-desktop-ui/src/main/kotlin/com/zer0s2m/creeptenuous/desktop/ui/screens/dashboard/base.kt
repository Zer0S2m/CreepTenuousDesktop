package com.zer0s2m.creeptenuous.desktop.ui.screens.dashboard

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.zer0s2m.creeptenuous.desktop.common.dto.BreadCrumbFileObject

/**
 * Base title for file object category
 *
 * @param text The text to be displayed
 * @param size Count objects
 */
@Composable
internal fun TitleCategoryFileObject(text: String, size: Int = 0): Unit = Text(
    text = "$text ($size)",
    fontWeight = FontWeight.SemiBold,
    color = Color.Black
)

internal fun getItemsBreadCrumbs(
    parents: Collection<String>,
    systemParents: Collection<String>
): MutableList<BreadCrumbFileObject> {
    val itemsBreadCrumbs: MutableList<BreadCrumbFileObject> = mutableListOf()
    parents.zip(systemParents) { parent, systemParent ->
        itemsBreadCrumbs.add(BreadCrumbFileObject(
            realName = parent,
            systemName = systemParent
        ))
    }
    return itemsBreadCrumbs
}