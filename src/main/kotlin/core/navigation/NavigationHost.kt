package core.navigation

import androidx.compose.runtime.Composable

class NavigationHost(
    val navigationController: NavigationController,
    val contents: @Composable NavigationGraphBuilder.() -> Unit
) {

    @Composable
    fun build() {
        NavigationGraphBuilder().renderContents()
    }

    inner class NavigationGraphBuilder(
        val navigationControllerGraph: NavigationController = navigationController
    ) {
        @Composable
        fun renderContents() {
            contents(this)
        }
    }

}
