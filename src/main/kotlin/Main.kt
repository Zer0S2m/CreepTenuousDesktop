import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import screens.LoginUser

@Composable
@Preview
fun App() {
    MaterialTheme {
        val loginUser = LoginUser()
        loginUser.LoginUser()
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "CreepTenuous",
    ) {
        App()
    }
}
