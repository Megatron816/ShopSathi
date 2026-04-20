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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppinglist2app.data.ShoppingItem
import com.example.shoppinglist2app.ui.components.*
import com.example.shoppinglist2app.ui.theme.*
import com.example.shoppinglist2app.viewmodel.ShoppingViewModel

@Composable
fun ListDetailScreen(
    viewModel: ShoppingViewModel,
    listId: Int,
    listName: String,
    onStartShopping: () -> Unit,
    onBack: () -> Unit
) {
    val sItems     by viewModel.itemsForSelectedList.collectAsState()
    val editingIds by viewModel.editingIds.collectAsState()
    val spent      by viewModel.totalSpent.collectAsState()
    val estimate   by viewModel.totalEstimate.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val suggestions by viewModel.suggestions.collectAsState()
    val activeLists by viewModel.activeLists.collectAsState()
    val list = activeLists.find { it.id == listId }

    var showAddDialog    by remember { mutableStateOf(false) }
    var showSaveTemplate by remember { mutableStateOf(false) }
    var showMenu         by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        // ── Top Bar ──────────────────────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(listOf(SkyBluePrimary, SkyBlueTeal))
                )
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Box(
                        modifier = Modifier.size(38.dp).clip(RoundedCornerShape(10.dp))
                            .background(Color(0x33FFFFFF)).clickable(onClick = onBack),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("←", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                    Column {
                        Text(listName, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        if (list != null && list.totalBudget > 0)
                            Text("Budget ₹${"%.0f".format(list.totalBudget)}  ·  Spent ₹${"%.0f".format(spent)}",
                                fontSize = 11.sp, color = Color(0xCCFFFFFF))
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Box {
                        Box(modifier = Modifier.size(38.dp).clip(RoundedCornerShape(10.dp))
                            .background(Color(0x33FFFFFF)).clickable { showMenu = true },
                            contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.MoreVert, null, tint = Color.White, modifier = Modifier.size(18.dp))
                        }
                        DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                            DropdownMenuItem(text = { Text("Save as template") }, onClick = {
                                showMenu = false; showSaveTemplate = true })
                            DropdownMenuItem(text = { Text("Share list") }, onClick = {
                                showMenu = false; viewModel.shareList(listName, sItems) })
                        }
                    }
                }
            }

            // Budget progress bar
            if (list != null && list.totalBudget > 0 && estimate > 0) {
                val fraction = (estimate / list.totalBudget).toFloat().coerceAtMost(1f)
                val barColor = if (fraction > 0.9f) Color(0xFFEF4444) else Color(0xFF86EFAC)
                Spacer(Modifier.height(12.dp))
                LinearProgressIndicator(
                    progress     = { fraction },
                    modifier     = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                    color        = barColor,
                    trackColor   = Color(0x33FFFFFF)
                )
                Spacer(Modifier.height(4.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Est: ₹${"%.0f".format(estimate)}", fontSize = 11.sp, color = Color(0xCCFFFFFF))
                    Text("Remaining: ₹${"%.0f".format((list.totalBudget - estimate).coerceAtLeast(0.0))}",
                        fontSize = 11.sp, color = barColor)
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
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    SkyButton("+ Add Item", Modifier.weight(1f)) { showAddDialog = true }
                    SkyButton("🛒  Shop", Modifier.weight(1f), primary = false) { onStartShopping() }
                }
            }

            if (sItems.isEmpty()) {
                item { EmptyState("📋", "No items yet", "Tap '+ Add Item' to get started") }
            } else {
                item { SectionHeader("${sItems.size} item${if (sItems.size != 1) "s" else ""}") }
                items(sItems, key = { it.id }) { item ->
                    val isEditing = item.id in editingIds
                    AnimatedContent(targetState = isEditing, label = "edit_${item.id}") { editing ->
                        if (editing) {
                            ShoppingItemEditor(
                                item       = item,
                                categories = categories,
                                onEditComplete = { name, qty, unit, cat ->
                                    viewModel.updateItem(item.copy(name = name, quantity = qty, unit = unit, category = cat))
                                }
                            )
                        } else {
                            ListItemRow(
                                item          = item,
                                onEditClick   = { viewModel.startEditing(item.id) },
                                onDeleteClick = { viewModel.deleteItem(item) }
                            )
                        }
                    }
                }
            }
        }
    }

    // ── Add Item Dialog ───────────────────────────────────────────────────────
    if (showAddDialog) {
        AddItemDialog(
            categories        = categories,
            suggestions       = suggestions,
            onSuggestionQuery = { viewModel.updateSuggestionQuery(it) },
            onDismiss         = { showAddDialog = false },
            onAdd             = { name, qty, unit, cat, price ->
                viewModel.addItem(ShoppingItem(name = name, quantity = qty, unit = unit,
                    category = cat, price = price, listId = listId))
                showAddDialog = false
            }
        )
    }

    // ── Save Template Dialog ──────────────────────────────────────────────────
    if (showSaveTemplate) {
        var tName by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showSaveTemplate = false },
            containerColor   = Color.White,
            shape            = RoundedCornerShape(24.dp),
            title  = { Text("Save as Template", fontWeight = FontWeight.Bold, color = SkyBlueDark) },
            text   = {
                OutlinedTextField(value = tName, onValueChange = { tName = it },
                    label = { Text("Template name") }, singleLine = true,
                    modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
            },
            confirmButton = {
                Row(Modifier.fillMaxWidth().padding(4.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    SkyButton("Save", Modifier.weight(1f)) {
                        if (tName.isNotBlank()) { viewModel.saveAsTemplate(tName.trim(), sItems); showSaveTemplate = false }
                    }
                    SkyButton("Cancel", Modifier.weight(1f), primary = false) { showSaveTemplate = false }
                }
            }
        )
    }
}

