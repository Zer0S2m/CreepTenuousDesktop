package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import components.cards.CardModalSheet
import components.cards.CartAdvanced
import components.fields.FieldSearch
import components.misc.Avatar
import components.modals.ModalRightSheetLayout
import enums.Colors

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
                .fillMaxWidth(0.25f)
                .background(Red)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.09f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp, 12.dp, 12.dp, 6.dp)
                        .background(LightGray)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp, 6.dp)
                        .background(LightGray)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp, 6.dp)
                        .background(LightGray)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp, 6.dp)
                        .background(LightGray)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp, 6.dp)
                        .background(LightGray)
                )
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
                        .fillMaxHeight(0.09f)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.94f)
                                .padding(12.dp)
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
                        .background(White)
                ) {
                    val list = (1..10).map { "Object $it" }

                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(200.dp),
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
                }
            }
        }
    }

    @Composable
    private fun renderContentModalRightSheet() {
        val baseModifierCard: Modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .padding(6.dp, 12.dp)
            .pointerHoverIcon(icon = PointerIcon.Hand)

        Column {
            Text(
                text = "Profile",
                modifier = Modifier.padding(bottom = 8.dp),
                color = Colors.TEXT.color,
                fontWeight = FontWeight.Bold
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                content = {
                    items(titleCardsProfile.size) { index ->
                        CardModalSheet(
                            modifier = baseModifierCard
                        ).render {
                            Text(
                                text = titleCardsProfile[index],
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Medium
                            )
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
            Text(
                text = "User control",
                modifier = Modifier.padding(bottom = 8.dp),
                color = Colors.TEXT.color,
                fontWeight = FontWeight.Bold
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                content = {
                    items(titleCardsUserControl.size) { index ->
                        CardModalSheet(
                            modifier = baseModifierCard
                        ).render {
                            Text(
                                text = titleCardsUserControl[index],
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            )
        }
    }

}