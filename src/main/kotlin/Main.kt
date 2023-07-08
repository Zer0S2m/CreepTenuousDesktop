import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import app.App

fun main() = application {
    val windowState = rememberWindowState(
        size = DpSize(width = 1200.dp, height = 740.dp)
    )

    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        title = "CreepTenuous"
    ) {
        App()
    }
}
