package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import components.cards.CardModalSheet
import components.cards.CardPanelBaseFolderUser
import components.cards.CartAdvanced
import components.fields.FieldSearch
import components.misc.Avatar
import components.misc.BreadCrumbs
import components.misc.BreadCrumbsItem
import components.misc.SwitchPanelDashboard
import components.modals.ModalRightSheetLayout
import components.screen.BaseDashboard
import core.context.ContextScreen
import core.navigation.NavigationController
import enums.*
import enums.Colors

/**
 * The main dashboard for interacting with system file objects
 *
 * @param navigation Handler for the navigation host for changing the current screen state
 */
class Dashboard(override var navigation: NavigationController?) : BaseDashboard {

    /**
     * Base directories for system user
     */
    private val baseFolderForUser: Map<String, String> = mapOf(
        "Videos" to Resources.ICON_VIDEO.path,
        "Documents" to Resources.ICON_DOCUMENT.path,
        "Images" to Resources.ICON_IMAGE.path,
        "Musics" to Resources.ICON_MUSIC.path
    )

    private fun onClickCardSheet(screen: Screen) {
        navigation?.navigate(Screen.PROFILE_SCREEN.name)
        ProfileUser.setAppliedScreenFromTransitionFromPast(ContextScreen(screen))
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

            baseFolderForUser.forEach { (folder, icon) ->
                CardPanelBaseFolderUser(
                    text = folder,
                    isIcon = true,
                    iconPath = icon
                ).render()
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
                .background(White)
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
                        .background(White),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    val list = (1..10).map { "Object $it" }

                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(160.dp),
                        contentPadding = PaddingValues(16.dp),
                        content = {
                            items(list.size) { index ->
                                if (index < 5) {
                                    CartAdvanced(
                                        isDirectory = true,
                                        isFile = false,
                                        text = list[index]
                                    ).render()
                                } else {
                                    CartAdvanced(
                                        isDirectory = false,
                                        isFile = true,
                                        text = list[index]
                                    ).render()
                                }
                            }
                        }
                    )

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

        Column {
            renderTitleInSectionForCardsModalSheet(Sections.MAIN_PROFILE.title)
            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                content = {
                    items(Sections.MAIN_PROFILE.sections.size) { index ->
                        CardModalSheet(
                            modifier = baseModifierCard,
                            onClick = {
                                onClickCardSheet(screen = Sections.MAIN_PROFILE.routes[index])
                            }
                        ).render {
                            renderTextInCardModalSheet(Sections.MAIN_PROFILE.sections[index])
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
            renderTitleInSectionForCardsModalSheet(Sections.USER_CONTROL.title)
            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                content = {
                    items(Sections.USER_CONTROL.sections.size) { index ->
                        CardModalSheet(
                            modifier = baseModifierCard,
                            onClick = {
                                onClickCardSheet(screen = Sections.USER_CONTROL.routes[index])
                            }
                        ).render {
                            renderTextInCardModalSheet(Sections.USER_CONTROL.sections[index])
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
            renderTitleInSectionForCardsModalSheet(Sections.USER_CUSTOMIZATION.title)
            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                content = {
                    items(Sections.USER_CUSTOMIZATION.sections.size) { index ->
                        CardModalSheet(
                            modifier = baseModifierCard,
                            onClick = {
                                onClickCardSheet(screen = Sections.USER_CUSTOMIZATION.routes[index])
                            }
                        ).render {
                            renderTextInCardModalSheet(Sections.USER_CUSTOMIZATION.sections[index])
                        }
                    }
                }
            )
        }
    }

    @Composable
    private fun renderTextInCardModalSheet(text: String = "") {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
    }

    @Composable
    private fun renderTitleInSectionForCardsModalSheet(text: String = "") {
        Text(
            text = text,
            modifier = Modifier
                .padding(4.dp, 0.dp),
            color = Colors.TEXT.color,
            fontWeight = FontWeight.Bold
        )
    }

}