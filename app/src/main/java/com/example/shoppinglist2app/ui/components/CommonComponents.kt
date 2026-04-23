package com.example.shoppinglist2app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppinglist2app.ui.theme.*

// ── Sky Card ─────────────────────────────────────────────────────────────────
@Composable
fun SkyCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .shadow(2.dp, RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .background(SkyBlueCard)
            .border(1.dp, SkyBlueBorder, RoundedCornerShape(20.dp))
            .padding(16.dp),
        content = content
    )
}

// ── Sky Button ────────────────────────────────────────────────────────────────
@Composable
fun SkyButton(
    text: String,
    modifier: Modifier = Modifier,
    primary: Boolean = true,
    onClick: () -> Unit
) {
    val bg = if (primary)
        Brush.horizontalGradient(listOf(SkyBluePrimary, Color(0xFF0369A1)))
    else
        Brush.horizontalGradient(listOf(Color(0xFFE2E8F0), Color(0xFFCBD5E1)))
    val textColor = if (primary) Color.White else Color(0xFF475569)

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(bg)
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 18.dp)
    ) {
        Text(text, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = textColor)
    }
}

// ── Icon Round Button ─────────────────────────────────────────────────────────
@Composable
fun IconRoundBtn(icon: ImageVector, desc: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(SkyBlueBorder)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, desc, tint = SkyBlueDark, modifier = Modifier.size(20.dp))
    }
}

// ── Empty State ───────────────────────────────────────────────────────────────
@Composable
fun EmptyState(
    icon: ImageVector = Icons.Default.Inventory2,
    title: String = "Nothing here yet",
    subtitle: String = "",
    iconDescription: String? = null
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier.size(60.dp).clip(CircleShape).background(SkyBlueBorder),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, iconDescription, tint = SkyBlueDark, modifier = Modifier.size(28.dp))
        }
        Text(title, fontSize = 15.sp, color = SkyBlueMedium, fontWeight = FontWeight.Medium)
        if (subtitle.isNotEmpty()) Text(subtitle, fontSize = 12.sp, color = Color(0xFF94A3B8))
    }
}

// ── Section Header ────────────────────────────────────────────────────────────
@Composable
fun SectionHeader(text: String) {
    Text(
        text = text.uppercase(),
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp,
        color = SkyBlueMedium,
        modifier = Modifier.padding(bottom = 8.dp, top = 4.dp)
    )
}

// ── Back Button ───────────────────────────────────────────────────────────────
@Composable
fun BackButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(SkyBlueBorder)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "←",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = SkyBlueDark
        )
    }
}

// ── Priority Badge ────────────────────────────────────────────────────────────
@Composable
fun PriorityBadge(priority: String) {
    val (color, label) = when (priority) {
        "HIGH" -> PriorityHigh to "HIGH"
        "LOW"  -> PriorityLow  to "LOW"
        else   -> PriorityMedium to "MED"
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .background(color.copy(alpha = 0.15f))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(label, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = color)
    }
}
