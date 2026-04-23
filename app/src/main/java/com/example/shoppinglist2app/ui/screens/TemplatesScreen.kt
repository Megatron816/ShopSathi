package com.example.shoppinglist2app.ui.screens

import androidx.compose.foundation.background
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
import com.example.shoppinglist2app.data.ListTemplate
import com.example.shoppinglist2app.ui.components.*
import com.example.shoppinglist2app.ui.theme.*
import com.example.shoppinglist2app.viewmodel.ShoppingViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TemplatesScreen(
    viewModel: ShoppingViewModel,
    onBack: () -> Unit,
    onTemplateLoaded: () -> Unit
) {
    val templates by viewModel.templates.collectAsState()
    val df = remember { SimpleDateFormat("MMM d, yyyy", Locale.getDefault()) }
    var showLoadDialog by remember { mutableStateOf<ListTemplate?>(null) }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp)
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Default.Description, contentDescription = null, tint = SkyBluePrimary, modifier = Modifier.size(22.dp))
                Text("Templates", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = SkyBlueDark)
            }
            Spacer(Modifier.height(2.dp))
            Text("Save lists as templates and reuse them", fontSize = 12.sp, color = Color(0xFF94A3B8))
        }

        if (templates.isEmpty()) {
            item {
                EmptyState(
                    icon     = Icons.Default.Inventory2,
                    title    = "No templates yet",
                    subtitle = "Open a list → ⋮ → Save as template"
                )
            }
        } else {
            items(templates, key = { it.id }) { template ->
                TemplateCard(
                    template = template,
                    dateStr  = df.format(Date(template.createdAt)),
                    onLoad   = { showLoadDialog = template },
                    onDelete = { viewModel.deleteTemplate(template.id) }
                )
            }
        }
    }

    // ── Load dialog ───────────────────────────────────────────────────────────
    showLoadDialog?.let { template ->
        var newListName by remember { mutableStateOf("From: ${template.templateName}") }
        AlertDialog(
            onDismissRequest = { showLoadDialog = null },
            containerColor   = Color.White,
            shape            = RoundedCornerShape(24.dp),
            title = { Text("Load Template", fontWeight = FontWeight.Bold, color = SkyBlueDark) },
            text  = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("A new list will be created with all items from \"${template.templateName}\".",
                        fontSize = 13.sp, color = SkyBlueMedium)
                    OutlinedTextField(
                        value = newListName, onValueChange = { newListName = it },
                        label = { Text("New list name") }, singleLine = true,
                        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)
                    )
                }
            },
            confirmButton = {
                Row(Modifier.fillMaxWidth().padding(4.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    SkyButton("Load", Modifier.weight(1f)) {
                        if (newListName.isNotBlank()) {
                            viewModel.loadTemplate(template, newListName.trim())
                            showLoadDialog = null
                            onTemplateLoaded()
                        }
                    }
                    SkyButton("Cancel", Modifier.weight(1f), primary = false) { showLoadDialog = null }
                }
            }
        )
    }
}

@Composable
private fun TemplateCard(
    template: ListTemplate,
    dateStr: String,
    onLoad: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .shadow(1.dp, RoundedCornerShape(18.dp))
            .clip(RoundedCornerShape(18.dp))
            .background(Color.White)
            .padding(16.dp),
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(template.templateName, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = SkyBlueDark)
            Text("Created $dateStr", fontSize = 11.sp, color = Color(0xFF94A3B8))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            SkyButton("Load") { onLoad() }
            Box(
                modifier = Modifier.size(36.dp).clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFFEE2E2)).clickable(onClick = onDelete),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Delete, null, tint = Color(0xFFEF4444), modifier = Modifier.size(16.dp))
            }
        }
    }
}
