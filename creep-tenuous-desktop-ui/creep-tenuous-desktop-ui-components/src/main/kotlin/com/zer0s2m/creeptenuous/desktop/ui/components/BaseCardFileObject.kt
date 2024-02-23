package com.zer0s2m.creeptenuous.desktop.ui.components

import androidx.compose.material.Card

/**
 * Base class for implementing work with file objects.
 * Main storage support types: directories and files.
 * Extending components [Card]
 */
interface BaseCardFileObject : BaseComponent, BaseAnimation {

    /**
     * File object type designation - directory
     */
    val isDirectory: Boolean

    /**
     * File object type designation - file
     */
    val isFile: Boolean

    /**
     * Display text when rendering
     */
    val text: String
        get() = ""

    /**
     * Color palette for file object type - directory
     */
    val color: String?
        get() = null

    /**
     * The user category to which the file object is linked
     */
    val categoryId: Int?
        get() = null

    /**
     *Action called when information about a file object is loaded.
     */
    val actionInfo: () -> Unit

    /**
     * Action called when a file object is downloaded
     */
    val actionDownload: () -> Unit

    /**
     * Action called when a file object is renamed
     */
    val actionRename: () -> Unit

    /**
     * Action called when a file object is copied
     */
    val actionCopy: () -> Unit

    /**
     * Action called when a file object is moved
     */
    val actionMove: () -> Unit

    /**
     * Action called when a file object is deleted
     */
    val actionDelete: () -> Unit

    /**
     * Action called when comments are opened.
     */
    val actionComments: () -> Unit

    /**
     * Action called when a custom category is set on a file object
     */
    val actionSetCategory: () -> Unit

    /**
     * Action called when a custom color is set on a file object
     */
    val actionSetColor: () -> Unit

    /**
     * Action called when double-clicked.
     */
    val actionDoubleClick: () -> Unit

}