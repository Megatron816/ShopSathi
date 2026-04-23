package com.example.shoppinglist2app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppinglist2app.ui.components.*
import com.example.shoppinglist2app.ui.theme.*
import com.example.shoppinglist2app.viewmodel.ShoppingViewModel

@Composable
fun AnalyticsScreen(viewModel: ShoppingViewModel, onBack: () -> Unit) {
    val monthlySpend      by viewModel.monthlySpend.collectAsState()
    val categoryBreakdown by viewModel.categoryBreakdown.collectAsState()
    val topItems          by viewModel.topItems.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp)
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Default.Analytics, contentDescription = null, tint = SkyBluePrimary, modifier = Modifier.size(22.dp))
                Text("Analytics", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = SkyBlueDark)
            }
        }

        // ── Monthly spend hero ───────────────────────────────────────────────
        item {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .shadow(3.dp, RoundedCornerShape(24.dp))
                    .clip(RoundedCornerShape(24.dp))
                    .background(Brush.linearGradient(listOf(SkyBluePrimary, SkyBlueTeal)))
                    .padding(24.dp)
            ) {
                Column {
                    Text("THIS MONTH'S SPENDING", fontSize = 11.sp,
                        fontWeight = FontWeight.Bold, color = Color(0xCCFFFFFF), letterSpacing = 1.sp)
                    Spacer(Modifier.height(6.dp))
                    Text("₹${"%.2f".format(monthlySpend)}", fontSize = 36.sp,
                        fontWeight = FontWeight.Bold, color = Color.White)
                    Spacer(Modifier.height(4.dp))
                    Text("across all completed lists", fontSize = 12.sp, color = Color(0xCCFFFFFF))
                }
            }
        }

        // ── Category breakdown ────────────────────────────────────────────────
        item {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .shadow(2.dp, RoundedCornerShape(20.dp))
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .padding(18.dp)
            ) {
                SectionHeader("Spending by Category")
                if (categoryBreakdown.isEmpty()) {
                    EmptyState(Icons.Default.Inventory2, "No data yet", "Complete a shopping list to see breakdown")
                } else {
                    val maxSpend = categoryBreakdown.maxOf { it.totalSpent }.coerceAtLeast(1.0)
                    categoryBreakdown.forEach { cs ->
                        val fraction  = (cs.totalSpent / maxSpend).toFloat()
                        val catColor  = getCategoryColor(cs.category)
                        val catIcon  = getCategoryIcon(cs.category)
                        Column(modifier = Modifier.padding(vertical = 6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Icon(imageVector = catIcon, contentDescription = cs.category, tint = catColor, modifier = Modifier.size(13.dp))
                                    Text(cs.category, fontSize = 13.sp, color = SkyBlueDark)
                                }
                                Text("₹${"%.0f".format(cs.totalSpent)}", fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold, color = catColor)
                            }
                            Spacer(Modifier.height(4.dp))
                            Box(modifier = Modifier.fillMaxWidth().height(7.dp)
                                .clip(RoundedCornerShape(4.dp)).background(catColor.copy(alpha = 0.15f))) {
                                Box(modifier = Modifier.fillMaxWidth(fraction).height(7.dp)
                                    .clip(RoundedCornerShape(4.dp)).background(catColor))
                            }
                        }
                    }
                }
            }
        }

        // ── Top items ─────────────────────────────────────────────────────────
        item {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .shadow(2.dp, RoundedCornerShape(20.dp))
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .padding(18.dp)
            ) {
                SectionHeader("Most Bought Items")
                if (topItems.isEmpty()) {
                    EmptyState(Icons.Default.ShoppingCart, "No history yet", "Items appear here after shopping")
                } else {
                    topItems.forEachIndexed { idx, item ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment     = Alignment.CenterVertically
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(28.dp).clip(CircleShape).background(
                                    when (idx) { 0 -> Color(0xFFFBBF24); 1 -> Color(0xFF94A3B8); 2 -> Color(0xFFCD7C2F); else -> SkyBlueBorder }
                                ), contentAlignment = Alignment.Center) {
                                    Text("${idx + 1}", fontSize = 11.sp, fontWeight = FontWeight.Bold,
                                        color = if (idx < 3) Color.White else SkyBlueDark)
                                }
                                Text(item.itemName, fontSize = 14.sp, color = SkyBlueDark)
                            }
                            Box(modifier = Modifier.clip(RoundedCornerShape(8.dp))
                                .background(SkyBlueLight).padding(horizontal = 10.dp, vertical = 3.dp)) {
                                Text("×${item.frequency}", fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold, color = SkyBluePrimary)
                            }
                        }
                    }
                }
            }
        }
    }
}
