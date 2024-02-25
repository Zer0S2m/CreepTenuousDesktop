package com.zer0s2m.creeptenuous.desktop.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zer0s2m.creeptenuous.desktop.common.enums.Resources
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.Colors

@get:ReadOnlyComposable
private val contentDescriptionIconArrow: String get() = "Bread crumb separator"

/**
 * Autonomous component - breadcrumbs (navigation).
 *
 * @param items List of components for drawing navigation items.
 * @param separatorPadding Spacing values applied internally between navigation items [BaseBreadCrumbsItem].
 * @param modifier The modifier to be applied to the [Column].
 */
@Composable
fun BreadCrumbs(
    items: Collection<BaseBreadCrumbsItem> = listOf(),
    separatorPadding: Dp = 8.dp,
    modifier: Modifier = Modifier
) {
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

/**
 * The standalone component is the breadcrumb element (navigation).
 *
 * @param text Display text in the component.
 * @param onClick Will be called when the user clicks the button.
 * @param modifier Modifier to be applied to the button [OutlinedButton].
 */
class BreadCrumbsItem(
    override val text: String = "",
    override val onClick: () -> Unit,
    private var modifier: Modifier = Modifier
) : BaseBreadCrumbsItem {

    /**
     * Component rendering
     */
    @Composable
    override fun render() {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier
                .pointerHoverIcon(icon = PointerIcon.Hand)
                .padding(end = 8.dp),
            contentPadding = PaddingValues(8.dp, 2.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = Color.White
            )
        ) {
            Text(
                text = text,
                modifier = Modifier
                    .background(Color.White),
                color = Colors.TEXT.color,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                fontSize = 12.sp
            )
        }
    }

    /**
     * Set the spacing between navigation items, set to spacing values applied internally.
     *
     * @param separator Spacing values applied internally.
     */
    @Composable
    override fun setSeparatorPadding(separator: PaddingValues): BaseBreadCrumbsItem {
        modifier = modifier.padding(separator)
        return this
    }

}
