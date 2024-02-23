package com.zer0s2m.creeptenuous.desktop.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zer0s2m.creeptenuous.desktop.common.enums.Resources
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.common.enums.SectionsProfileUser
import com.zer0s2m.creeptenuous.desktop.common.enums.SizeComponents
import com.zer0s2m.creeptenuous.desktop.core.context.BaseContextScreen
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import com.zer0s2m.creeptenuous.desktop.core.navigation.actions.reactiveNavigationScreen
import com.zer0s2m.creeptenuous.desktop.navigation.NavigationController
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveUser
import com.zer0s2m.creeptenuous.desktop.ui.components.Avatar
import com.zer0s2m.creeptenuous.desktop.ui.components.CardSectionProfileUser
import com.zer0s2m.creeptenuous.desktop.ui.components.TitleMenuSectionProfileUser
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.float
import com.zer0s2m.creeptenuous.desktop.ui.screens.graphs.CollectScreenProfileCategories
import com.zer0s2m.creeptenuous.desktop.ui.screens.graphs.CollectScreenProfileColors
import com.zer0s2m.creeptenuous.desktop.ui.screens.graphs.CollectScreenProfileFileObjectDistribution
import com.zer0s2m.creeptenuous.desktop.ui.screens.graphs.CollectScreenProfileGrantedRights
import com.zer0s2m.creeptenuous.desktop.ui.screens.graphs.CollectScreenProfileListUsers
import com.zer0s2m.creeptenuous.desktop.ui.screens.graphs.CollectScreenProfileSettings
import com.zer0s2m.creeptenuous.desktop.ui.screens.graphs.CollectScreenProfileUserControl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * User profile screen
 *
 * @param navigation Handler for the navigation host for changing the current screen state
 */
class ProfileUser(var navigation: NavigationController) {

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
        private val baseSection: SectionsProfileUser = SectionsProfileUser.MAIN_PROFILE

        /**
         * The new state of the applied screen from the transition from the previous screen
         */
        private val appliedScreenFromTransitionFromPast: MutableState<Screen> = mutableStateOf(baseScreen)

        /**
         * The new state of the applied screen from the transition from the previous screen
         */
        private val appliedSectionFromTransitionFromPast: MutableState<SectionsProfileUser> = mutableStateOf(baseSection)

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
        internal fun setAppliedSectionFromTransitionFromPast(section: SectionsProfileUser) {
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
    fun render(
        scaffoldState: ScaffoldState
    ) {
        val scope = rememberCoroutineScope()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    renderLeftContent(scope = scope)
                    renderRightContent()
                }
            }
        }

        if (isFromPastScreen.value) {
            var objects: Collection<String> = listOf()
            val currentScreen = appliedScreenFromTransitionFromPast.value

            val objectsFromSection = appliedSectionFromTransitionFromPast.value.objects[currentScreen]
            if (objectsFromSection != null) {
                objects = objectsFromSection
            }

            isFromPastScreen.value = false

            scope.launch {
                reactiveNavigationScreen.action(
                    state = internalNavigation,
                    route = appliedScreenFromTransitionFromPast.value,
                    objects = objects,
                    scope = scope
                )
            }
        }
    }

    /**
     * Rendering the left side of the content
     */
    @Composable
    fun renderLeftContent(scope: CoroutineScope) {
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
                UserAvatar()
            }
            Column {
                Menu(scope = scope)
            }
        }
    }

    /**
     * Render the right side of the content
     */
    @Composable
    fun renderRightContent() {
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
    private fun UserAvatar() {
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
    private fun Menu(scope: CoroutineScope) {
        LazyColumn {
            item {
                TitleMenuSectionProfileUser(
                    text = SectionsProfileUser.MAIN_PROFILE.title,
                    modifierText = Modifier.padding(baseHorizontalPadding, 6.dp)
                )
                SectionsProfileUser.MAIN_PROFILE.sections.entries.toList()
                    .zip(SectionsProfileUser.MAIN_PROFILE.routes) { item, route ->
                        if (!item.value || (item.value && ReactiveUser.profileSettings!!.role.contains("ROLE_ADMIN"))) {
                            CardSectionProfileUser(
                                text = item.key,
                                onClick = {
                                    var objects: Collection<String> = listOf()

                                    if (SectionsProfileUser.MAIN_PROFILE.objects.containsKey(route)) {
                                        objects = SectionsProfileUser.MAIN_PROFILE.objects[route]!!
                                    }

                                    scope.launch {
                                        reactiveNavigationScreen.action(
                                            state = internalNavigation,
                                            route = route,
                                            objects = objects,
                                            scope = scope
                                        )
                                    }
                                }
                            )
                        }
                    }

                TitleMenuSectionProfileUser(
                    text = SectionsProfileUser.USER_CONTROL.title,
                    modifierText = Modifier.padding(baseHorizontalPadding, 6.dp)
                )
                SectionsProfileUser.USER_CONTROL.sections.entries.toList()
                    .zip(SectionsProfileUser.USER_CONTROL.routes) { item, route ->
                        if (!item.value || (item.value && ReactiveUser.profileSettings!!.role.contains("ROLE_ADMIN"))) {
                            CardSectionProfileUser(
                                text = item.key,
                                onClick = {
                                    var objects: Collection<String> = listOf()

                                    if (SectionsProfileUser.USER_CONTROL.objects.containsKey(route)) {
                                        objects = SectionsProfileUser.USER_CONTROL.objects[route]!!
                                    }

                                    scope.launch {
                                        reactiveNavigationScreen.action(
                                            state = internalNavigation,
                                            route = route,
                                            objects = objects,
                                            scope = scope
                                        )
                                    }
                                }
                            )
                        }
                    }

                TitleMenuSectionProfileUser(
                    text = SectionsProfileUser.USER_CUSTOMIZATION.title,
                    modifierText = Modifier.padding(baseHorizontalPadding, 6.dp)
                )
                SectionsProfileUser.USER_CUSTOMIZATION.sections.entries.toList()
                    .zip(SectionsProfileUser.USER_CUSTOMIZATION.routes) { item, route ->
                        if (!item.value || (item.value && ReactiveUser.profileSettings!!.role.contains("ROLE_ADMIN"))) {
                            CardSectionProfileUser(
                                text = item.key,
                                onClick = {
                                    var objects: Collection<String> = listOf()

                                    if (SectionsProfileUser.USER_CUSTOMIZATION.objects.containsKey(route)) {
                                        objects = SectionsProfileUser.USER_CUSTOMIZATION.objects[route]!!
                                    }

                                    scope.launch {
                                        reactiveNavigationScreen.action(
                                            state = internalNavigation,
                                            route = route,
                                            objects = objects,
                                            scope = scope
                                        )
                                    }
                                }
                            )
                        }
                    }
            }
        }
    }

}

/**
 * Basic horizontal padding for building the left side of the panel
 */
@get:ReadOnlyComposable
private val baseHorizontalPadding: Dp get() = 12.dp
