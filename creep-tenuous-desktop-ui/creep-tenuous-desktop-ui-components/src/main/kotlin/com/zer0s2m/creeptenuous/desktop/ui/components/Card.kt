package com.zer0s2m.creeptenuous.desktop.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.isSecondary
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zer0s2m.creeptenuous.desktop.common.enums.Resources
import com.zer0s2m.creeptenuous.desktop.common.utils.colorConvertHexToRgb
import com.zer0s2m.creeptenuous.desktop.common.utils.manipulateColor
import com.zer0s2m.creeptenuous.desktop.core.errors.ComponentBuildException
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveUser
import com.zer0s2m.creeptenuous.desktop.ui.components.animations.setAnimateColorAsStateInCard
import com.zer0s2m.creeptenuous.desktop.ui.components.animations.setHoverInCard
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.Colors
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Text used by accessibility services to describe what this image represents
 */
private val contentDescriptionIconBaseFolderUser: String get() = "Icon displayed meaning directory"

/**
 * Text used by accessibility services to describe what this image represents
 */
@get:ReadOnlyComposable
private val contentDescriptionUnblock: String get() = "User unlock icon"

/**
 * Date format for file object comment.
 */
@get:ReadOnlyComposable
private val dateFormatForComment: DateTimeFormatter
    get() =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")

/**
 * optional [Modifier] for this [Icon].
 */
@Stable
private val baseModifierIcon: Modifier
    get() = Modifier
        .size(24.dp)
        .pointerHoverIcon(icon = PointerIcon.Hand)

/**
 * Autonomous component - a card for navigating through the user profile.
 *
 * Extends a component [Card]
 *
 * @param modifier Modifier to be applied to the layout of the card.
 * @param backgroundColor The background color.
 * @param isAnimation Set background change animation for a component.
 * @param onClick Callback to be called when the [Card] is clicked.
 */
@Composable
@OptIn(ExperimentalMaterialApi::class)
fun CardModalSheetSectionProfileUser(
    modifier: Modifier = Modifier.fillMaxSize(),
    backgroundColor: Color = Colors.CARD_BASE.color,
    isAnimation: Boolean = true,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    var baseModifier: Modifier = modifier
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    val isHover: MutableState<Boolean> = remember { mutableStateOf(false) }
    val animatedCardColor = setAnimateColorAsStateInCard(isHover = isHover)

    if (isAnimation) {
        baseModifier = modifier
            .hoverable(interactionSource = interactionSource)
        setHoverInCard(
            interactionSource = interactionSource,
            isHover = isHover
        )
    }

    Card(
        onClick = onClick,
        modifier = baseModifier,
        elevation = 0.dp,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = if (isAnimation) animatedCardColor.value else backgroundColor
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            content()
        }
    }
}

/**
 * Stand-alone component - base catalog card (Music, Documents, etc.)ю
 *
 * @param text The text to be displayed.
 * @param isAnimation Set background change animation for a component.
 * @param isIcon Set an icon for a component.
 * @param iconPath Path to icons [Resources].
 * @param onClick Callback to be called when the [Card] is clicked.
 */
@Composable
@OptIn(ExperimentalMaterialApi::class)
fun CardPanelBaseFolderUser(
    text: String,
    isAnimation: Boolean = true,
    isIcon: Boolean = false,
    iconPath: String? = null,
    onClick: () -> Unit
) {
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    val isHover: MutableState<Boolean> = remember { mutableStateOf(false) }
    val animatedButtonColor = setAnimateColorAsStateInCard(
        isHover = isHover
    )

    if (isAnimation) {
        setHoverInCard(
            interactionSource = interactionSource,
            isHover = isHover
        )
    }

    Card(
        backgroundColor = animatedButtonColor.value,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .hoverable(interactionSource = interactionSource)
            .pointerHoverIcon(icon = PointerIcon.Hand),
        elevation = 0.dp,
        shape = RoundedCornerShape(0.dp),
        contentColor = Color.Black,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp),
            verticalArrangement = Arrangement.Center
        ) {
            if (!isIcon) {
                Text(
                    text = text,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(12.dp, 0.dp)
                )
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    iconPath?.let { painterResource(resourcePath = it) }?.let {
                        Image(
                            painter = it,
                            contentDescription = contentDescriptionIconBaseFolderUser,
                            modifier = Modifier
                                .height(24.dp)
                                .width(24.dp)
                        )
                    }
                    Text(
                        text = text,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(12.dp, 0.dp)
                    )
                }
            }
        }
    }
}

