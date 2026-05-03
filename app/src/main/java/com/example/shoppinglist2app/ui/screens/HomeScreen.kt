package com.example.shoppinglist2app.ui.screens

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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppinglist2app.data.ShoppingList
import com.example.shoppinglist2app.ui.components.*
import com.example.shoppinglist2app.ui.theme.*
import com.example.shoppinglist2app.viewmodel.ShoppingViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen(
    viewModel: ShoppingViewModel,
    onOpenList: (Int) -> Unit
) {
    val activeLists   by viewModel.activeLists.collectAsState()
    val archivedLists by viewModel.archivedLists.collectAsState()
    val monthlySpend  by viewModel.monthlySpend.collectAsState()

    var showCreateDialog by remember { mutableStateOf(false) }
    var showArchived     by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp)
    ) {
        // ── Hero Header ─────────────────────────────────────────────────────
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(SkyBluePrimary, Color(0xFF0369A1), SkyBlueTeal)
                        )
                    )
                    .padding(20.dp)
            ) {
                Column {
                    Text("ShopSathi", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("Your smart shopping companion", fontSize = 12.sp, color = Color(0xCCFFFFFF))
                    Spacer(Modifier.height(14.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        StatBubble("${activeLists.size}", "Active Lists")
                        StatBubble("₹${"%.0f".format(monthlySpend)}", "This Month")
                    }
                }
            }
        }

        // ── Quick Create ─────────────────────────────────────────────────────
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(3.dp, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .clickable { showCreateDialog = true }
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.size(32.dp).clip(CircleShape)
                            .background(SkyBluePrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Add, "New", tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                    Spacer(Modifier.width(10.dp))
                    Text("Create New Shopping List", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = SkyBlueDark)
                }
            }
        }

        // ── Active Lists Header ──────────────────────────────────────────────
        item { SectionHeader("Active Lists (${activeLists.size})") }

        // ── List Cards ──────────────────────────────────────────────────────
        if (activeLists.isEmpty()) {
            item {
                EmptyState(
                    icon     = Icons.Default.Inventory2,
                    title    = "No shopping lists yet",
                    subtitle = "Tap 'Create New List' to get started"
                )
            }
        } else {
            items(activeLists, key = { it.id }) { list ->
                ListCard(
                    list      = list,
                    onClick   = { onOpenList(list.id) },
                    onArchive = { viewModel.archiveList(list.id) },
                    onDelete  = { viewModel.deleteList(list.id) }
                )
            }
        }

        // ── Archived Section ─────────────────────────────────────────────────
        if (archivedLists.isNotEmpty()) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFE2E8F0))
                        .clickable { showArchived = !showArchived }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Archived (${archivedLists.size})", fontSize = 13.sp, color = Color(0xFF64748B))
                    Icon(
                        if (showArchived) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        null, tint = Color(0xFF64748B), modifier = Modifier.size(18.dp)
                    )
                }
            }
            if (showArchived) {
                items(archivedLists, key = { "arch_${it.id}" }) { list ->
                    ListCard(
                        list       = list,
                        isArchived = true,
                        onClick    = { onOpenList(list.id) },
                        onRestore  = { viewModel.restoreList(list.id) },
                        onDelete   = { viewModel.deleteList(list.id) }
                    )
                }
            }
        }
    }

    // ── Create Dialog ─────────────────────────────────────────────────────────
    if (showCreateDialog) {
        CreateListDialog(
            onDismiss = { showCreateDialog = false },
            onCreate  = { name, budget ->
                viewModel.createList(name, budget)
                showCreateDialog = false
            }
        )
    }
}

// ── Stat bubble in hero ───────────────────────────────────────────────────────
@Composable
private fun StatBubble(value: String, label: String) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0x33FFFFFF))
            .padding(horizontal = 14.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Text(label, fontSize = 10.sp, color = Color(0xCCFFFFFF))
    }
}

// ── List Card ─────────────────────────────────────────────────────────────────
@Composable
fun ListCard(
    list: ShoppingList,
    isArchived: Boolean = false,
    onClick: () -> Unit,
    onArchive: (() -> Unit)? = null,
    onRestore: (() -> Unit)? = null,
    onDelete: () -> Unit
) {
    val df = remember { SimpleDateFormat("MMM d, yyyy", Locale.getDefault()) }
    var showMenu by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(if (isArchived) 0.dp else 2.dp, RoundedCornerShape(18.dp))
            .clip(RoundedCornerShape(18.dp))
            .background(if (isArchived) Color(0xFFEEF2FF) else Color.White)
            .border(1.dp, SkyBlueBorder, RoundedCornerShape(18.dp))
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Left accent bar
        Box(
            modifier = Modifier
                .width(4.dp).height(40.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(if (isArchived) Color(0xFFCBD5E1) else SkyBluePrimary)
        )
        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                list.name,
                fontSize   = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color      = if (isArchived) Color(0xFF94A3B8) else SkyBlueDark
            )
            Text(
                df.format(Date(list.createdAt)),
                fontSize = 11.sp, color = Color(0xFF94A3B8)
            )
            if (list.totalBudget > 0) {
                Text(
                    "Budget: ₹${"%.0f".format(list.totalBudget)}",
                    fontSize = 11.sp, color = SkyBlueTeal, fontWeight = FontWeight.Medium
                )
            }
        }

        if (isArchived) {
            Box(
                modifier = Modifier.clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFE2E8F0)).padding(horizontal = 8.dp, vertical = 4.dp)
            ) { Text("Archived", fontSize = 10.sp, color = Color(0xFF94A3B8)) }
            Spacer(Modifier.width(4.dp))
        }


        Box {
            IconButton(onClick = { showMenu = true }) {
                Icon(Icons.Default.MoreVert, null, tint = Color(0xFF94A3B8))
            }
            DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                if (!isArchived && onArchive != null)
                    DropdownMenuItem(text = { Text("Archive") }, onClick = { showMenu = false; onArchive() })
                if (isArchived && onRestore != null)
                    DropdownMenuItem(text = { Text("Restore") }, onClick = { showMenu = false; onRestore() })
                DropdownMenuItem(
                    text = { Text("Delete", color = Color.Red) },
                    onClick = { showMenu = false; onDelete() }
                )
            }
        }
    }
}

// ── Create List Dialog ────────────────────────────────────────────────────────
@Composable
fun CreateListDialog(onDismiss: () -> Unit, onCreate: (String, Double) -> Unit) {
    var name   by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor   = Color.White,
        shape            = RoundedCornerShape(24.dp),
        title = {
            Text("New Shopping List", fontWeight = FontWeight.Bold, color = SkyBlueDark)
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value         = name,
                    onValueChange = { name = it },
                    label         = { Text("List name") },
                    placeholder   = { Text("e.g. Weekly Grocery") },
                    singleLine    = true,
                    modifier      = Modifier.fillMaxWidth(),
                    shape         = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value         = budget,
                    onValueChange = { budget = it },
                    label         = { Text("Budget ₹ (optional)") },
                    singleLine    = true,
                    modifier      = Modifier.fillMaxWidth(),
                    shape         = RoundedCornerShape(12.dp),
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal
                    )
                )
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SkyButton("Create", Modifier.weight(1f)) {
                    if (name.isNotBlank()) onCreate(name.trim(), budget.toDoubleOrNull() ?: 0.0)
                }
                SkyButton("Cancel", Modifier.weight(1f), primary = false) { onDismiss() }
            }
        }
    )
}
