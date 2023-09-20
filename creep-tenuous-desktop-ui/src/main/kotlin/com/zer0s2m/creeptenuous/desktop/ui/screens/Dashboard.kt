package com.zer0s2m.creeptenuous.desktop.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zer0s2m.creeptenuous.desktop.common.dto.FileObject
import com.zer0s2m.creeptenuous.desktop.common.dto.ManagerFileObject
import com.zer0s2m.creeptenuous.desktop.common.enums.Resources
import com.zer0s2m.creeptenuous.desktop.common.enums.Screen
import com.zer0s2m.creeptenuous.desktop.common.enums.Sections
import com.zer0s2m.creeptenuous.desktop.common.enums.SizeComponents
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreenPage
import com.zer0s2m.creeptenuous.desktop.core.injection.ReactiveInjectionClass
import com.zer0s2m.creeptenuous.desktop.core.navigation.actions.reactiveNavigationScreen
import com.zer0s2m.creeptenuous.desktop.navigation.NavigationController
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveFileObject
import com.zer0s2m.creeptenuous.desktop.ui.components.CardModalSheet
import com.zer0s2m.creeptenuous.desktop.ui.components.ModalRightSheetLayout
import com.zer0s2m.creeptenuous.desktop.ui.components.base.BaseDashboard
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.BreadCrumbs
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.BreadCrumbsItem
import com.zer0s2m.creeptenuous.desktop.ui.misc.Colors
import com.zer0s2m.creeptenuous.desktop.ui.misc.float
import com.zer0s2m.creeptenuous.desktop.ui.screens.dashboard.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * The main dashboard for interacting with system file objects
 *
 * @param navigation Handler for the navigation host for changing the current screen state
 */
class Dashboard(override var navigation: NavigationController) : BaseDashboard, ReactiveInjectionClass {

    private val navigationState: State<NavigationController> = mutableStateOf(navigation)

    /**
     * Base directories for system user
     */
    private val baseFolderForUser: Map<String, String> = mapOf(
        "Videos" to Resources.ICON_VIDEO.path,
        "Documents" to Resources.ICON_DOCUMENT.path,
        "Images" to Resources.ICON_IMAGE.path,
        "Musics" to Resources.ICON_MUSIC.path
    )

    /**
     * Current state of the modal [PopupSetUserCategoryInFileObject] when setting a custom category
     */
    private val expandedStateModalSetCategoryPopup: MutableState<Boolean> = mutableStateOf(false)

    /**
     * Current state of the modal [PopupSetUserColorInFileObject] when setting a custom color
     */
    private val expandedStateModalSetColorPopup: MutableState<Boolean> = mutableStateOf(false)

    internal companion object {

        /**
         * Information about the directory at a certain segment of the nesting level
         */
        private val managerFileObject: MutableState<ManagerFileObject> =
            mutableStateOf(ReactiveFileObject.managerFileSystemObjects)

        private val managerFileObject_Directories: MutableState<MutableList<FileObject>> =
            mutableStateOf(mutableListOf())

        private val managerFileObject_Files: MutableState<MutableList<FileObject>> =
            mutableStateOf(mutableListOf())

        /**
         * Set information about file objects by nesting level
         */
        internal fun setManagerFileObject(managerFileObject: ManagerFileObject) {
            this.managerFileObject.value = managerFileObject

            val folders: MutableList<FileObject> = mutableListOf()
            val files: MutableList<FileObject> = mutableListOf()

            this.managerFileObject.value.objects.forEach {
                if (it.isDirectory) folders.add(it)
                else if (it.isFile) files.add(it)
            }

            managerFileObject_Directories.value = folders
            managerFileObject_Files.value = files
        }

    }

    /**
     * Event when clicking on the button to go to the section of an individual user profile
     *
     * @param screen Internal profile screen to go to
     * @param scope Defines a scope for new coroutines
     * @param sectionProfile New section in profile
     */
    private fun onClickCardSheet(screen: Screen, scope: CoroutineScope, sectionProfile: Sections) {
        scope.launch {
            ProfileUser.setAppliedScreenFromTransitionFromPast(context = ContextScreenPage(screen))
            ProfileUser.setAppliedSectionFromTransitionFromPast(section = sectionProfile)

            reactiveNavigationScreen.action(
                state = navigationState,
                route = Screen.PROFILE_SCREEN,
                objects = listOf(),
                scope = scope
            )
        }
    }

    /**
     * Rendering content on the left side of the dashboard
     */
    @Composable
    override fun renderLeftContent() {
        RenderLeftContentDashboard(
            systemBaseFolderForUser = baseFolderForUser
        )
    }

