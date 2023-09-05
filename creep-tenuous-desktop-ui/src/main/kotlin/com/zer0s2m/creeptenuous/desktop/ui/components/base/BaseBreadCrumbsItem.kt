package com.zer0s2m.creeptenuous.desktop.ui.components.base

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
     * Will be called when the user clicks the button
     */
    val action: () -> Unit

    /**
     * Set the spacing between navigation items, set to [BaseBreadCrumbs.separatorPadding]
     *
     * @param separator [BaseBreadCrumbs.separatorPadding]
     */
    @Composable
    fun setSeparatorPadding(separator: PaddingValues): BaseBreadCrumbsItem

}
