package com.example.shoppinglist2app.data

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        ShoppingItem::class,
        ShoppingList::class,
        Category::class,
        PurchaseHistory::class,
        ListTemplate::class,
        TemplateItem::class
    ],
    version = 4,
    exportSchema = false
)
abstract class ShoppingDatabase : RoomDatabase() {

    abstract fun shoppingDao(): ShoppingDao

    companion object {
        @Volatile private var INSTANCE: ShoppingDatabase? = null

        fun getDatabase(context: Context): ShoppingDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ShoppingDatabase::class.java,
                    "shopping_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                .also { db ->
                    INSTANCE = db
                    CoroutineScope(Dispatchers.IO).launch {
                        val existingNames = db.shoppingDao().getCategoryNames().map { it.trim().lowercase() }.toSet()
                        val missingDefaults = DEFAULT_CATEGORIES.filterNot { it.name.trim().lowercase() in existingNames }
                        if (missingDefaults.isNotEmpty()) {
                            db.shoppingDao().insertCategories(missingDefaults)
                        }
                    }
                }
            }
        }
    }
}
