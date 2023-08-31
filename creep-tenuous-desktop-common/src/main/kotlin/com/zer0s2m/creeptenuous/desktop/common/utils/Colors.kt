package com.zer0s2m.creeptenuous.desktop.common.utils

import com.zer0s2m.creeptenuous.desktop.common.dto.ConverterColor
import com.zer0s2m.creeptenuous.desktop.common.exceptions.InvalidFormatHerColorException

/**
 * Convert color from `hex` to `rgb` format
 *
 * @param hex Dirty color as a string
 * @return Converted color
 * @throws InvalidFormatHerColorException Invalid color palette format - hex
 */
fun colorConvertHexToRgb(hex: String): ConverterColor {
    var cleanHex = hex

    if (cleanHex[0] == '#') {
        cleanHex = cleanHex.drop(1)
    }

    if (cleanHex.length == 6) {
        return ConverterColor(
            red = Integer.parseInt(cleanHex.substring(0, 2), 16),
            green = Integer.parseInt(cleanHex.substring(2, 4), 16),
            blue = Integer.parseInt(cleanHex.substring(4, 6), 16),
        )
    } else if (cleanHex.length == 3) {
        return ConverterColor(
            red = Integer.parseInt(cleanHex.substring(0, 1).repeat(1), 16),
            green = Integer.parseInt(cleanHex.substring(1, 2).repeat(1), 16),
            blue = Integer.parseInt(cleanHex.substring(2, 3).repeat(1), 16),
        )
    }

    throw InvalidFormatHerColorException("Invalid color format - hex")
}
