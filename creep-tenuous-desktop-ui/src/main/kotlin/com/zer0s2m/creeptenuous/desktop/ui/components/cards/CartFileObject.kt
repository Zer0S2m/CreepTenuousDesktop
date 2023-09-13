package com.zer0s2m.creeptenuous.desktop.ui.components.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zer0s2m.creeptenuous.desktop.common.enums.Resources
import com.zer0s2m.creeptenuous.desktop.common.utils.colorConvertHexToRgb
import com.zer0s2m.creeptenuous.desktop.common.utils.manipulateColor
import com.zer0s2m.creeptenuous.desktop.core.errors.ComponentException
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveUser
import com.zer0s2m.creeptenuous.desktop.ui.animations.setAnimateColorAsStateInCard
import com.zer0s2m.creeptenuous.desktop.ui.animations.setHoverInCard
import com.zer0s2m.creeptenuous.desktop.ui.components.base.BaseCardFileObject
import com.zer0s2m.creeptenuous.desktop.ui.components.menu.DropdownMenuAdvanced
import com.zer0s2m.creeptenuous.desktop.ui.components.menu.DropdownMenuItemAdvanced
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.CircleCategoryBox
import com.zer0s2m.creeptenuous.desktop.ui.misc.Colors

/**
 * Base class for implementing work with file objects.
 * Main storage support types: directories and files.
 * Extending components [Card]
 *
 * @param isDirectory File object type designation - directory
 * @param isFile File object type designation - file
 * @param text Display text when rendering
 * @param isAnimation Set background change animation for a component
 * @param color Color palette for file object type - directory
 * @param categoryId The user category to which the file object is linked
 * @param actionDownload Action called when a file object is downloaded
 * @param actionRename Action called when a file object is renamed
 * @param actionCopy Action called when a file object is copied
 * @param actionMove Action called when a file object is moved
 * @param actionSetCategory Action called when a custom category is set on a file object
 * @param actionSetColor Action called when a custom color is set on a file object
 */
