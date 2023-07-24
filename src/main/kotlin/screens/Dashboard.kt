package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import components.cards.CartAdvanced

class Dashboard {

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
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Green)
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(0.dp, 12.dp, 12.dp, 12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Gray)
                        )
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
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        top = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    ),
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