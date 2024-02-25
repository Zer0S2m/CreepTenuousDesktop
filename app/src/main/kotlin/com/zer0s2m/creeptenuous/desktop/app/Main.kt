package com.zer0s2m.creeptenuous.desktop.app

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(
            size = DpSize(width = 1280.dp, height = 740.dp)
        ),
        title = "CreepTenuous"
    ) {
        App()
    }
}