    /**
     * Rendering content on the right side of the dashboard
     */
    @Composable
    override fun renderRightContent() {
        val directories: MutableState<MutableList<FileObject>> = remember {
            managerFileObject_Directories
        }
        val files: MutableState<MutableList<FileObject>> = remember {
            managerFileObject_Files
        }

        PopupSetUserCategoryInFileObject(
            expandedState = expandedStateModalSetCategoryPopup,
            actionSetCategory = {
                // TODO: A crutch for forcing layout reflow
                directories.value = mutableListOf()
                files.value = mutableListOf()
            }
        )
        PopupSetUserColorInFileObject(
            expandedState = expandedStateModalSetColorPopup,
            actionSetColor = {
                // TODO: A crutch for forcing layout reflow
                directories.value = mutableListOf()
                files.value = mutableListOf()
            }
        )

        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()

        val modalRightSheetLayout = ModalRightSheetLayout(
            state = scaffoldState,
            modifier = Modifier
                .fillMaxSize(),
            modifierDrawerInternal = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .background(Color.White)
        )

        modalRightSheetLayout.render(
            drawerContent = {
                renderContentModalRightSheet()
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxHeight(SizeComponents.UPPER_BLOCK_LEFT_PANEL.float)
                ) {
                    TopPanelDashboard(
                        scaffoldState = scaffoldState,
                        scope = scope
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    RenderLayoutFilesObject(
                        directories = directories,
                        files = files,
                        expandedStateSetCategoryPopup = expandedStateModalSetCategoryPopup,
                        expandedStateSetColorPopup = expandedStateModalSetColorPopup
                    )

                    BreadCrumbs(
                        items = listOf(
                            BreadCrumbsItem(
                                text = "Folder 1",
                                action = {
                                    println(true)
                                }
                            ),
                            BreadCrumbsItem(
                                text = "Folder 2",
                                action = {
                                    println(true)
                                }
                            ),
                            BreadCrumbsItem(
                                text = "Folder 3",
                                action = {
                                    println(true)
                                }
                            )
                        ),
                        modifier = Modifier
                            .height(40.dp)
                            .background(Colors.BREAD_CRUMBS_BASE.color)
                            .fillMaxWidth()
                            .padding(4.dp, 8.dp)
                    ).render()
                }
            }
        }
    }

    /**
     * Renders a popup modal window to navigate to the screen state - user settings [Screen.PROFILE_SCREEN]
     */
    @Composable
    private fun renderContentModalRightSheet() {
        val baseModifierCard: Modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .padding(4.dp)
            .pointerHoverIcon(icon = PointerIcon.Hand)

        val coroutineScope: CoroutineScope = rememberCoroutineScope()

        Column {
            TitleInSectionForCardsModalSheet(Sections.MAIN_PROFILE.title)
            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                content = {
                    items(Sections.MAIN_PROFILE.sections.size) { index ->
                        CardModalSheet(
                            modifier = baseModifierCard
                        ) {
                            onClickCardSheet(
                                screen = Sections.MAIN_PROFILE.routes[index],
                                scope = coroutineScope,
                                sectionProfile = Sections.MAIN_PROFILE
                            )
                        }.render {
                            TextInCardModalSheet(Sections.MAIN_PROFILE.sections[index])
                        }
                    }
                }
            )
        }

        Divider(
            modifier = Modifier.padding(bottom = 12.dp),
            thickness = 0.dp
        )

        Column {
            TitleInSectionForCardsModalSheet(Sections.USER_CONTROL.title)
            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                content = {
                    items(Sections.USER_CONTROL.sections.size) { index ->
                        CardModalSheet(
                            modifier = baseModifierCard
                        ) {
                            onClickCardSheet(
                                screen = Sections.USER_CONTROL.routes[index],
                                scope = coroutineScope,
                                sectionProfile = Sections.USER_CONTROL
                            )
                        }.render {
                            TextInCardModalSheet(Sections.USER_CONTROL.sections[index])
                        }
                    }
                }
            )
        }

        Divider(
            modifier = Modifier.padding(bottom = 12.dp),
            thickness = 0.dp
        )

        Column {
            TitleInSectionForCardsModalSheet(Sections.USER_CUSTOMIZATION.title)
            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                content = {
                    items(Sections.USER_CUSTOMIZATION.sections.size) { index ->
                        CardModalSheet(
                            modifier = baseModifierCard
                        ) {
                            onClickCardSheet(
                                screen = Sections.USER_CUSTOMIZATION.routes[index],
                                scope = coroutineScope,
                                sectionProfile = Sections.USER_CUSTOMIZATION
                            )
                        }.render {
                            TextInCardModalSheet(Sections.USER_CUSTOMIZATION.sections[index])
                        }
                    }
                }
            )
        }
    }

}

/**
 * Text for user profile navigation element
 *
 * @param text The text to be displayed.
 */
@Composable
private fun TextInCardModalSheet(text: String = "") = Text(
    text = text,
    textAlign = TextAlign.Center,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp
)

/**
 * Header for profile settings section
 *
 * @param text The text to be displayed.
 */
@Composable
private fun TitleInSectionForCardsModalSheet(text: String = ""): Unit = Text(
    text = text,
    modifier = Modifier
        .padding(4.dp, 0.dp),
    color = Colors.TEXT.color,
    fontWeight = FontWeight.Bold
)
