package com.example.shoppinglist2app.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppinglist2app.ui.theme.SkyBluePrimary

@Composable
fun SplashScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F9FF)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Storefront,
            contentDescription = null,
            tint = SkyBluePrimary,
            modifier = Modifier.size(52.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "ShopSathi",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            color = SkyBluePrimary
        )
        Spacer(Modifier.height(20.dp))
        SplashSkeleton()
    }
}

@Composable
private fun SplashSkeleton() {
    val transition = rememberInfiniteTransition(label = "skeleton")
    val shimmerOffset = transition.animateFloat(
        initialValue = -300f,
        targetValue = 900f,
        animationSpec = infiniteRepeatable(tween(1100, easing = LinearEasing)),
        label = "offset"
    ).value

    val shimmerBrush = Brush.linearGradient(
        colors = listOf(Color(0x1A0284C7), Color(0x550284C7), Color(0x1A0284C7)),
        start = Offset(shimmerOffset - 220f, 0f),
        end = Offset(shimmerOffset, 220f)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 56.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .height(14.dp)
                .fillMaxWidth(0.7f)
                .background(shimmerBrush, RoundedCornerShape(8.dp))
        )
        Box(
            modifier = Modifier
                .height(14.dp)
                .fillMaxWidth(0.5f)
                .background(shimmerBrush, RoundedCornerShape(8.dp))
        )
    }
}
