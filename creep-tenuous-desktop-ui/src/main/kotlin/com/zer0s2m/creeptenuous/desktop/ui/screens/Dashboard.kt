package com.zer0s2m.creeptenuous.desktop.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.zer0s2m.creeptenuous.desktop.core.context.ContextScreen
import com.zer0s2m.creeptenuous.desktop.core.navigation.actions.reactiveNavigationScreen
import com.zer0s2m.creeptenuous.desktop.navigation.NavigationController
import com.zer0s2m.creeptenuous.desktop.reactive.models.ReactiveFileObject
import com.zer0s2m.creeptenuous.desktop.ui.components.base.BaseDashboard
import com.zer0s2m.creeptenuous.desktop.ui.components.cards.CardModalSheet
import com.zer0s2m.creeptenuous.desktop.ui.components.cards.CardPanelBaseFolderUser
import com.zer0s2m.creeptenuous.desktop.ui.components.cards.CartFileObject
import com.zer0s2m.creeptenuous.desktop.ui.components.fields.FieldSearch
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.Avatar
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.BreadCrumbs
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.BreadCrumbsItem
import com.zer0s2m.creeptenuous.desktop.ui.components.misc.SwitchPanelDashboard
import com.zer0s2m.creeptenuous.desktop.ui.components.modals.ModalRightSheetLayout
import com.zer0s2m.creeptenuous.desktop.ui.misc.Colors
import com.zer0s2m.creeptenuous.desktop.ui.misc.float
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * The main dashboard for interacting with system file objects
 *
 * @param navigation Handler for the navigation host for changing the current screen state
 */
class Dashboard(override var navigation: NavigationController) : BaseDashboard {

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
     * Event when clicking on the button to go to the section of an individual user profile
     *
     * @param screen Internal profile screen to go to
     * @param scope Defines a scope for new coroutines
     * @param sectionProfile New section in profile
     */
    private fun onClickCardSheet(screen: Screen, scope: CoroutineScope, sectionProfile: Sections) {
        scope.launch {
            ProfileUser.setAppliedScreenFromTransitionFromPast(context = ContextScreen(screen))
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
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(SizeComponents.LEFT_PANEL_DASHBOARD.float)
        ) {
            SwitchPanelDashboard()
                .render()

            Column {
                baseFolderForUser.forEach { (folder, icon) ->
                    CardPanelBaseFolderUser(
                        text = folder,
                        isIcon = true,
                        iconPath = icon
                    ).render()
                }
            }
        }
    }

    /**
     * Rendering content on the right side of the dashboard
     */
    @Composable
    override fun renderRightContent() {
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
                    Row(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.94f)
                                .padding(0.dp, 12.dp, 12.dp, 12.dp)
                        ) {
                            FieldSearch().render()
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(0.dp, 12.dp, 12.dp, 12.dp)
                        ) {
                            Avatar(
                                stateScaffold = scaffoldState,
                                scope = scope
                            ).render()
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    val fileObjects: MutableState<ManagerFileObject?> = rememberSaveable {
                        mutableStateOf(ReactiveFileObject.managerFileSystemObjects)
                    }
                    val folders: MutableState<MutableList<FileObject>> = rememberSaveable {
                        mutableStateOf(mutableListOf())
                    }
                    val files: MutableState<MutableList<FileObject>> = rememberSaveable {
                        mutableStateOf(mutableListOf())
                    }
                    if (fileObjects.value != null) {
                        fileObjects.value!!.objects.forEach {
                            if (it.isDirectory) folders.value.add(it)
                            else if (it.isFile) files.value.add(it)
                        }
                    }

                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(bottom = 28.dp)
                        ) {
                            TitleCategoryFileObject("Folders", folders.value.size)
                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(160.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(folders.value.size) { index ->
                                    CartFileObject(
                                        isDirectory = true,
                                        isFile = false,
                                        text = folders.value[index].realName,
                                        color = folders.value[index].color
                                    ).render()
                                }
                            }
                        }

                        Column {
                            TitleCategoryFileObject("Files", files.value.size)
                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(160.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(files.value.size) { index ->
                                    CartFileObject(
                                        isDirectory = false,
                                        isFile = true,
                                        text = files.value[index].realName
                                    ).render()
                                }
                            }
                        }
                    }

                    BreadCrumbs(
                        items = listOf(
                            BreadCrumbsItem(text = "Folder 1"),
                            BreadCrumbsItem(text = "Folder 2"),
                            BreadCrumbsItem(text = "Folder 3")
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
 * Base title for file object category
 *
 * @param text The text to be displayed
 * @param size Count objects
 */
@Composable
private fun TitleCategoryFileObject(text: String, size: Int = 0): Unit = Text(
    text = "$text ($size)",
    fontWeight = FontWeight.SemiBold,
    color = Color.Black,
    modifier = Modifier
        .padding(bottom = 12.dp)
)

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
