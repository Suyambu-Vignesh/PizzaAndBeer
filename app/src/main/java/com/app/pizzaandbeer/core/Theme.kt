package com.app.pizzaandbeer.core

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val darkColorPalette =
    darkColors(
        primary = Color(255, 255, 255),
        onPrimary = Color(0, 0, 0),
        background = Color(red = 245, blue = 245, green = 245),
        secondary = Color(240, 240, 240),
        onSecondary = Color(30, 30, 30),
        surface = Color(255, 255, 255),
        onSurface = Color(0, 0, 0),
        error = Color(190, 190, 190),
        onError = Color(60, 60, 60),
    )

private val lightColorPalette =
    darkColors(
        primary = Color(255, 255, 255),
        onPrimary = Color(0, 0, 0),
        background = Color(red = 245, blue = 245, green = 245),
        secondary = Color(240, 240, 240),
        onSecondary = Color(30, 30, 30),
        surface = Color(255, 255, 255),
        onSurface = Color(0, 0, 0),
        error = Color(190, 190, 190),
        onError = Color(60, 60, 60),
    )

// Sample font families
val Roboto = FontFamily.Default

// Sample Typography for Material 3
val typography =
    Typography(
        h1 =
            TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.Bold,
                fontSize = 45.sp,
            ),
        h2 =
            TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
            ),
        h3 =
            TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
            ),
        h4 =
            TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
            ),
        h5 =
            TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            ),
        h6 =
            TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
            ),
        subtitle1 =
            TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
            ),
        subtitle2 =
            TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
            ),
        body1 =
            TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
            ),
        body2 =
            TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
            ),
        button =
            TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
            ),
        caption =
            TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
            ),
        overline =
            TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
            ),
    )

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors =
            if (darkTheme) {
                darkColorPalette
            } else {
                lightColorPalette
            },
        typography = typography,
        shapes = Shapes(),
        content,
    )
}
