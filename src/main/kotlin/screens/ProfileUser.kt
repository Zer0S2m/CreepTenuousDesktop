package screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import components.screen.BaseDashboard
import core.navigation.NavigationController

/**
 * User profile screen
 */
class ProfileUser : BaseDashboard {

    /**
     * Render user profile screen
     *
     * @param navigationController Handler for the navigation host for changing the current screen state
     */
    @Composable
    fun render(
        navigationController: NavigationController
    ) {
        render()
    }

    /**
     * Rendering the left side of the content
     */
    @Composable
    override fun renderLeftContent() {

    }

    /**
     * Render the right side of the content
     */
    @Composable
    override fun renderRightContent() {

    }

}