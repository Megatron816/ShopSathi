package com.example.shoppinglist2app.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDao {

    // ── Shopping Lists ──────────────────────────────────────────────────────
    @Query("SELECT * FROM shopping_lists WHERE isArchived = 0 ORDER BY createdAt DESC")
    fun getActiveLists(): Flow<List<ShoppingList>>

    @Query("SELECT * FROM shopping_lists WHERE isArchived = 1 ORDER BY createdAt DESC")
    fun getArchivedLists(): Flow<List<ShoppingList>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: ShoppingList): Long

    @Update
    suspend fun updateList(list: ShoppingList)

    @Query("UPDATE shopping_lists SET isArchived = 1 WHERE id = :id")
    suspend fun archiveList(id: Int)

    @Query("UPDATE shopping_lists SET isArchived = 0 WHERE id = :id")
    suspend fun restoreList(id: Int)

    @Query("DELETE FROM shopping_lists WHERE id = :id")
    suspend fun deleteList(id: Int)

    // ── Shopping Items ──────────────────────────────────────────────────────
    @Query("""
        SELECT * FROM shopping_items WHERE listId = :listId
        ORDER BY CASE priority WHEN 'HIGH' THEN 1 WHEN 'MEDIUM' THEN 2 ELSE 3 END, category ASC
    """)
    fun getItemsForList(listId: Int): Flow<List<ShoppingItem>>

    @Query("SELECT * FROM shopping_items WHERE eventDate = :date ORDER BY id DESC")
    fun getItemsByDate(date: String): Flow<List<ShoppingItem>>

    @Query("SELECT DISTINCT eventDate FROM shopping_items ORDER BY eventDate DESC")
    fun getAllEventDates(): Flow<List<String>>

    @Query("SELECT * FROM shopping_items WHERE eventStatus = 'today' ORDER BY id DESC")
    fun getTodayItems(): Flow<List<ShoppingItem>>

    @Query("SELECT COALESCE(SUM(price * quantity), 0.0) FROM shopping_items WHERE listId = :listId AND isChecked = 1")
    fun getTotalSpent(listId: Int): Flow<Double>

    @Query("SELECT COALESCE(SUM(price * quantity), 0.0) FROM shopping_items WHERE listId = :listId")
    fun getTotalEstimate(listId: Int): Flow<Double>

    @Upsert
    suspend fun upsertItem(item: ShoppingItem)

    @Delete
    suspend fun deleteItem(item: ShoppingItem)

    @Query("DELETE FROM shopping_items WHERE listId = :listId")
    suspend fun deleteAllItemsForList(listId: Int)

    @Query("DELETE FROM shopping_items WHERE eventDate = :date")
    suspend fun deleteAllItemsForDate(date: String)

    @Query("UPDATE shopping_items SET isChecked = :checked WHERE id = :id")
    suspend fun setChecked(id: Int, checked: Boolean)

    @Query("UPDATE shopping_items SET isChecked = 0 WHERE listId = :listId")
    suspend fun uncheckAll(listId: Int)

    // ── Categories ───────────────────────────────────────────────────────────
    @Query("SELECT * FROM categories ORDER BY name ASC")
    fun getAllCategories(): Flow<List<Category>>

    @Query("SELECT name FROM categories")
    suspend fun getCategoryNames(): List<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: Category)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategories(categories: List<Category>)

    @Query("SELECT COUNT(*) FROM categories")
    suspend fun getCategoryCount(): Int

    @Delete
    suspend fun deleteCategory(category: Category)

    // ── Purchase History ─────────────────────────────────────────────────────
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: PurchaseHistory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistoryBatch(items: List<PurchaseHistory>)

    @Query("SELECT COALESCE(SUM(price * quantity), 0.0) FROM purchase_history WHERE purchasedAt >= :monthStart AND purchasedAt <= :monthEnd")
    fun getMonthlySpend(monthStart: Long, monthEnd: Long): Flow<Double>

    @Query("""
        SELECT category, SUM(price * quantity) as totalSpent
        FROM purchase_history
        WHERE purchasedAt >= :monthStart AND purchasedAt <= :monthEnd
        GROUP BY category ORDER BY totalSpent DESC
    """)
    fun getCategoryBreakdown(monthStart: Long, monthEnd: Long): Flow<List<CategorySpend>>

    // Smart auto-suggestions — pulls from purchase history, no AI needed!
    @Query("""
        SELECT itemName, COUNT(*) as frequency
        FROM purchase_history
        WHERE itemName LIKE :query || '%'
        GROUP BY itemName
        ORDER BY frequency DESC
        LIMIT 10
    """)
    fun getSuggestions(query: String): Flow<List<ItemFrequency>>

    @Query("""
        SELECT itemName, COUNT(*) as frequency
        FROM purchase_history
        GROUP BY itemName
        ORDER BY frequency DESC
        LIMIT 5
    """)
    fun getTopItems(): Flow<List<ItemFrequency>>

    // ── Templates ────────────────────────────────────────────────────────────
    @Query("SELECT * FROM list_templates ORDER BY createdAt DESC")
    fun getAllTemplates(): Flow<List<ListTemplate>>

    @Query("SELECT * FROM template_items WHERE templateId = :templateId")
    fun getTemplateItems(templateId: Int): Flow<List<TemplateItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplate(template: ListTemplate): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplateItems(items: List<TemplateItem>)

    @Query("DELETE FROM list_templates WHERE id = :id")
    suspend fun deleteTemplate(id: Int)

    @Query("DELETE FROM template_items WHERE templateId = :templateId")
    suspend fun deleteTemplateItems(templateId: Int)

    @Query("SELECT COUNT(*) FROM template_items WHERE templateId = :templateId")
    suspend fun getTemplateItemCount(templateId: Int): Int
}
