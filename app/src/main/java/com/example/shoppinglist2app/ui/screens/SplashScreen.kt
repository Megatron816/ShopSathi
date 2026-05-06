package com.example.shoppinglist2app.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import kotlinx.coroutines.delay
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppinglist2app.ui.theme.SkyBlueDark
import com.example.shoppinglist2app.ui.theme.SkyBlueLight
import com.example.shoppinglist2app.ui.theme.SkyBluePrimary

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000)
        onFinished()
    }
    val infiniteTransition = rememberInfiniteTransition(label = "splashIcon")
    val iconScale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "iconScale"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SkyBlueLight),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Shopping Cart Logo with background
        Box(
            modifier = Modifier
                .size(140.dp)
                .scale(iconScale)
                .shadow(12.dp, CircleShape)
                .background(Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Shopping Cart Logo",
                tint = SkyBluePrimary,
                modifier = Modifier.size(70.dp)
            )
        }

        Spacer(Modifier.height(28.dp))

        // App Name
        Text(
            text = "ShopSathi",
            fontSize = 48.sp,
            fontWeight = FontWeight.ExtraBold,
            color = SkyBlueDark,
            letterSpacing = 1.sp
        )

        Spacer(Modifier.height(40.dp))

        // Loading Animation
        SplashLoadingAnimation()
    }
}

@Composable
private fun SplashLoadingAnimation() {
    val infiniteTransition = rememberInfiniteTransition(label = "loading")

    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = -600f,
        targetValue = 600f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer"
    )

    val shimmerBrush = Brush.linearGradient(
        colors = listOf(
            Color(0x1A0284C7),
            Color(0x550284C7),
            Color(0x1A0284C7)
        ),
        start = Offset(shimmerOffset - 220f, 0f),
        end = Offset(shimmerOffset, 220f)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Loading bar 1
        Box(
            modifier = Modifier
                .height(12.dp)
                .fillMaxWidth(0.8f)
                .background(shimmerBrush, RoundedCornerShape(6.dp))
        )

        // Loading bar 2
        Box(
            modifier = Modifier
                .height(12.dp)
                .fillMaxWidth(0.6f)
                .background(shimmerBrush, RoundedCornerShape(6.dp))
        )

        // Loading bar 3
        Box(
            modifier = Modifier
                .height(12.dp)
                .fillMaxWidth(0.7f)
                .background(shimmerBrush, RoundedCornerShape(6.dp))
        )
    }
}