/**
 * A standalone component is a comment card for a file object.
 *
 * @param text Comment text.
 * @param createdAt Date the comment was created.
 * @param modifierButtonEdit The modifier to be applied to the layout for button edit.
 * @param modifierButtonDelete The modifier to be applied to the layout. for button delete
 * @param actionEdit The lambda to be invoked when this icon is pressed [IconButton]. Event - edit.
 * @param actionDelete The lambda to be invoked when this icon is pressed [IconButton]. Event - delete.
 */
@Composable
fun CardCommentForFileObject(
    text: String,
    createdAt: String,
    modifierButtonEdit: Modifier = Modifier
        .pointerHoverIcon(PointerIcon.Hand)
        .size(24.dp),
    modifierButtonDelete: Modifier = Modifier
        .pointerHoverIcon(PointerIcon.Hand)
        .size(24.dp),
    actionEdit: () -> Unit = {},
    actionDelete: () -> Unit = {}
) {
    val localCreatedAt = DateTimeFormatter
        .ofPattern("MMMM dd, yyyy HH:mm:ss")
        .format(LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_DATE_TIME))

    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = localCreatedAt,
                color = MaterialTheme.colors.secondaryVariant.copy(0.8f),
            )
            Spacer(modifier = Modifier.width(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButtonEdit(
                    onClick = actionEdit,
                    modifier = modifierButtonEdit
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButtonRemove(
                    onClick = actionDelete,
                    modifier = modifierButtonDelete
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = text)
    }
}

/**
 * Basic button element for screen navigation. Extends a component [Surface].
 *
 * @param text The text to be displayed [Text]
 * @param enabled Controls the enabled state of the surface [Surface]
 * @param onClick Callback to be called when the surface is clicked.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardSectionProfileUser(
    text: String,
    enabled: Boolean = true,
    isCurrentScreenRoute: Boolean = false,
    onClick: () -> Unit
) {
    val colors: ButtonColors = ButtonDefaults.outlinedButtonColors()
    val contentColor by colors.contentColor(enabled)
    var modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(48.dp)
    if (!isCurrentScreenRoute) {
        modifier = modifier
            .pointerHoverIcon(icon = PointerIcon.Hand)
    }

    Surface(
        onClick = onClick,
        modifier = modifier.semantics { role = Role.Button },
        enabled = if (isCurrentScreenRoute) false else enabled,
        shape = RoundedCornerShape(0.dp),
        color = if (isCurrentScreenRoute)
            Colors.CARD_SECTION_PROFILE_HOVER.color else colors.backgroundColor(enabled).value,
        contentColor = contentColor.copy(alpha = 1f),
        border = null,
        elevation = 0.dp,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        CompositionLocalProvider(LocalContentAlpha provides contentColor.alpha) {
            ProvideTextStyle(
                value = MaterialTheme.typography.button
            ) {
                Row(
                    Modifier
                        .defaultMinSize(
                            minWidth = ButtonDefaults.MinWidth,
                            minHeight = ButtonDefaults.MinHeight
                        )
                        .padding(
                            PaddingValues(
                                horizontal = 12.dp,
                                vertical = 8.dp
                            )
                        ),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        Text(
                            text = text,
                            color = Color.White
                        )
                    }
                )
            }
        }
    }
}

/**
 * The main card to show the user in the system
 *
 * @param nameUser Username.
 * @param loginUser Login user.
 * @param roleUser Role.
 * @param isBlocked Is blocked user.
 * @param avatar Avatar for user.
 * @param actionDelete The action is called when the delete user button is clicked.
 * @param actionBlock The action is called when the block user button is clicked.
 * @param actionUnblock The action is called when the unblock user button is clicked.
 */
@Composable
fun CardUserControl(
    nameUser: String,
    loginUser: String,
    roleUser: String,
    isBlocked: Boolean,
    avatar: String?,
    actionDelete: (String) -> Unit = {},
    actionBlock: () -> Unit = {},
    actionUnblock: () -> Unit = {},
) {
    BaseCardForItemCardUser(
        nameUser = nameUser,
        loginUser = loginUser,
        fractionBaseInfoUser = 0.6f,
        avatar = avatar
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.75f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Role - $roleUser"
                )
            }
            if (!isBlocked) {
                TooltipAreaMain(text = "User blocking") {
                    IconButtonBlock(
                        onClick = actionBlock,
                        modifierIcon = baseModifierIcon
                    )
                }
            } else {
                TooltipAreaMain(text = "User unblocking") {
                    IconButtonUnblock(
                        onClick = actionUnblock,
                        modifierIcon = baseModifierIcon
                    )
                }
            }

            TooltipAreaMain(text = "Deleting a user") {
                IconButtonRemove(
                    onClick = { actionDelete(loginUser) },
                    contentDescription = contentDescriptionUnblock,
                )
            }
        }
    }
}

