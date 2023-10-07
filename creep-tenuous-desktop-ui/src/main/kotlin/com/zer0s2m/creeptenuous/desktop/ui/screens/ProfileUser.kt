package com.zer0s2m.creeptenuous.desktop.ui.screens

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zer0s2m.creeptenuous.desktop.common.enums.Resources
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.common.enums.Sections
import com.zer0s2m.creeptenuous.desktop.common.enums.SizeComponents
import com.zer0s2m.creeptenuous.desktop.core.context.BaseContextScreen
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import com.zer0s2m.creeptenuous.desktop.core.navigation.actions.reactiveNavigationScreen
import com.zer0s2m.creeptenuous.desktop.navigation.NavigationController
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveUser
import com.zer0s2m.creeptenuous.desktop.ui.components.base.BaseDashboard
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.Avatar
import com.zer0s2m.creeptenuous.desktop.ui.misc.Colors
import com.zer0s2m.creeptenuous.desktop.ui.misc.float
import com.zer0s2m.creeptenuous.desktop.ui.navigation.graphs.*
import kotlinx.coroutines.launch

/**
 * User profile screen
 *
 * @param navigation Handler for the navigation host for changing the current screen state
 */
class ProfileUser(override var navigation: NavigationController) : BaseDashboard {

    /**
     * Text used by accessibility services to describe what this image represents
     */
    private val contentDescriptionIcon: String = "Go to main menu"

    /**
     * Base object for managing profile screen state
     */
    internal companion object {

        /**
         * Base screen for initial user profile page
         */
        private val baseScreen: Screen = Screen.PROFILE_SETTINGS_SCREEN

        /**
         * Base section for initial user profile page
         */
        private val baseSection: Sections = Sections.MAIN_PROFILE

        /**
         * The new state of the applied screen from the transition from the previous screen
         */
        private val appliedScreenFromTransitionFromPast: MutableState<Screen> = mutableStateOf(baseScreen)

        /**
         * The new state of the applied screen from the transition from the previous screen
         */
        private val appliedSectionFromTransitionFromPast: MutableState<Sections> = mutableStateOf(baseSection)

        /**
         * Set new screen state from past transition
         *
         * @param context Context `illusion` to convey new screen state
         */
        internal fun setAppliedScreenFromTransitionFromPast(context: BaseContextScreen) {
            appliedScreenFromTransitionFromPast.value = context.screen
        }

        /**
         * Set new section state from past transition
         *
         * @param section Section
         */
        internal fun setAppliedSectionFromTransitionFromPast(section: Sections) {
            appliedSectionFromTransitionFromPast.value = section
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

        val coroutineScope = rememberCoroutineScope()

        if (isFromPastScreen.value) {
            var objects: Collection<String> = listOf()
            val currentScreen = appliedScreenFromTransitionFromPast.value

            val objectsFromSection = appliedSectionFromTransitionFromPast.value.objects[currentScreen]
            if (objectsFromSection != null) {
                objects = objectsFromSection
            }

            isFromPastScreen.value = false

            coroutineScope.launch {
                reactiveNavigationScreen.action(
                    state = internalNavigation,
                    route = appliedScreenFromTransitionFromPast.value,
                    objects = objects,
                    scope = coroutineScope
                )
            }
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
        val coroutineScope = rememberCoroutineScope()

        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.8f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val avatar: String? = if (ReactiveUser.profileSettings != null)
                    ReactiveUser.profileSettings!!.avatar else null

                Avatar(
                    modifierIcon = Modifier
                        .padding(0.dp)
                        .pointerHoverIcon(icon = PointerIcon.Hand)
                        .width(32.dp)
                        .height(32.dp),
                    enabled = false,
                    avatar = avatar
                ).render()
                Text(
                    text = ReactiveUser.profileSettings!!.name,
                    modifier = Modifier
                        .padding(start = 8.dp)
                )
            }
            IconButton(
                onClick = {
                    ContextScreen.clearScreen(Screen.PROFILE_USER_MANAGEMENT_SCREEN)
                    coroutineScope.launch {
                        reactiveNavigationScreen.action(
                            state = mutableStateOf(navigation),
                            route = Screen.DASHBOARD_SCREEN,
                            objects = listOf(
                                "managerFileSystemObjects"
                            ),
                            scope = coroutineScope
                        )

                        isFromPastScreen.value = true
                        appliedScreenFromTransitionFromPast.value = baseScreen
                    }
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
        TitleSectionInMenu(text = Sections.MAIN_PROFILE.title)
        Sections.MAIN_PROFILE.sections
            .zip(Sections.MAIN_PROFILE.routes) { item, route ->
                ItemSectionInMenu(
                    text = item,
                    navigation = internalNavigation,
                    route = route,
                    section = Sections.MAIN_PROFILE
                )
            }

        TitleSectionInMenu(text = Sections.USER_CONTROL.title)
        Sections.USER_CONTROL.sections
            .zip(Sections.USER_CONTROL.routes) { item, route ->
                ItemSectionInMenu(
                    text = item,
                    navigation = internalNavigation,
                    route = route,
                    section = Sections.USER_CONTROL
                )
            }

        TitleSectionInMenu(text = Sections.USER_CUSTOMIZATION.title)
        Sections.USER_CUSTOMIZATION.sections
            .zip(Sections.USER_CUSTOMIZATION.routes) { item, route ->
                ItemSectionInMenu(
                    text = item,
                    navigation = internalNavigation,
                    route = route,
                    section = Sections.USER_CUSTOMIZATION
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
private fun TitleSectionInMenu(text: String = "") {
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
private fun ItemSectionInMenu(
    text: String = "",
    enabled: Boolean = true,
    navigation: State<NavigationController>,
    route: Screen,
    section: Sections
) {
    val coroutineScope = rememberCoroutineScope()

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
            var objects: Collection<String> = listOf()

            if (section.objects.containsKey(route)) {
                objects = section.objects[route]!!
            }

            coroutineScope.launch {
                reactiveNavigationScreen.action(
                    state = navigation,
                    route = route,
                    objects = objects,
                    scope = coroutineScope
                )
            }
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
@get:ReadOnlyComposable
private val baseHorizontalPadding: Dp get() = 12.dp
