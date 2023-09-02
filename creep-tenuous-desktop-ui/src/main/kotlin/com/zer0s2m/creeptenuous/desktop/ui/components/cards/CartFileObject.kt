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
import com.zer0s2m.creeptenuous.desktop.ui.animations.setAnimateColorAsStateInCard
import com.zer0s2m.creeptenuous.desktop.ui.animations.setHoverInCard
import com.zer0s2m.creeptenuous.desktop.ui.components.base.BaseCardFileObject
import com.zer0s2m.creeptenuous.desktop.ui.components.menu.DropdownMenuAdvanced
import com.zer0s2m.creeptenuous.desktop.ui.components.menu.DropdownMenuItemAdvanced
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
 */
class CartFileObject(
    override val isDirectory: Boolean,
    override val isFile: Boolean,
    override val text: String = "",
    override val isAnimation: Boolean = true,
    override val color: String? = null
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
                .hoverable(interactionSource = interactionSource)
                .pointerHoverIcon(icon = PointerIcon.Hand),
            elevation = 0.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(12.dp, 8.dp)
                        .fillMaxWidth()
                ) {
                    content()
                }
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
        Image(
            painter = painterResource(resourcePath = Resources.ICON_FOLDER.path),
            contentDescription = contentDescriptionFolder
        )
        Text(
            text = textComp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(12.dp, 0.dp),
            color = Colors.TEXT.color,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
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
        Image(
            painter = painterResource(resourcePath = Resources.ICON_FILE.path),
            contentDescription = contentDescriptionFile
        )
        Text(
            text = textComp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(12.dp, 0.dp),
            color = Colors.TEXT.color,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        renderIconMenu()
    }

    /**
     * Rendering a component - opening a menu for the underlying file system object
     */
    @Composable
    private fun renderIconMenu() {
        Box(
            modifier = Modifier
                .fillMaxHeight(1f)
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

        val items: Iterable<DropdownMenuItemAdvanced> = listOf(
            DropdownMenuItemAdvanced(
                text = "Download",
                expanded = expanded,
                colorText = Color.Black,
                modifierMenu = modifierMenu,
                contentPadding = contentPaddingMenu
            ),
            DropdownMenuItemAdvanced(
                text = "Rename",
                expanded = expanded,
                colorText = Color.Black,
                modifierMenu = modifierMenu,
                contentPadding = contentPaddingMenu
            ),
            DropdownMenuItemAdvanced(
                text = "Copy",
                expanded = expanded,
                colorText = Color.Black,
                modifierMenu = modifierMenu,
                contentPadding = contentPaddingMenu
            ),
            DropdownMenuItemAdvanced(
                text = "Move",
                expanded = expanded,
                colorText = Color.Black,
                modifierMenu = modifierMenu,
                contentPadding = contentPaddingMenu
            )
        )

        DropdownMenuAdvanced(
            itemsComp = items,
            expanded = expanded,
            modifier = Modifier
                .width(240.dp)
                .background(Color.White)
        ).render()
    }

}
