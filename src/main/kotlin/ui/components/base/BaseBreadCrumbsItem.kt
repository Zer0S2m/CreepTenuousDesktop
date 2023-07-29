package ui.components.base

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable

/**
 * Base interface to implement the navigation element component for [BaseBreadCrumbs]
 */
interface BaseBreadCrumbsItem : BaseComponent {

    /**
     * Display text in the component
     */
    val text: String
        get() = ""

    /**
     * Set the spacing between navigation items, set to [BaseBreadCrumbs.separatorPadding]
     *
     * @param separator [BaseBreadCrumbs.separatorPadding]
     */
    @Composable
    fun setSeparatorPadding(separator: PaddingValues): BaseBreadCrumbsItem

}
