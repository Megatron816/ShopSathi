package com.example.shoppinglist2app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppinglist2app.data.Category
import com.example.shoppinglist2app.ui.theme.*

fun getCategoryColor(name: String): Color = when (name.lowercase().trim()) {
    "vegetables"         -> CategoryVegetables
    "dairy"              -> CategoryDairy
    "meat"               -> CategoryMeat
    "snacks"             -> CategorySnacks
    "beverages"          -> CategoryBeverages
    "household"          -> CategoryHousehold
    "bakery"             -> CategoryBakery
    "frozen"             -> CategoryFrozen
    "personal"           -> CategoryPersonal
    else                 -> CategoryGeneral
}

fun getCategoryEmoji(name: String): String = when (name.lowercase().trim()) {
    "vegetables"  -> "🥦"
    "dairy"       -> "🥛"
    "meat"        -> "🥩"
    "snacks"      -> "🍿"
    "beverages"   -> "🧃"
    "household"   -> "🧹"
    "bakery"      -> "🍞"
    "frozen"      -> "🧊"
    "personal"    -> "🧴"
    else          -> "📦"
}

// ── Category color dot ────────────────────────────────────────────────────────
@Composable
fun CategoryDot(category: String, size: Int = 10) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(getCategoryColor(category))
    )
}

// ── Category chip (selectable) ────────────────────────────────────────────────
@Composable
fun CategoryChip(
    category: Category,
    selected: Boolean,
    onClick: () -> Unit
) {
    val color = getCategoryColor(category.name)
    val bg    = if (selected) color else color.copy(alpha = 0.12f)
    val textC = if (selected) Color.White else color

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bg)
            .border(
                width = if (selected) 0.dp else 1.dp,
                color = color.copy(alpha = 0.4f),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(category.emoji, fontSize = 14.sp)
        Text(
            text       = category.name,
            fontSize   = 12.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            color      = textC
        )
    }
}

// ── Horizontal category picker ────────────────────────────────────────────────
@Composable
fun CategoryPicker(
    categories: List<Category>,
    selectedName: String,
    onSelect: (String) -> Unit
) {
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { cat ->
            CategoryChip(
                category = cat,
                selected = selectedName == cat.name,
                onClick  = { onSelect(cat.name) }
            )
        }
    }
}

// ── Category label pill (read-only) ──────────────────────────────────────────
@Composable
fun CategoryPill(category: String) {
    val color = getCategoryColor(category)
    val emoji = getCategoryEmoji(category)
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(color.copy(alpha = 0.12f))
            .padding(horizontal = 8.dp, vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(emoji, fontSize = 11.sp)
        Text(category, fontSize = 11.sp, color = color, fontWeight = FontWeight.Medium)
    }
}
