package com.zer0s2m.creeptenuous.desktop.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zer0s2m.creeptenuous.desktop.common.enums.Resources
import com.zer0s2m.creeptenuous.desktop.ui.components.base.BaseBreadCrumbs
import com.zer0s2m.creeptenuous.desktop.ui.components.base.BaseBreadCrumbsItem

/**
 * Component Class - Breadcrumbs
 *
 * @param items List of components for drawing navigation items
 * @param separatorPadding Spacing values applied internally between navigation items [BaseBreadCrumbsItem]
 * @param modifier The modifier to be applied to the [Column]
 */
class BreadCrumbs(
    override val items: Collection<BaseBreadCrumbsItem> = listOf(),
    override val separatorPadding: Dp = 8.dp,
    private val modifier: Modifier = Modifier
) : BaseBreadCrumbs {

    private val contentDescriptionIconArrow: String = ""

    /**
     * Component rendering
     */
    @Composable
    override fun render() {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = modifier
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEachIndexed { index, item ->
                    if (index == 0) {
                        item
                            .setSeparatorPadding(separator = PaddingValues())
                            .render()
                    } else {
                        item
                            .setSeparatorPadding(
                                separator = PaddingValues(start = separatorPadding)
                            )
                            .render()
                    }

                    if (items.size - 1 != index) {
                        Icon(
                            painter = painterResource(resourcePath = Resources.ICON_ARROW.path),
                            contentDescription = contentDescriptionIconArrow,
                            modifier = Modifier
                                .rotate(-90f)
                                .size(16.dp),
                            tint = Color.Black
                        )
                    }
                }
            }
        }
    }

}
