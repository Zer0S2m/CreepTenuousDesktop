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
        val navigationController: NavigationController = this@NavigationHost.navigationController
    ) {
        @Composable
        fun renderContents() {
            this@NavigationHost.contents(this)
        }
    }

}
