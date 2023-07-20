package components.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import components.base.BaseCard
import enums.Colors
import enums.Resources

/**
 * Base class for implementing work with file objects.
 * Support types: directories and files
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
    private val contentDescription: String = "Storage object - directory"

    /**
     * Component rendering
     */
    @Composable
    override fun render() {
        if (isDirectoryComp) {
            renderDirectory()
        } else if (isFileComp) {
            renderFile()
        }
    }

    /**
     * Rendering a component with the main system storage object type - [isDirectory]
     */
    @Composable
    private fun renderDirectory() {
        Card(
            backgroundColor = Colors.CARD_BASE.color,
            modifier = Modifier
                .padding(6.dp)
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
                    modifier = Modifier
                        .padding(16.dp, 16.dp)
                ) {
                    renderDirectoryContent()
                }
            }
        }
    }

    /**
     * Renders the main content of a [isDirectory]
     */
    @Composable
    private fun renderDirectoryContent() {
        Image(
            painter = painterResource(resourcePath = Resources.ICON_FOLDER.path),
            contentDescription = contentDescription
        )
        Text(
            text = textComp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(12.dp, 0.dp),
            color = Colors.TEXT.color,
            fontWeight = FontWeight.Bold
        )
    }

    /**
     * Rendering a component with the main system storage object type - [isFile]
     */
    @Composable
    private fun renderFile() {

    }


}
