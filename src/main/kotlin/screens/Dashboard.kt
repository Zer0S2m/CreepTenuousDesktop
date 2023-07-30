package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.components.cards.CardModalSheet
import ui.components.cards.CardPanelBaseFolderUser
import ui.components.cards.CartFileObject
import ui.components.fields.FieldSearch
import ui.components.misc.Avatar
import ui.components.misc.BreadCrumbs
import ui.components.misc.BreadCrumbsItem
import ui.components.misc.SwitchPanelDashboard
import ui.components.modals.ModalRightSheetLayout
import ui.components.base.BaseDashboard
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
                    val list1 = (1..8).map { "Object $it" }
                    val list2 = (1..6).map { "Object $it" }

                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(bottom = 28.dp)
                        ) {
                            TitleCategoryFileObject("Folders", list1.size)
                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(160.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(list1.size) { index ->
                                    CartFileObject(
                                        isDirectory = true,
                                        isFile = false,
                                        text = list1[index]
                                    ).render()
                                }
                            }
                        }

                        Column {
                            TitleCategoryFileObject("Files", list2.size)
                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(160.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(list2.size) { index ->
                                    CartFileObject(
                                        isDirectory = false,
                                        isFile = true,
                                        text = list2[index]
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

        Column {
            TitleInSectionForCardsModalSheet(Sections.MAIN_PROFILE.title)
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
                            modifier = baseModifierCard,
                            onClick = {
                                onClickCardSheet(screen = Sections.USER_CONTROL.routes[index])
                            }
                        ).render {
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
                            modifier = baseModifierCard,
                            onClick = {
                                onClickCardSheet(screen = Sections.USER_CUSTOMIZATION.routes[index])
                            }
                        ).render {
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
