package com.example.shoppinglist2app.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppinglist2app.data.ShoppingItem
import com.example.shoppinglist2app.ui.components.*
import com.example.shoppinglist2app.ui.theme.*
import com.example.shoppinglist2app.viewmodel.ShoppingViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun EventDetailScreen(
    date: String,
    viewModel: ShoppingViewModel,
    onBack: () -> Unit
) {
    val items      by viewModel.itemsForSelectedDate.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val suggestions by viewModel.suggestions.collectAsState()
    val editingIds by viewModel.editingIds.collectAsState()
    val isPast    = viewModel.isPast(date)
    val isToday   = viewModel.isToday(date)
    val isFuture  = viewModel.isFuture(date)
    var showAddDialog by remember { mutableStateOf(false) }

    val formattedDate = try {
        LocalDate.parse(date).format(DateTimeFormatter.ofPattern("EEEE, MMM d yyyy"))
    } catch (_: Exception) { date }

    val statusLabel = when { isPast -> "Past Event"; isToday -> "Today"; isFuture -> "Future Event"; else -> "" }
    val (statusBg, statusColor) = when {
        isPast   -> Color(0xFFD1FAE5) to Color(0xFF065F46)
        isToday  -> SkyBluePrimary   to Color.White
        isFuture -> Color(0xFFFEF9C3) to Color(0xFF92400E)
        else     -> SkyBlueLight     to SkyBlueDark
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // ── Top bar ──────────────────────────────────────────────────────────
        Column(
            modifier = Modifier.fillMaxWidth().background(SkyBluePrimary)
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(40.dp).clip(RoundedCornerShape(12.dp))
                    .background(Color(0x33FFFFFF)).clickable(onClick = onBack),
                    contentAlignment = Alignment.Center) {
                    Text("←", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(formattedDate, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Box(modifier = Modifier.clip(RoundedCornerShape(20.dp))
                            .background(statusBg).padding(horizontal = 10.dp, vertical = 3.dp)) {
                            Text(statusLabel, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = statusColor)
                        }
                        if (isPast) Text("View only", fontSize = 11.sp, color = Color(0xCCFFFFFF))
                    }
                }
            }
        }

        // ── Body ─────────────────────────────────────────────────────────────
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("${items.size} item${if (items.size != 1) "s" else ""}",
                        fontSize = 13.sp, color = SkyBlueMedium, fontWeight = FontWeight.SemiBold)
                    if (!isPast) {
                        SkyButton("+ Add Item") { showAddDialog = true }
                    }
                }
            }

            if (items.isEmpty()) {
                item {
                    EmptyState(
                        icon     = "📅",
                        title    = if (isPast) "No items were saved for this day" else "No items yet",
                        subtitle = if (!isPast) "Tap '+ Add Item' to add one" else ""
                    )
                }
            } else {
                items(items, key = { it.id }) { item ->
                    val isEditing = item.id in editingIds
                    AnimatedContent(targetState = isEditing, label = "edit_${item.id}") { editing ->
                        if (editing && !isPast) {
                            ShoppingItemEditor(
                                item       = item,
                                categories = categories,
                                onEditComplete = { name, qty, unit, cat ->
                                    viewModel.updateItem(item.copy(name = name, quantity = qty, unit = unit, category = cat))
                                }
                            )
                        } else {
                            EventItemRow(
                                item          = item,
                                readOnly      = isPast,
                                onEditClick   = { viewModel.startEditing(item.id) },
                                onDeleteClick = { viewModel.deleteItem(item) }
                            )
                        }
                    }
                }
            }

            // Past info
            if (isPast) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color(0xFFD1FAE5))
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment     = Alignment.CenterVertically
                    ) {
                        Text("🔒", fontSize = 18.sp)
                        Column {
                            Text("Past event — view only", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF065F46))
                            Text("Items from past events are preserved for your history.",
                                fontSize = 11.sp, color = Color(0xFF10B981))
                        }
                    }
                }
            }

            // Future delete
            if (isFuture && items.isNotEmpty()) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .shadow(1.dp, RoundedCornerShape(14.dp))
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color(0xFFFEE2E2))
                            .clickable { viewModel.deleteEvent(date) }
                            .padding(14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Delete entire event", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFFEF4444))
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddItemDialog(
            categories        = categories,
            suggestions       = suggestions,
            onSuggestionQuery = { viewModel.updateSuggestionQuery(it) },
            onDismiss         = { showAddDialog = false },
            onAdd             = { name, qty, unit, cat, _ ->
                viewModel.addItemToDate(ShoppingItem(name = name, quantity = qty, unit = unit, category = cat), date)
                showAddDialog = false
            }
        )
    }
}

// ── Event item row ────────────────────────────────────────────────────────────
@Composable
fun EventItemRow(item: ShoppingItem, readOnly: Boolean, onEditClick: () -> Unit, onDeleteClick: () -> Unit) {
    val qty      = if (item.quantity % 1.0 == 0.0) item.quantity.toInt().toString() else "%.2f".format(item.quantity)
    val catColor = getCategoryColor(item.category)
    val catEmoji = getCategoryEmoji(item.category)

    Row(
        modifier = Modifier.fillMaxWidth()
            .shadow(1.dp, RoundedCornerShape(14.dp))
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
            .border(1.dp, catColor.copy(alpha = 0.3f), RoundedCornerShape(14.dp))
            .padding(12.dp),
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(modifier = Modifier.width(3.dp).height(36.dp).clip(RoundedCornerShape(2.dp)).background(catColor))
        Spacer(Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(item.name, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = SkyBlueDark)
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(catEmoji, fontSize = 11.sp)
                Text(item.category, fontSize = 11.sp, color = catColor, fontWeight = FontWeight.Medium)
            }
        }
        Box(modifier = Modifier.clip(RoundedCornerShape(10.dp)).background(SkyBlueLight)
            .padding(horizontal = 10.dp, vertical = 4.dp)) {
            Text("$qty ${item.unit}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SkyBluePrimary)
        }
        if (!readOnly) {
            Spacer(Modifier.width(6.dp))
            Box(modifier = Modifier.size(32.dp).clip(RoundedCornerShape(8.dp))
                .background(SkyBlueBorder).clickable(onClick = onEditClick), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Edit, null, tint = SkyBlueDark, modifier = Modifier.size(14.dp))
            }
            Spacer(Modifier.width(4.dp))
            Box(modifier = Modifier.size(32.dp).clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFFEE2E2)).clickable(onClick = onDeleteClick), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Delete, null, tint = Color(0xFFEF4444), modifier = Modifier.size(14.dp))
            }
        }
    }
}
