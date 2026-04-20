package com.example.shoppinglist2app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// ─── Shopping List ─────────────────────────────────────────────────────────────
@Entity(tableName = "shopping_lists")
data class ShoppingList(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val isArchived: Boolean = false,
    val totalBudget: Double = 0.0
)

// ─── Category ─────────────────────────────────────────────────────────────────
@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val colorHex: String = "#607D8B",
    val emoji: String = "📦"
)

val DEFAULT_CATEGORIES = listOf(
    Category(name = "Vegetables",  colorHex = "#4CAF50", emoji = "🥦"),
    Category(name = "Dairy",       colorHex = "#2196F3", emoji = "🥛"),
    Category(name = "Meat",        colorHex = "#F44336", emoji = "🥩"),
    Category(name = "Snacks",      colorHex = "#FFC107", emoji = "🍿"),
    Category(name = "Beverages",   colorHex = "#9C27B0", emoji = "🧃"),
    Category(name = "Household",   colorHex = "#FF9800", emoji = "🧹"),
    Category(name = "Bakery",      colorHex = "#795548", emoji = "🍞"),
    Category(name = "Frozen",      colorHex = "#00BCD4", emoji = "🧊"),
    Category(name = "Personal",    colorHex = "#E91E63", emoji = "🧴"),
    Category(name = "General",     colorHex = "#607D8B", emoji = "📦"),
)

// ─── Purchase History ──────────────────────────────────────────────────────────
@Entity(tableName = "purchase_history")
data class PurchaseHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val itemName: String = "",
    val category: String = "General",
    val price: Double = 0.0,
    val quantity: Double = 0.0,
    val unit: String = "pcs",
    val purchasedAt: Long = System.currentTimeMillis(),
    val listName: String = ""
)

data class CategorySpend(val category: String, val totalSpent: Double)
data class ItemFrequency(val itemName: String, val frequency: Int)

// ─── List Templates ────────────────────────────────────────────────────────────
@Entity(tableName = "list_templates")
data class ListTemplate(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val templateName: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "template_items")
data class TemplateItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val templateId: Int = 0,
    val itemName: String = "",
    val quantity: Double = 1.0,
    val unit: String = "pcs",
    val category: String = "General",
    val price: Double = 0.0
)

data class TemplateWithCount(val template: ListTemplate, val itemCount: Int)
