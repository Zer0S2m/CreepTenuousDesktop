package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import components.misc.Avatar
import components.screen.BaseDashboard
import core.errors.ComponentException
import core.navigation.NavigationController
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
                .fillMaxHeight(SizeComponents.UPPER_BLOCK_LEFT_PANEL.float)
                .fillMaxWidth(SizeComponents.LEFT_PANEL_DASHBOARD.float)
                .padding(12.dp, 0.dp)
        ) {
            renderUserAvatar()
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
            Text("user")
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
                    .height(32.dp)
            ).render()
            Text(
                text = "User name",
                modifier = Modifier
                    .padding(start = 8.dp)
            )
        }
    }

}