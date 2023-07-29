package ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.staticCompositionLocalOf

import androidx.compose.ui.graphics.Color

/**
 * Creates a complete color definition
 */
fun lightColors() {

}

/**
 * Creates a complete color definition
 */
fun darkColors(
    primary: Color = Color(0xffa5c9ca),
    primaryVariant: Color = Color(0xff395b64),
    secondary: Color = Color(0xff5c5470),
    secondaryVariant: Color = Color(0xff5c5470),
    background: Color = Color(0xFF121212),
    surface: Color = Color(0xFF121212),
    error: Color = Color(0xFFCF6679),
    onPrimary: Color = Color.Black,
    onSecondary: Color = Color.Black,
    onBackground: Color = Color.White,
    onSurface: Color = Color.White,
    onError: Color = Color.Black
): Colors = Colors(
    primary,
    primaryVariant,
    secondary,
    secondaryVariant,
    background,
    surface,
    error,
    onPrimary,
    onSecondary,
    onBackground,
    onSurface,
    onError,
    false
)

val LocalColors = staticCompositionLocalOf { darkColors() }