class CartFileObject(
    override val isDirectory: Boolean,
    override val isFile: Boolean,
    override val text: String = "",
    override val isAnimation: Boolean = true,
    override val color: String? = null,
    override val categoryId: Int? = null,
    override val actionDownload: () -> Unit = {},
    override val actionRename: () -> Unit = {},
    override val actionCopy: () -> Unit = {},
    override val actionMove: () -> Unit = {},
    override val actionSetCategory: () -> Unit = {},
    override val actionSetColor: () -> Unit = {},
) : BaseCardFileObject {

    /**
     * File object type designation - directory
     */
    private val isDirectoryComp: Boolean = isDirectory

    /**
     * File object type designation - file
     */
    private val isFileComp: Boolean = isFile

    /**
     * Display text when rendering
     */
    private val textComp: String = text

    /**
     * Text used by accessibility services to describe what this image represents
     */
    private val contentDescriptionFolder: String = "Storage object - directory"

    /**
     * Text used by accessibility services to describe what this image represents
     */
    private val contentDescriptionFile: String = "Storage object - file"

    /**
     * Text used by accessibility services to describe what this image represents
     */
    private val contentDescriptionKebab: String = "Menu open icon"

    /**
     * Variable value holder for opening and closing the menu for the user
     */
    private val expandedMenu: MutableState<Boolean> = mutableStateOf(false)

    /**
     * Component rendering
     */
    @Composable
    override fun render() {
        if (isDirectoryComp) {
            renderDirectory()
        } else if (isFileComp) {
            renderFile()
        } else {
            throw ComponentException(
                "The component is built from two types of main storage objects. " +
                        "Specify a directory or file"
            )
        }
    }

    /**
     * Renders the foundation of the component
     *
     * @param content Main component block
     */
    @Composable
    private fun renderBase(content: @Composable () -> Unit) {
        val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
        val isHover: MutableState<Boolean> = remember { mutableStateOf(false) }
        var animatedCardColor = setAnimateColorAsStateInCard(isHover = isHover)

        if (isAnimation) {
            setHoverInCard(
                interactionSource = interactionSource,
                isHover = isHover
            )
        }

        if (color != null) {
            val converterColor = colorConvertHexToRgb(color)
            val backgroundHoverColor = manipulateColor(converterColor, 0.95)
            val newColor = Color(
                red = converterColor.red,
                green = converterColor.green,
                blue = converterColor.blue
            )

            animatedCardColor = setAnimateColorAsStateInCard(
                isHover = isHover,
                backgroundColor = newColor,
                backgroundHover = Color(
                    red = backgroundHoverColor.red,
                    green = backgroundHoverColor.green,
                    blue = backgroundHoverColor.blue
                )
            )
        }

        Card(
            backgroundColor = animatedCardColor.value,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .hoverable(interactionSource = interactionSource)
                .pointerHoverIcon(icon = PointerIcon.Hand),
            elevation = 0.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(12.dp, 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    content()
                }

                renderCategoryLayout()
            }
        }
    }

    /**
     * Rendering a component with the main system storage object type - [isDirectory]
     */
    @Composable
    private fun renderDirectory() {
        renderBase {
            renderDirectoryContent()
        }
    }

    /**
     * Renders the main content of a [isDirectory]
     */
    @Composable
    private fun renderDirectoryContent() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(
                painter = painterResource(resourcePath = Resources.ICON_FOLDER.path),
                contentDescription = contentDescriptionFolder
            )
            Text(
                text = textComp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 8.dp, end = 4.dp),
                color = Colors.TEXT.color,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
        renderIconMenu()
    }

    /**
     * Rendering a component with the main system storage object type - [isFile]
     */
    @Composable
    private fun renderFile() {
        renderBase {
            renderFileContent()
        }
    }

    /**
     * Renders the main content of a [isFile]
     */
    @Composable
    private fun renderFileContent() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(
                painter = painterResource(resourcePath = Resources.ICON_FILE.path),
                contentDescription = contentDescriptionFile
            )
            Text(
                text = textComp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 8.dp, end = 4.dp),
                color = Colors.TEXT.color,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
        renderIconMenu()
    }

    /**
     * Rendering the component responsible for binding a custom category to a file object
     */
    @Composable
    private fun renderCategoryLayout() {
        if (categoryId != null) {
            val userCategory = ReactiveUser.customCategories.find {
                it.id == categoryId
            }
            if (userCategory != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(0.dp)
                ) {
                    if (userCategory.color != null) {
                        CircleCategoryBox(userCategory.color!!, 10.dp)
                    }
                    Text(
                        text = userCategory.title,
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = if (userCategory.color != null) Modifier
                            .padding(start = 8.dp) else Modifier
                    )
                }
            }
        }
    }

    /**
     * Rendering a component - opening a menu for the underlying file system object
     */
    @Composable
    private fun renderIconMenu() {
        Box(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            IconButton(
                onClick = {
                    expandedMenu.value = !expandedMenu.value
                },
                modifier = Modifier
                    .padding(0.dp)
            ) {
                Icon(
                    painter = painterResource(resourcePath = Resources.ICON_KEBAB_MENU_CARD.path),
                    contentDescription = contentDescriptionKebab,
                    tint = Color.Black,
                    modifier = Modifier
                        .padding(0.dp)
                )
            }

            renderDropDownMenu(expandedMenu)
        }
    }

    /**
     * Render component - dropdown menu [DropdownMenuAdvanced]
     *
     * @param expanded Variable value holder for opening and closing the menu for the user
     */
    @Composable
    private fun renderDropDownMenu(expanded: MutableState<Boolean>) {
        val modifierMenu: Modifier = Modifier
            .pointerHoverIcon(icon = PointerIcon.Hand)
        val contentPaddingMenu = PaddingValues(12.dp, 4.dp)

        val items: MutableList<DropdownMenuItemAdvanced> = mutableListOf(
            DropdownMenuItemAdvanced(
                text = "Download",
                colorText = Color.Black,
                modifierMenu = modifierMenu,
                contentPadding = contentPaddingMenu,
                action = {
                    expandedMenu.value = false
                    actionDownload()
                }
            ),
            DropdownMenuItemAdvanced(
                text = "Rename",
                colorText = Color.Black,
                modifierMenu = modifierMenu,
                contentPadding = contentPaddingMenu,
                action = {
                    expandedMenu.value = false
                    actionRename()
                }
            ),
            DropdownMenuItemAdvanced(
                text = "Copy",
                colorText = Color.Black,
                modifierMenu = modifierMenu,
                contentPadding = contentPaddingMenu,
                action = {
                    expandedMenu.value = false
                    actionCopy()
                }
            ),
            DropdownMenuItemAdvanced(
                text = "Move",
                colorText = Color.Black,
                modifierMenu = modifierMenu,
                contentPadding = contentPaddingMenu,
                action = {
                    expandedMenu.value = false
                    actionMove()
                }
            ),
            DropdownMenuItemAdvanced(
                text = "Set category",
                colorText = Color.Black,
                modifierMenu = modifierMenu,
                contentPadding = contentPaddingMenu,
                action = {
                    expandedMenu.value = false
                    actionSetCategory()
                }
            )
        )

        if (isDirectoryComp) {
            items.add(DropdownMenuItemAdvanced(
                text = "Set color",
                colorText = Color.Black,
                modifierMenu = modifierMenu,
                contentPadding = contentPaddingMenu,
                action = {
                    expandedMenu.value = false
                    actionSetColor()
                }
            ))
        }

        DropdownMenuAdvanced(
            itemsComp = items,
            expanded = expanded,
            modifier = Modifier
                .width(240.dp)
                .background(Color.White)
        ).render()
    }

}
