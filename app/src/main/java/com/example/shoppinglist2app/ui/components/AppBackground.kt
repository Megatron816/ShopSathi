package com.example.shoppinglist2app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.shoppinglist2app.ui.theme.SkyBlueLight
import com.example.shoppinglist2app.ui.theme.SkyBlueSurface

// Scattered shopping icons for background texture
private data class BgIcon(
    val icon: ImageVector,
    val xBias: Float,       // -1..1 in parent width
    val yBias: Float,       // -1..1 in parent height
    val size: Float,
    val alpha: Float,
    val rotation: Float
)

private val BG_ICONS = listOf(
    BgIcon(Icons.Default.ShoppingCart, -0.90f, -0.95f, 38f, 0.07f, -12f),
    BgIcon(Icons.Default.Eco, 0.70f, -0.92f, 32f, 0.08f, 15f),
    BgIcon(Icons.Default.LocalCafe, -0.82f, -0.60f, 28f, 0.06f, -5f),
    BgIcon(Icons.Default.LunchDining, 0.60f, -0.56f, 30f, 0.07f, 20f),
    BgIcon(Icons.Default.Fastfood, 0.00f, -0.86f, 26f, 0.06f, 0f),
    BgIcon(Icons.Default.LocalDrink, 0.84f, -0.10f, 30f, 0.07f, -10f),
    BgIcon(Icons.Default.Home, -0.95f, 0.10f, 26f, 0.05f, 8f),
    BgIcon(Icons.Default.LocalFlorist, 0.50f, 0.40f, 32f, 0.07f, -18f),
    BgIcon(Icons.Default.Restaurant, -0.70f, 0.56f, 28f, 0.06f, 12f),
    BgIcon(Icons.Default.BakeryDining, 0.76f, 0.70f, 28f, 0.07f, -5f),
    BgIcon(Icons.Default.BreakfastDining, -0.20f, 0.88f, 26f, 0.05f, 10f),
    BgIcon(Icons.Default.AcUnit, 0.20f, 0.10f, 24f, 0.05f, -15f),
    BgIcon(Icons.Default.ShoppingBag, -0.40f, -0.30f, 24f, 0.04f, 5f),
)

@Composable
fun AppBackground(content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        SkyBlueLight,
                        Color(0xFFF0F9FF),
                        Color(0xFFE8F4FD)
                    )
                )
            )
    ) {
        // Decorative background icons
        BG_ICONS.forEach { bg ->
            Icon(
                imageVector = bg.icon,
                contentDescription = null,
                tint = SkyBlueSurface,
                modifier = Modifier
                    .align(BiasAlignment(bg.xBias, bg.yBias))
                    .alpha(bg.alpha)
                    .graphicsLayer(rotationZ = bg.rotation)
                    .size(bg.size.dp)
            )
        }

        content()
    }
}
