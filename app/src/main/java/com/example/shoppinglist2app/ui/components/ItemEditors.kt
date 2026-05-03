package com.example.shoppinglist2app.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.shoppinglist2app.data.Category
import com.example.shoppinglist2app.data.ItemFrequency
import com.example.shoppinglist2app.data.Priority
import com.example.shoppinglist2app.data.ShoppingItem
import com.example.shoppinglist2app.ui.theme.*

private val SI_UNITS = listOf(
    "kg", "g", "mg", "L", "mL", "m", "cm", "mm",
    "pcs", "dozen", "pack", "box", "bag", "W", "kWh"
)

private val fieldColors @Composable get() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor    = SkyBluePrimary,
    unfocusedBorderColor  = SkyBlueBorder,
    focusedLabelColor     = SkyBluePrimary,
    unfocusedLabelColor   = SkyBlueMedium,
    cursorColor           = SkyBluePrimary,
    focusedTextColor      = SkyBlueDark,
    unfocusedTextColor    = SkyBlueDark,
    focusedContainerColor = Color(0xFFF0F9FF),
    unfocusedContainerColor = Color(0xFFF0F9FF)
)

// ── Add Item Dialog with auto-suggestions + category colour picker ─────────────
@Composable
fun AddItemDialog(
    categories: List<Category>,
    suggestions: List<ItemFrequency>,
    onSuggestionQuery: (String) -> Unit,
    onDismiss: () -> Unit,
    onAdd: (name: String, qty: Double, unit: String, category: String, priority: String) -> Unit
) {
    var itemName     by remember { mutableStateOf("") }
    var itemCategory by remember { mutableStateOf(categories.firstOrNull()?.name ?: "General") }
    var itemQuantity by remember { mutableStateOf("1") }
    var itemUnit     by remember { mutableStateOf("kg") }
    var itemPriority by remember { mutableStateOf(Priority.MEDIUM.name) }

    LaunchedEffect(itemName) { onSuggestionQuery(itemName) }

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .shadow(8.dp, RoundedCornerShape(24.dp))
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text("Add Item", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SkyBlueDark)

            // Item name + suggestions
            Column {
                OutlinedTextField(
                    value         = itemName,
                    onValueChange = { itemName = it },
                    label         = { Text("Item name") },
                    placeholder   = { Text("e.g. Milk…", color = Color(0xFF94A3B8)) },
                    singleLine    = true,
                    modifier      = Modifier.fillMaxWidth(),
                    shape         = RoundedCornerShape(12.dp),
                    colors        = fieldColors
                )
                // Smart suggestions row
                AnimatedVisibility(visible = suggestions.isNotEmpty() && itemName.isNotBlank()) {
                    Column {
                        Spacer(Modifier.height(4.dp))
                        Text("Suggestions (from your history):", fontSize = 10.sp, color = SkyBlueMedium)
                        Spacer(Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            suggestions.forEach { sug ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(SkyBlueAccent.copy(alpha = 0.2f))
                                        .border(1.dp, SkyBlueAccent, RoundedCornerShape(12.dp))
                                        .clickable { itemName = sug.itemName }
                                        .padding(horizontal = 10.dp, vertical = 5.dp)
                                ) {
                                    Text(
                                        "${sug.itemName} ×${sug.frequency}",
                                        fontSize = 12.sp,
                                        color = SkyBluePrimary,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Category picker
            Column {
                Text("Category", fontSize = 11.sp, fontWeight = FontWeight.Bold,
                    color = SkyBlueMedium, letterSpacing = 0.5.sp)
                Spacer(Modifier.height(6.dp))
                CategoryPicker(
                    categories   = categories,
                    selectedName = itemCategory,
                    onSelect     = { itemCategory = it }
                )
            }

            // Priority picker
            Column {
                Text("Priority", fontSize = 11.sp, fontWeight = FontWeight.Bold,
                    color = SkyBlueMedium, letterSpacing = 0.5.sp)
                Spacer(Modifier.height(6.dp))
                PrioritySelector(
                    selectedPriority = itemPriority,
                    onSelect = { itemPriority = it }
                )
            }

            // Qty + Unit row
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value         = itemQuantity,
                    onValueChange = { itemQuantity = it },
                    label         = { Text("Qty") },
                    singleLine    = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier      = Modifier.weight(1f),
                    shape         = RoundedCornerShape(12.dp),
                    colors        = fieldColors
                )
                UnitDropdown(
                    selected  = itemUnit,
                    onSelect  = { itemUnit = it },
                    modifier  = Modifier.weight(1.2f)
                )
            }

            // Buttons
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                SkyButton("Add", Modifier.weight(1f), primary = true) {
                    if (itemName.isNotBlank()) {
                        onAdd(
                            itemName.trim(),
                            itemQuantity.toDoubleOrNull() ?: 1.0,
                            itemUnit,
                            itemCategory,
                            itemPriority
                        )
                    }
                }
                SkyButton("Cancel", Modifier.weight(1f), primary = false) { onDismiss() }
            }
        }
    }
}

// ── Unit Dropdown ─────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitDropdown(selected: String, onSelect: (String) -> Unit, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }, modifier = modifier) {
        OutlinedTextField(
            value         = selected,
            onValueChange = {},
            readOnly      = true,
            label         = { Text("Unit") },
            trailingIcon  = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier      = Modifier.fillMaxWidth().menuAnchor(),
            singleLine    = true,
            shape         = RoundedCornerShape(12.dp),
            colors        = OutlinedTextFieldDefaults.colors(
                focusedBorderColor    = SkyBlueTeal,
                unfocusedBorderColor  = SkyBlueBorder,
                focusedLabelColor     = SkyBlueTeal,
                unfocusedLabelColor   = SkyBlueMedium,
                focusedTextColor      = SkyBlueDark,
                unfocusedTextColor    = SkyBlueDark,
                focusedContainerColor = Color(0xFFF0F9FF),
                unfocusedContainerColor = Color(0xFFF0F9FF)
            )
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            SI_UNITS.forEach { unit ->
                DropdownMenuItem(
                    text    = { Text(unit, color = SkyBlueDark) },
                    onClick = { onSelect(unit); expanded = false }
                )
            }
        }
    }
}

