package components.base

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Base interface for component implementation - breadcrumbs
 */
interface BaseBreadCrumbs : BaseComponent {

    /**
     * List of components for drawing navigation items
     */
    val items: Collection<BaseBreadCrumbsItem>
        get() = listOf()

    /**
     * Spacing values applied internally between navigation items [BaseBreadCrumbsItem]
     */
    val separatorPadding: Dp
        get() = 0.dp

}
