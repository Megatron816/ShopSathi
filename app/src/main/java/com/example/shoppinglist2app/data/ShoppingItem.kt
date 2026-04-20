package com.example.shoppinglist2app.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

object EventStatus {
    const val PAST   = "past"
    const val TODAY  = "today"
    const val FUTURE = "future"

    fun forDate(dateStr: String): String {
        if (dateStr.isBlank()) return TODAY
        return try {
            val date  = LocalDate.parse(dateStr)
            val today = LocalDate.now()
            when {
                date.isBefore(today) -> PAST
                date.isAfter(today)  -> FUTURE
                else                 -> TODAY
            }
        } catch (e: Exception) { TODAY }
    }
}

enum class Priority { HIGH, MEDIUM, LOW }

@Entity(tableName = "shopping_items")
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int          = 0,
    val listId: Int      = 0,
    val name: String     = "",
    val quantity: Double = 0.0,
    val unit: String     = "kg",
    val category: String = "General",
    val price: Double    = 0.0,
    val isChecked: Boolean = false,
    val priority: String = Priority.MEDIUM.name,
    val eventDate: String   = LocalDate.now().toString(),
    val eventStatus: String = EventStatus.TODAY,
    val addedAt: Long = System.currentTimeMillis()
)

data class ShoppingItemUiState(
    val item: ShoppingItem,
    val isEditing: Boolean = false
)