// ── Single list item row with category color ──────────────────────────────────
@Composable
fun ListItemRow(
    item: ShoppingItem,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val qty  = if (item.quantity % 1.0 == 0.0) item.quantity.toInt().toString() else "%.2f".format(item.quantity)
    val catColor = getCategoryColor(item.category)
    val catEmoji = getCategoryEmoji(item.category)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(1.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, catColor.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
            .padding(12.dp),
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Category color bar
        Box(
            modifier = Modifier
                .width(4.dp).height(42.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(catColor)
        )
        Spacer(Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(item.name, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = SkyBlueDark)
                PriorityBadge(item.priority)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(catEmoji, fontSize = 11.sp)
                Text(item.category, fontSize = 11.sp, color = catColor, fontWeight = FontWeight.Medium)
                if (item.price > 0)
                    Text("· ₹${"%.0f".format(item.price)}", fontSize = 11.sp, color = Color(0xFF94A3B8))
            }
        }

        // Qty pill
        Box(
            modifier = Modifier.clip(RoundedCornerShape(12.dp))
                .background(SkyBlueLight)
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text("$qty ${item.unit}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SkyBluePrimary)
        }
        Spacer(Modifier.width(6.dp))

        // Edit
        Box(modifier = Modifier.size(32.dp).clip(RoundedCornerShape(8.dp))
            .background(SkyBlueBorder).clickable(onClick = onEditClick),
            contentAlignment = Alignment.Center) {
            Icon(Icons.Default.Edit, null, tint = SkyBlueDark, modifier = Modifier.size(15.dp))
        }
        Spacer(Modifier.width(5.dp))
        // Delete
        Box(modifier = Modifier.size(32.dp).clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFFEE2E2)).clickable(onClick = onDeleteClick),
            contentAlignment = Alignment.Center) {
            Icon(Icons.Default.Delete, null, tint = Color(0xFFEF4444), modifier = Modifier.size(15.dp))
        }
    }
}