// ── Inline Item Editor ────────────────────────────────────────────────────────
@Composable
fun ShoppingItemEditor(
    item: ShoppingItem,
    categories: List<Category>,
    onEditComplete: (name: String, qty: Double, unit: String, category: String, priority: String) -> Unit
) {
    var name     by remember(item.id) { mutableStateOf(item.name) }
    var qty      by remember(item.id) { mutableStateOf(if (item.quantity % 1.0 == 0.0) item.quantity.toInt().toString() else "%.2f".format(item.quantity)) }
    var unit     by remember(item.id) { mutableStateOf(item.unit) }
    var category by remember(item.id) { mutableStateOf(item.category) }
    var priority by remember(item.id) { mutableStateOf(item.priority.ifBlank { Priority.MEDIUM.name }) }

    val fieldC = fieldColors

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFF0F9FF))
            .border(1.5.dp, SkyBlueBorder, RoundedCornerShape(16.dp))
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Edit Item", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SkyBlueDark)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = name, onValueChange = { name = it },
                label = { Text("Name") }, singleLine = true, colors = fieldC,
                shape = RoundedCornerShape(12.dp), modifier = Modifier.weight(1f))
            UnitDropdown(selected = unit, onSelect = { unit = it }, modifier = Modifier.weight(1f))
        }
        OutlinedTextField(value = qty, onValueChange = { qty = it },
            label = { Text("Quantity") }, singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            colors = fieldC, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth())
        CategoryPicker(categories = categories, selectedName = category, onSelect = { category = it })
        PrioritySelector(selectedPriority = priority, onSelect = { priority = it })
        SkyButton("Save Changes", Modifier.fillMaxWidth()) {
            onEditComplete(name.trim(), qty.toDoubleOrNull() ?: 0.0, unit, category, priority)
        }
    }
}

// ── Priority selector ─────────────────────────────────────────────────────────
@Composable
fun PrioritySelector(selectedPriority: String, onSelect: (String) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Priority.values().forEach { priority ->
            val selected = selectedPriority.equals(priority.name, ignoreCase = true)
            val bg = when (priority) {
                Priority.HIGH -> if (selected) PriorityHigh.copy(alpha = 0.18f) else Color(0xFFFEE2E2)
                Priority.MEDIUM -> if (selected) PriorityMedium.copy(alpha = 0.18f) else Color(0xFFFEF3C7)
                Priority.LOW -> if (selected) PriorityLow.copy(alpha = 0.18f) else Color(0xFFDCFCE7)
            }
            val border = when (priority) {
                Priority.HIGH -> PriorityHigh
                Priority.MEDIUM -> PriorityMedium
                Priority.LOW -> PriorityLow
            }
            val textColor = when (priority) {
                Priority.HIGH -> PriorityHigh
                Priority.MEDIUM -> PriorityMedium
                Priority.LOW -> PriorityLow
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(bg)
                    .border(1.dp, border, RoundedCornerShape(12.dp))
                    .clickable { onSelect(priority.name) }
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(priority.name, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = textColor)
            }
        }
    }
}
