package com.example.shoppinglist2app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import com.example.shoppinglist2app.ui.theme.SkyBlueLight
import com.example.shoppinglist2app.ui.theme.SkyBlueSurface

// Scattered food emojis for background texture
private data class BgEmoji(
    val emoji: String,
    val xFraction: Float,   // 0..1 of width
    val yFraction: Float,   // 0..1 of height
    val size: Float,
    val alpha: Float,
    val rotation: Float
)

private val BG_EMOJIS = listOf(
    BgEmoji("🛒", 0.05f, 0.03f, 38f, 0.07f, -12f),
    BgEmoji("🥦", 0.85f, 0.05f, 32f, 0.08f, 15f),
    BgEmoji("🥛", 0.10f, 0.20f, 28f, 0.06f, -5f),
    BgEmoji("🥩", 0.80f, 0.22f, 30f, 0.07f, 20f),
    BgEmoji("🍿", 0.50f, 0.08f, 26f, 0.06f, 0f),
    BgEmoji("🧃", 0.92f, 0.45f, 30f, 0.07f, -10f),
    BgEmoji("🧹", 0.03f, 0.55f, 26f, 0.05f, 8f),
    BgEmoji("🥑", 0.75f, 0.70f, 32f, 0.07f, -18f),
    BgEmoji("🍎", 0.15f, 0.78f, 28f, 0.06f, 12f),
    BgEmoji("🧀", 0.88f, 0.85f, 28f, 0.07f, -5f),
    BgEmoji("🍞", 0.40f, 0.92f, 26f, 0.05f, 10f),
    BgEmoji("🧊", 0.60f, 0.55f, 24f, 0.05f, -15f),
    BgEmoji("🍋", 0.30f, 0.35f, 24f, 0.04f, 5f),
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
        // Decorative background emojis
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val w = maxWidth
            val h = maxHeight
            BG_EMOJIS.forEach { bg ->
                Text(
                    text = bg.emoji,
                    fontSize = bg.size.sp,
                    modifier = Modifier
                        .offset(x = w * bg.xFraction, y = h * bg.yFraction)
                        .alpha(bg.alpha)
                )
            }
        }

        content()
    }
}
