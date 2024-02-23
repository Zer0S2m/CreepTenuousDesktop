package com.zer0s2m.creeptenuous.desktop.ui.components

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
    val onClick: () -> Unit

    /**
     * Set the spacing between navigation items, set to padding.
     *
     * @param separator Padding.
     */
    @Composable
    fun setSeparatorPadding(separator: PaddingValues): BaseBreadCrumbsItem

}