package components.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.navigation.NavigationController

/**
 * The base interface for implementing a dashboard that is divided into
 * two parts by a component [Row]
 */
interface BaseDashboard {

    /**
     * Navigation handler for changing the state of the current screen
     */
    var navigation: NavigationController?

    /**
     * Rendering the left side of the content
     */
    @Composable
    fun renderLeftContent()

    /**
     * Rendering the right side of the content
     */
    @Composable
    fun renderRightContent()

    /**
     * Splitter render for splitting the screen into two parts
     *
     * @param scaffoldState State of this scaffold widget.
     */
    @Composable
    fun render(
        scaffoldState: ScaffoldState = rememberScaffoldState()
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    renderLeftContent()
                    renderRightContent()
                }
            }
        }
    }

    /**
     * Splitter render for splitting the screen into two parts
     *
     * @param navigationController Navigation handler for changing the state of the current screen
     * @param scaffoldState State of this scaffold widget.
     */
    @Composable
    fun render(
        navigationController: NavigationController? = null,
        scaffoldState: ScaffoldState = rememberScaffoldState()
    ) {
        navigation = navigationController
        render(scaffoldState = scaffoldState)
    }

}