/**
 * The main card to show the user in the system
 *
 * @param nameUser Username.
 * @param loginUser Login user.
 * @param roleUser Role.
 * @param avatar Avatar for user.
 */
@Composable
fun CardUserCommon(
    nameUser: String,
    loginUser: String,
    roleUser: String,
    avatar: String?
) {
    BaseCardForItemCardUser(
        nameUser = nameUser,
        loginUser = loginUser,
        avatar = avatar
    ) {
        Text(
            text = "Role - $roleUser"
        )
    }
}


/**
 * Basic card for user interaction in the system. Extends a component [Card]
 *
 * @param nameUser Username
 * @param loginUser Login user
 * @param fractionBaseInfoUser ave the content fill [Modifier.fillMaxHeight] basic user information
 * @param avatar Avatar for user/
 * @param content Map internal content
 */
@Composable
private fun BaseCardForItemCardUser(
    nameUser: String,
    loginUser: String,
    fractionBaseInfoUser: Float = 0.8f,
    avatar: String? = null,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp, 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fractionBaseInfoUser),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Avatar(
                    modifierIcon = Modifier
                        .size(32.dp)
                        .pointerHoverIcon(icon = PointerIcon.Default)
                        .padding(0.dp),
                    avatar = avatar,
                    enabled = false
                ).render()
                Text(
                    text = "$nameUser ($loginUser)",
                    modifier = Modifier
                        .padding(start = 8.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                content()
            }
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
}

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
 * @param actionInfo Action called when information about a file object is loaded.
 * @param actionDownload Action called when a file object is downloaded
 * @param actionRename Action called when a file object is renamed
 * @param actionCopy Action called when a file object is copied
 * @param actionMove Action called when a file object is moved
 * @param actionDelete Action called when a file object is deleted
 * @param actionComments Action called when comments are opened.
 * @param actionSetCategory Action called when a custom category is set on a file object
 * @param actionSetColor Action called when a custom color is set on a file object
 * @param actionDoubleClick Action called when double-clicked.
 */
