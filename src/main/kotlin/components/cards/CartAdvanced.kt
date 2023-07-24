package components.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import components.base.BaseCard
import components.menu.DropdownMenuAdvanced
import components.menu.DropdownMenuItemAdvanced
import core.errors.ComponentException
import enums.Colors
import enums.Resources

/**
 * Base class for implementing work with file objects.
 * Main storage support types: directories and files.
 * Extending components [Card]
 *
 * @param isDirectory File object type designation - directory
 * @param isFile File object type designation - file
 * @param text Display text when rendering
 */
class CartAdvanced(
    override val isDirectory: Boolean,
    override val isFile: Boolean,
    override val text: String = "",
) : BaseCard {

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
            throw ComponentException("The component is built from two types of main storage objects. " +
                    "Specify a directory or file")
        }
    }

    /**
     * Renders the foundation of the component
     *
     * @param content Main component block
     */
    @Composable
    private fun renderBase(content: @Composable () -> Unit) {
        Card(
            backgroundColor = Colors.CARD_BASE.color,
            modifier = Modifier
                .padding(6.dp, 6.dp, 6.dp, 6.dp)
                .fillMaxWidth()
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
                        .padding(16.dp, 12.dp)
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
            fontWeight = FontWeight.Bold
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
            fontWeight = FontWeight.Bold
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
        val items: Iterable<DropdownMenuItemAdvanced> = listOf(
            DropdownMenuItemAdvanced(
                text = "Menu item 1",
                expanded = expanded
            ),
            DropdownMenuItemAdvanced(
                text = "Menu item 2",
                expanded = expanded
            )
        )

        DropdownMenuAdvanced(
            itemsComp = items,
            expanded = expanded,
            modifier = Modifier
                .width(240.dp),
        ).render()
    }

}
