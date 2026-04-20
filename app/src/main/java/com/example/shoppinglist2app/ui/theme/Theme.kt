package com.example.shoppinglist2app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary          = SkyBluePrimary,
    onPrimary        = Color.White,
    secondary        = SkyBlueTeal,
    onSecondary      = Color.White,
    background       = SkyBlueLight,
    onBackground     = SkyBlueDark,
    surface          = SkyBlueSurface,
    onSurface        = SkyBlueDark,
    surfaceVariant   = Color(0xFFD0E8F8),
    outline          = SkyBlueBorder
)

@Composable
fun ShoppingList2AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography  = Typography,
        content     = content
    )
}
