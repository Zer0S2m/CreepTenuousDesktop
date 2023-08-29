package com.zer0s2m.creeptenuous.desktop.ui.components.misc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zer0s2m.creeptenuous.desktop.ui.components.base.BaseBreadCrumbs
import com.zer0s2m.creeptenuous.desktop.ui.components.base.BaseBreadCrumbsItem
import com.zer0s2m.creeptenuous.desktop.ui.misc.Colors

/**
 * Basic navigation element component for [BaseBreadCrumbs]
 *
 * @param text Display text in the component
 * @param modifier Modifier to be applied to the button [OutlinedButton]
 */
class BreadCrumbsItem(
    override val text: String = "",
    private var modifier: Modifier = Modifier
) : BaseBreadCrumbsItem {

    /**
     * Component rendering
     */
    @Composable
    override fun render() {
        OutlinedButton(
            onClick = {
                println(true)
            },
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
     * Set the spacing between navigation items, set to [BaseBreadCrumbs.separatorPadding]
     *
     * @param separator [BaseBreadCrumbs.separatorPadding]
     */
    @Composable
    override fun setSeparatorPadding(separator: PaddingValues): BaseBreadCrumbsItem {
        modifier = modifier
            .padding(separator)
        return this
    }

}