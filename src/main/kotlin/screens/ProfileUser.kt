package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import components.misc.Avatar
import components.screen.BaseDashboard
import core.errors.ComponentException
import core.navigation.NavigationController
import enums.Colors
import enums.Sections
import enums.SizeComponents
import enums.float

/**
 * User profile screen
 *
 * @param navigation Handler for the navigation host for changing the current screen state
 */
class ProfileUser(override var navigation: NavigationController?) : BaseDashboard {

    /**
     * Splitter render for splitting the screen into two parts
     *
     * @param scaffoldState State of this scaffold widget.
     */
    @Composable
    override fun render(
        scaffoldState: ScaffoldState
    ) {
        super.render(scaffoldState = scaffoldState)

        if (navigation == null) {
            throw ComponentException(message = "To build a screen, you need a parameter [navigation]")
        }
    }

    /**
     * Rendering the left side of the content
     */
    @Composable
    override fun renderLeftContent() {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(SizeComponents.LEFT_PANEL_DASHBOARD.float)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(SizeComponents.UPPER_BLOCK_LEFT_PANEL.float)
                    .padding(baseHorizontalPadding, 0.dp)
            ) {
                renderUserAvatar()
            }
            Column(
                modifier = Modifier
                    .padding(top = 8.dp)
            ) {
                renderMenu()
            }
        }
    }

    /**
     * Render the right side of the content
     */
    @Composable
    override fun renderRightContent() {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {
            Text("user", color = Color.Black)
        }
    }

    /**
     * Render user icon and name
     */
    @Composable
    private fun renderUserAvatar() {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Avatar(
                modifierIcon = Modifier
                    .padding(0.dp)
                    .pointerHoverIcon(icon = PointerIcon.Hand)
                    .width(32.dp)
                    .height(32.dp),
                enabled = false
            ).render()
            Text(
                text = "User name",
                modifier = Modifier
                    .padding(start = 8.dp)
            )
        }
    }

    /**
     * Render main section for user settings
     */
    @Composable
    private fun renderMenu() {
        titleSectionInMenu(text = Sections.MAIN_PROFILE.title)
        Sections.MAIN_PROFILE.sections.forEach { item ->
            itemSectionInMenu(text = item)
        }

        titleSectionInMenu(text = Sections.USER_CONTROL.title)
        Sections.USER_CONTROL.sections.forEach { item ->
            itemSectionInMenu(text = item)
        }

        titleSectionInMenu(text = Sections.USER_CUSTOMIZATION.title)
        Sections.USER_CUSTOMIZATION.sections.forEach { item ->
            itemSectionInMenu(text = item)
        }
    }

}

/**
 * Base title for the section collection menu
 *
 * @param text The text to be displayed [Text]
 */
@Composable
internal fun titleSectionInMenu(text: String = "") {
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
            modifier = Modifier
                .padding(baseHorizontalPadding, 6.dp)
        )
    }
}

/**
 * Basic button element for screen navigation. Extends a component [Button]
 *
 * @param text The text to be displayed [Text]
 * @param enabled Controls the enabled state of the surface [Surface]
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun itemSectionInMenu(
    text: String = "",
    enabled: Boolean = true
) {
    val colors: ButtonColors = ButtonDefaults.outlinedButtonColors()
    val contentColor by colors.contentColor(enabled)
    val modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(48.dp)
        .pointerHoverIcon(icon = PointerIcon.Hand)

    Surface(
        onClick = {
            println(true)
        },
        modifier = modifier.semantics { role = Role.Button },
        enabled = enabled,
        shape = RoundedCornerShape(0.dp),
        color = colors.backgroundColor(enabled).value,
        contentColor = contentColor.copy(alpha = 1f),
        border = null,
        elevation = 0.dp,
        interactionSource = remember { MutableInteractionSource() },
    ) {
        CompositionLocalProvider(LocalContentAlpha provides contentColor.alpha) {
            ProvideTextStyle(
                value = MaterialTheme.typography.button
            ) {
                Row(
                    Modifier
                        .defaultMinSize(
                            minWidth = ButtonDefaults.MinWidth,
                            minHeight = ButtonDefaults.MinHeight
                        )
                        .padding(PaddingValues(
                            horizontal = baseHorizontalPadding,
                            vertical = 8.dp
                        )),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        Text(
                            text = text,
                            color = Color.White
                        )
                    }
                )
            }
        }
    }
}

/**
 * Basic horizontal padding for building the left side of the panel
 */
internal val baseHorizontalPadding: Dp get() = 12.dp
