package com.zer0s2m.creeptenuous.desktop.ui.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

/**
 * Creates a complete color definition
 */
fun darkColors(
    primary: Color = Color(0xffa5c9ca),
    primaryVariant: Color = Color(0xff395b64),
    secondary: Color = Color(0xffa5c9ca),
    secondaryVariant: Color = Color(0xff395b64),
    background: Color = Color(red = 48, green = 48, blue = 48),
    surface: Color = Color(red = 48, green = 48, blue = 48),
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
