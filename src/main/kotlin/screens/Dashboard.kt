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
import enums.Colors
import enums.Resources

class Dashboard {

    /**
     * List of map names for drawing components for user interaction
     */
    private val titleCardsProfile: List<String> = listOf(
        "File object distribution settings",
        "Settings",
        "Viewing granted rights"
    )

    /**
     * List of map names for drawing components for user interaction
     */
    private val titleCardsUserControl: List<String> = listOf(
        "List of users",
        "User management"
    )

    /**
     * List of map names for drawing components for user interaction
     */
    private val titleCardsUserCustomization: List<String> = listOf(
        "Categories",
        "Colors"
    )

    /**
     * Base directories for system user
     */
    private val baseFolderForUser: Map<String, String> = mapOf(
        "Videos" to Resources.ICON_VIDEO.path,
        "Documents" to Resources.ICON_DOCUMENT.path,
        "Images" to Resources.ICON_IMAGE.path,
        "Musics" to Resources.ICON_MUSIC.path
    )

    @Composable
    fun Dashboard() {
        val scaffoldState: ScaffoldState = rememberScaffoldState()

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
                    createLeftContent()
                    createRightContent()
                }
            }
        }
    }

    /**
     * Rendering content on the left side of the dashboard
     */
    @Composable
    private fun createLeftContent() {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.225f)
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
    private fun createRightContent() {
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
                        .fillMaxHeight(0.075f)
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
            .padding(4.dp,)
            .pointerHoverIcon(icon = PointerIcon.Hand)

        Column {
            renderTitleInSectionForCardsModalSheet("Profile")
            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                content = {
                    items(titleCardsProfile.size) { index ->
                        CardModalSheet(
                            modifier = baseModifierCard
                        ).render {
                            renderTextInCardModalSheet(titleCardsProfile[index])
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
            renderTitleInSectionForCardsModalSheet("User control")
            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                content = {
                    items(titleCardsUserControl.size) { index ->
                        CardModalSheet(
                            modifier = baseModifierCard
                        ).render {
                            renderTextInCardModalSheet(titleCardsUserControl[index])
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
            renderTitleInSectionForCardsModalSheet("Customization")
            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                content = {
                    items(titleCardsUserCustomization.size) { index ->
                        CardModalSheet(
                            modifier = baseModifierCard
                        ).render {
                            renderTextInCardModalSheet(titleCardsUserCustomization[index])
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