class CartFileObject(
    override val isDirectory: Boolean,
    override val isFile: Boolean,
    override val text: String = "",
    override val isAnimation: Boolean = true,
    override val color: String? = null,
    override val categoryId: Int? = null,
    override val actionInfo: () -> Unit = {},
    override val actionDownload: () -> Unit = {},
    override val actionRename: () -> Unit = {},
    override val actionCopy: () -> Unit = {},
    override val actionMove: () -> Unit = {},
    override val actionDelete: () -> Unit = {},
    override val actionComments: () -> Unit = {},
    override val actionSetCategory: () -> Unit = {},
    override val actionSetColor: () -> Unit = {},
    override val actionDoubleClick: () -> Unit = {},
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
            throw ComponentBuildException(
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
    @OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
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
                .onPointerEvent(
                    eventType = PointerEventType.Press,
                    pass = PointerEventPass.Final,
                ) { event ->
                    if (event.button?.isSecondary == true) {
                        expandedMenu.value = !expandedMenu.value
                    }
                }
                .combinedClickable(
                    onClick = { },
                    onDoubleClick = {
                        if (isDirectoryComp) {
                            actionDoubleClick()
                        }
                    }
                )
                .pointerHoverIcon(icon = PointerIcon.Hand),
            elevation = 0.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(12.dp, 8.dp)
                    .fillMaxSize()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        content()
                        if (categoryId != null) {
                            Column(
                                modifier = Modifier
                                    .padding(top = 8.dp)
                            ) {
                                CategoryLayout()
                            }
                        }
                    }
                    IconMenu()
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
            DirectoryContent()
        }
    }

    /**
     * Renders the main content of a [isDirectory]
     */
    @Composable
    private fun DirectoryContent() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(
                painter = painterResource(resourcePath = Resources.ICON_FOLDER.path),
                contentDescription = contentDescriptionFolder
            )
            LazyRow(modifier = Modifier.width(100.dp)) {
                item {
                    Text(
                        text = textComp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 8.dp, end = 4.dp),
                        color = Colors.TEXT.color,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }

    /**
     * Rendering a component with the main system storage object type - [isFile]
     */
    @Composable
    private fun renderFile() {
        renderBase {
            FileContent()
        }
    }

    /**
     * Renders the main content of a [isFile]
     */
    @Composable
    private fun FileContent() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(
                painter = painterResource(resourcePath = Resources.ICON_FILE.path),
                contentDescription = contentDescriptionFile
            )
            LazyRow(modifier = Modifier.width(100.dp)) {
                item {
                    Text(
                        text = textComp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 8.dp, end = 4.dp),
                        color = Colors.TEXT.color,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }

    /**
     * Rendering the component responsible for binding a custom category to a file object
     */
    @Composable
    private fun CategoryLayout() {
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
                        if (userCategory.color == color) {
                            Box(
                                modifier = Modifier
                                    .background(Color.White, CircleShape)
                                    .size(14.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircleCategoryBox(userCategory.color!!, 10.dp)
                            }
                        } else {
                            CircleCategoryBox(userCategory.color!!, 10.dp)
                        }
                    }
                    if (userCategory.color != null) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(
                        text = userCategory.title,
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }

    /**
     * Rendering a component - opening a menu for the underlying file system object
     */
    @Composable
    private fun IconMenu() {
        Box(
            modifier = Modifier
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
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

            DropdownMenuActions(expandedMenu)
        }
    }

    /**
     * Render component - dropdown menu.
     *
     * @param expanded Variable value holder for opening and closing the menu for the user
     */
    @Composable
    private fun DropdownMenuActions(expanded: MutableState<Boolean>) {
        val modifierMenu: Modifier = Modifier.pointerHoverIcon(icon = PointerIcon.Hand)

        DropdownMenu(
            expanded = expanded.value,
            modifier = Modifier
                .width(240.dp)
                .background(Color.White),
            onDismissRequest = {
                expanded.value = false
            }
        ) {
            DropdownMenuItemAdvanced(
                text = "Info",
                colorText = Color.Black,
                modifier = modifierMenu,
                onClick = {
                    expandedMenu.value = false
                    actionInfo()
                }
            )
            DropdownMenuItemAdvanced(
                text = "Download",
                colorText = Color.Black,
                modifier = modifierMenu,
                onClick = {
                    expandedMenu.value = false
                    actionDownload()
                }
            )
            DropdownMenuItemAdvanced(
                text = "Rename",
                colorText = Color.Black,
                modifier = modifierMenu,
                onClick = {
                    expandedMenu.value = false
                    actionRename()
                }
            )
            DropdownMenuItemAdvanced(
                text = "Copy",
                colorText = Color.Black,
                modifier = modifierMenu,
                onClick = {
                    expandedMenu.value = false
                    actionCopy()
                }
            )
            DropdownMenuItemAdvanced(
                text = "Move",
                colorText = Color.Black,
                modifier = modifierMenu,
                onClick = {
                    expandedMenu.value = false
                    actionMove()
                }
            )
            DropdownMenuItemAdvanced(
                text = "Delete",
                colorText = Color.Black,
                modifier = modifierMenu,
                onClick = {
                    expandedMenu.value = false
                    actionDelete()
                }
            )
            DropdownMenuItemAdvanced(
                text = "Comments",
                colorText = Color.Black,
                modifier = modifierMenu,
                onClick = {
                    expandedMenu.value = false
                    actionComments()
                }
            )
            DropdownMenuItemAdvanced(
                text = "Set category",
                colorText = Color.Black,
                modifier = modifierMenu,
                onClick = {
                    expandedMenu.value = false
                    actionSetCategory()
                }
            )
            if (isDirectoryComp) {
                DropdownMenuItemAdvanced(
                    text = "Set color",
                    colorText = Color.Black,
                    modifier = modifierMenu,
                    onClick = {
                        expandedMenu.value = false
                        actionSetColor()
                    }
                )
            }
        }
    }

}
