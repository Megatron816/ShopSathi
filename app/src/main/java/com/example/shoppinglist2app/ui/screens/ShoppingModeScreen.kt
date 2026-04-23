package com.example.shoppinglist2app.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppinglist2app.data.ShoppingItem
import com.example.shoppinglist2app.ui.components.getCategoryColor
import com.example.shoppinglist2app.ui.components.getCategoryIcon
import com.example.shoppinglist2app.ui.theme.*
import com.example.shoppinglist2app.viewmodel.ShoppingViewModel
import kotlin.math.sin
import kotlin.random.Random

// ── Confetti particle data ────────────────────────────────────────────────────
private data class Particle(
    val x: Float, val y: Float,
    val vx: Float, val vy: Float,
    val color: Color, val size: Float, val rotation: Float
)

private val CONFETTI_COLORS = listOf(
    Color(0xFFEF4444), Color(0xFF3B82F6), Color(0xFFFBBF24),
    Color(0xFF10B981), Color(0xFFA855F7), Color(0xFFF97316)
)

@Composable
fun ShoppingModeScreen(
    listId: Int,
    listName: String,
    viewModel: ShoppingViewModel,
    onBack: () -> Unit,
    onDone: () -> Unit
) {
    val items    by viewModel.itemsForSelectedList.collectAsState()
    val spent    by viewModel.totalSpent.collectAsState()
    val estimate by viewModel.totalEstimate.collectAsState()
    val activeLists by viewModel.activeLists.collectAsState()

    val unchecked = items.filter { !it.isChecked }
    val checked   = items.filter  { it.isChecked }
    val allDone   = items.isNotEmpty() && unchecked.isEmpty()

    // Confetti animation
    val infiniteAnim = rememberInfiniteTransition(label = "confetti")
    val confettiTime by infiniteAnim.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(3000, easing = LinearEasing)),
        label = "time"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F9FF))
    ) {
        // ── Confetti overlay ──────────────────────────────────────────────────
        if (allDone) {
            val particles = remember {
                List(60) {
                    Particle(
                        x        = Random.nextFloat(),
                        y        = Random.nextFloat() * -0.5f,
                        vx       = (Random.nextFloat() - 0.5f) * 0.3f,
                        vy       = 0.2f + Random.nextFloat() * 0.4f,
                        color    = CONFETTI_COLORS[it % CONFETTI_COLORS.size],
                        size     = 8f + Random.nextFloat() * 12f,
                        rotation = Random.nextFloat() * 360f
                    )
                }
            }
            Canvas(modifier = Modifier.fillMaxSize()) {
                particles.forEach { p ->
                    val t  = (confettiTime + p.x) % 1f
                    val cx = (p.x + p.vx * t + sin(t * 6f + p.rotation) * 0.05f) * size.width
                    val cy = (p.y + p.vy * t) * size.height
                    if (cy < size.height + p.size) {
                        rotate(p.rotation + confettiTime * 360f, Offset(cx, cy)) {
                            drawRect(
                                color    = p.color.copy(alpha = 0.85f),
                                topLeft  = Offset(cx - p.size / 2f, cy - p.size / 2f),
                                size     = androidx.compose.ui.geometry.Size(p.size, p.size * 0.6f)
                            )
                        }
                    }
                }
            }
        }

        Column(modifier = Modifier.fillMaxSize()) {
            // ── Top bar ───────────────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SkyBluePrimary)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size(40.dp).clip(RoundedCornerShape(12.dp))
                        .background(Color(0x33FFFFFF)).clickable(onClick = onBack),
                        contentAlignment = Alignment.Center) {
                        Text("←", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(listName, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Text("${checked.size} / ${items.size} checked", fontSize = 12.sp, color = Color(0xCCFFFFFF))
                    }

                    Box(modifier = Modifier.size(40.dp).clip(RoundedCornerShape(12.dp))
                        .background(Color(0x33FFFFFF)).clickable { viewModel.uncheckAll(listId) },
                        contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Refresh, "Reset", tint = Color.White, modifier = Modifier.size(20.dp))
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Progress bar
                val progress = if (items.isEmpty()) 0f else checked.size.toFloat() / items.size
                LinearProgressIndicator(
                    progress   = { progress },
                    modifier   = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                    color      = Color(0xFF86EFAC),
                    trackColor = Color(0x44FFFFFF)
                )
                Spacer(Modifier.height(6.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("${(progress * 100).toInt()}% done", fontSize = 12.sp, color = Color(0xCCFFFFFF))
                    if (estimate > 0)
                        Text("₹${"%.0f".format(spent)} / ₹${"%.0f".format(estimate)}",
                            fontSize = 12.sp, color = Color(0xFFFBBF24), fontWeight = FontWeight.Bold)
                }
            }

            // ── Items ─────────────────────────────────────────────────────────
            LazyColumn(
                modifier = Modifier.weight(1f).padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Unchecked items — BIG checkboxes, tap to check
                items(unchecked, key = { it.id }) { item ->
                    ShoppingModeItem(item = item, onCheck = { viewModel.checkItem(item.id, true) })
                }

                // Checked section — moves down after check
                if (checked.isNotEmpty()) {
                    item {
                        Row(
                            modifier = Modifier.padding(top = 8.dp, bottom = 2.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(modifier = Modifier.weight(1f).height(1.dp).background(SkyBlueBorder))
                            Text("DONE (${checked.size})", fontSize = 10.sp, color = SkyBlueMedium,
                                fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                            Box(modifier = Modifier.weight(1f).height(1.dp).background(SkyBlueBorder))
                        }
                    }
                    items(checked, key = { "c_${it.id}" }) { item ->
                        ShoppingModeItem(item = item, onCheck = { viewModel.checkItem(item.id, false) })
                    }
                }

                item { Spacer(Modifier.height(120.dp)) }
            }
        }

        // ── All Done Banner ───────────────────────────────────────────────────
        AnimatedVisibility(
            visible  = allDone,
            enter    = slideInVertically { it } + fadeIn(),
            modifier = Modifier.align(Alignment.BottomCenter).padding(20.dp)
        ) {
            val list = activeLists.find { it.id == listId }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.Celebration, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(48.dp))
                Spacer(Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(SkyBluePrimary)
                        .clickable {
                            list?.let { viewModel.markListDone(it, items) }
                            onDone()
                        }
                        .padding(18.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("All done! Archive list →", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

// ── Shopping Mode Item — big checkbox, category dot, running price ─────────────
@Composable
fun ShoppingModeItem(item: ShoppingItem, onCheck: () -> Unit) {
    val qty      = if (item.quantity % 1.0 == 0.0) item.quantity.toInt().toString() else "%.2f".format(item.quantity)
    val catColor = getCategoryColor(item.category)
    val catIcon = getCategoryIcon(item.category)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(if (item.isChecked) Color(0xFFF1F5F9) else Color.White)
            .border(
                1.dp,
                if (item.isChecked) catColor.copy(alpha = 0.2f) else catColor.copy(alpha = 0.5f),
                RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onCheck)
            .padding(14.dp),
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // BIG checkbox
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(if (item.isChecked) catColor else Color.White)
                .border(2.dp, catColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (item.isChecked)
                Icon(Icons.Default.Check, null, tint = Color.White, modifier = Modifier.size(18.dp))
        }

        // Category accent line
        Box(modifier = Modifier.width(3.dp).height(36.dp).clip(RoundedCornerShape(2.dp)).background(catColor))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                item.name,
                fontSize   = 16.sp,
                fontWeight = FontWeight.Medium,
                color      = if (item.isChecked) Color(0xFF94A3B8) else SkyBlueDark,
                textDecoration = if (item.isChecked) TextDecoration.LineThrough else TextDecoration.None
            )
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = catIcon, contentDescription = item.category, tint = catColor, modifier = Modifier.size(11.dp))
                Text("$qty ${item.unit}  ·  ${item.category}", fontSize = 12.sp, color = Color(0xFF94A3B8))
            }
        }

        // Running price
        if (item.price > 0 && !item.isChecked) {
            Text("₹${"%.0f".format(item.price * item.quantity)}",
                fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SkyBluePrimary)
        }
    }
}
