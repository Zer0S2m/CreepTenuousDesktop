package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import ui.components.misc.Avatar
import ui.components.base.BaseDashboard
import core.actions.navigationScreen
import core.context.BaseContextScreen
import core.errors.ComponentException
import core.navigation.NavigationController
import core.navigation.graphs.*
import enums.*
import enums.Colors

/**
 * User profile screen
 *
 * @param navigation Handler for the navigation host for changing the current screen state
 */
class ProfileUser(override var navigation: NavigationController?) : BaseDashboard {

    /**
     * Text used by accessibility services to describe what this image represents
     */
    private val contentDescriptionIcon: String = "Go to main menu"

    companion object {

        /**
         * Base screen for initial user profile page
         */
        internal val baseScreen: Screen = Screen.PROFILE_SETTINGS_SCREEN

        /**
         * The new state of the applied screen from the transition from the previous screen
         */
        internal val appliedScreenFromTransitionFromPast: MutableState<Screen> = mutableStateOf(baseScreen)

        /**
         * Set new screen state from past transition
         *
         * @param context Context `illusion` to convey new screen state
         */
        fun setAppliedScreenFromTransitionFromPast(context: BaseContextScreen) {
            appliedScreenFromTransitionFromPast.value = context.screen!!
        }

        /**
         * Transition to a new screen state of a non-child parent screen [Screen.PROFILE_SCREEN.childs]
         */
        internal val isFromPastScreen: MutableState<Boolean> = mutableStateOf(true)

    }

    /**
     * Internal navigation host to change user profile screen
     */
    private val internalNavigation: State<NavigationController> = mutableStateOf(
        NavigationController(startDestination = baseScreen.name)
    )

    /**
     * Splitter render for splitting the screen into two parts
     *
     * @param scaffoldState State of this scaffold widget.
     */
    @Composable
    override fun render(
        scaffoldState: ScaffoldState
    ) {
        super.render(scaffoldState = scaffoldState)

        if (navigation == null) {
            throw ComponentException(message = "To build a screen [ProfileUser], you need a parameter [navigation]")
        }

        if (isFromPastScreen.value) {
            internalNavigation.value.navigate(appliedScreenFromTransitionFromPast.value.name)
            isFromPastScreen.value = false
        }
    }

    /**
     * Rendering the left side of the content
     */
    @Composable
    override fun renderLeftContent() {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(SizeComponents.LEFT_PANEL_DASHBOARD.float)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(SizeComponents.UPPER_BLOCK_LEFT_PANEL.float)
                    .fillMaxWidth()
                    .padding(baseHorizontalPadding, 0.dp)
            ) {
                renderUserAvatar()
            }
            Column {
                renderMenu()
            }
        }
    }

    /**
     * Render the right side of the content
     */
    @Composable
    override fun renderRightContent() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
        ) {
            CollectScreenProfileFileObjectDistribution(internalNavigation.value)
            CollectScreenProfileSettings(internalNavigation.value)
            CollectScreenProfileGrantedRights(internalNavigation.value)

            CollectScreenProfileListUsers(internalNavigation.value)
            CollectScreenProfileUserControl(internalNavigation.value)

            CollectScreenProfileCategories(internalNavigation.value)
            CollectScreenProfileColors(internalNavigation.value)
        }
    }

    /**
     * [Screen.PROFILE_FILE_OBJECT_DISTRIBUTION]
     */
    class ProfileFileObjectDistribution

    /**
     * [Screen.PROFILE_SETTINGS_SCREEN]
     */
    class ProfileSettings

    /**
     * [Screen.PROFILE_GRANTED_RIGHTS_SCREEN]
     */
    class ProfileGrantedRights

    /**
     * [Screen.PROFILE_LIST_USERS_SCREEN]
     */
    class ProfileListUsers

    /**
     * [Screen.PROFILE_USER_MANAGEMENT_SCREEN]
     */
    class ProfileUserControl

    /**
     * [Screen.PROFILE_CATEGORY_SCREEN]
     */
    class ProfileCategories

    /**
     * [Screen.PROFILE_COLORS_SCREEN]
     */
    class ProfileColors

    /**
     * Render user icon and name
     */
    @Composable
    private fun renderUserAvatar() {
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
                verticalAlignment = Alignment.CenterVertically
            ) {
                Avatar(
                    modifierIcon = Modifier
                        .padding(0.dp)
                        .pointerHoverIcon(icon = PointerIcon.Hand)
                        .width(32.dp)
                        .height(32.dp),
                    enabled = false
                ).render()
                Text(
                    text = "User name",
                    modifier = Modifier
                        .padding(start = 8.dp)
                )
            }
            IconButton(
                onClick = {
                    navigation?.navigate(Screen.DASHBOARD_SCREEN.name)
                    isFromPastScreen.value = true
                    appliedScreenFromTransitionFromPast.value = baseScreen
                },
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 8.dp)
            ) {
                Icon(
                    painter = painterResource(resourcePath = Resources.ICON_MAIN_DASHBOARD.path),
                    contentDescription = contentDescriptionIcon,
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerHoverIcon(icon = PointerIcon.Hand)
                )
            }
        }
    }

    /**
     * Render main section for user settings
     */
    @Composable
    private fun renderMenu() {
        titleSectionInMenu(text = Sections.MAIN_PROFILE.title)
        Sections.MAIN_PROFILE.sections
            .zip(Sections.MAIN_PROFILE.routes) { item, route ->
                itemSectionInMenu(
                    text = item,
                    navigation = internalNavigation,
                    route = route
                )
            }

        titleSectionInMenu(text = Sections.USER_CONTROL.title)
        Sections.USER_CONTROL.sections
            .zip(Sections.USER_CONTROL.routes) { item, route ->
                itemSectionInMenu(
                    text = item,
                    navigation = internalNavigation,
                    route = route
                )
            }

        titleSectionInMenu(text = Sections.USER_CUSTOMIZATION.title)
        Sections.USER_CUSTOMIZATION.sections
            .zip(Sections.USER_CUSTOMIZATION.routes) { item, route ->
                itemSectionInMenu(
                    text = item,
                    navigation = internalNavigation,
                    route = route
                )
            }
    }

}

/**
 * Base title for the section collection menu
 *
 * @param text The text to be displayed [Text]
 */
@Composable
internal fun titleSectionInMenu(text: String = "") {
    Row(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = Colors.TEXT.color,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(baseHorizontalPadding, 6.dp)
        )
    }
}

/**
 * Basic button element for screen navigation. Extends a component [Button]
 *
 * @param text The text to be displayed [Text]
 * @param enabled Controls the enabled state of the surface [Surface]
 * @param navigation Internal navigational user profile screen handler
 * @param route Screen name for changing the current state
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun itemSectionInMenu(
    text: String = "",
    enabled: Boolean = true,
    navigation: State<NavigationController>,
    route: Screen
) {
    val isCurrentScreenRoute: Boolean = navigation.value.currentScreen.value == route.name
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
        onClick = {
            navigationScreen.action(
                state = navigation,
                route = route
            )
        },
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
                        .padding(PaddingValues(
                            horizontal = baseHorizontalPadding,
                            vertical = 8.dp
                        )),
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
 * Basic horizontal padding for building the left side of the panel
 */
internal val baseHorizontalPadding: Dp get() = 12.dp
