package com.example.shoppinglist2app.viewmodel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.*
import com.example.shoppinglist2app.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.util.Calendar

class ShoppingViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = ShoppingDatabase.getDatabase(application).shoppingDao()
    private val app = application

    // ── UI editing state ────────────────────────────────────────────────────
    private val _editingIds = MutableStateFlow<Set<Int>>(emptySet())
    val editingIds: StateFlow<Set<Int>> = _editingIds.asStateFlow()

    // ── Selected date ────────────────────────────────────────────────────────
    private val _selectedDate = MutableStateFlow(LocalDate.now().toString())
    val selectedDate: StateFlow<String> = _selectedDate.asStateFlow()

    // ── Selected list ─────────────────────────────────────────────────────────
    private val _selectedListId = MutableStateFlow(0)
    val selectedListId: StateFlow<Int> = _selectedListId.asStateFlow()

    // ── Shopping Lists ────────────────────────────────────────────────────────
    val activeLists: StateFlow<List<ShoppingList>> =
        dao.getActiveLists().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val archivedLists: StateFlow<List<ShoppingList>> =
        dao.getArchivedLists().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ── Items for selected list ───────────────────────────────────────────────
    val itemsForSelectedList: StateFlow<List<ShoppingItem>> =
        _selectedListId.flatMapLatest { id ->
            if (id == 0) dao.getTodayItems() else dao.getItemsForList(id)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalSpent: StateFlow<Double> =
        _selectedListId.flatMapLatest { dao.getTotalSpent(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val totalEstimate: StateFlow<Double> =
        _selectedListId.flatMapLatest { dao.getTotalEstimate(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val allItems: StateFlow<List<ShoppingItem>> =
        dao.getTodayItems().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ── Calendar ──────────────────────────────────────────────────────────────
    val itemsForSelectedDate: StateFlow<List<ShoppingItem>> =
        _selectedDate.flatMapLatest { dao.getItemsByDate(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allEventDates: StateFlow<List<String>> =
        dao.getAllEventDates().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ── Categories ────────────────────────────────────────────────────────────
    val categories: StateFlow<List<Category>> =
        dao.getAllCategories().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ── Templates ─────────────────────────────────────────────────────────────
    val templates: StateFlow<List<ListTemplate>> =
        dao.getAllTemplates().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ── Analytics ─────────────────────────────────────────────────────────────
    private fun currentMonthRange(): Pair<Long, Long> {
        val cal = Calendar.getInstance()
        val ym  = YearMonth.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1)
        val start = Calendar.getInstance().apply {
            set(ym.year, ym.monthValue - 1, 1, 0, 0, 0); set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        val end = Calendar.getInstance().apply {
            set(ym.year, ym.monthValue - 1, ym.lengthOfMonth(), 23, 59, 59)
        }.timeInMillis
        return start to end
    }

    val monthlySpend: StateFlow<Double> = run {
        val (s, e) = currentMonthRange()
        dao.getMonthlySpend(s, e).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)
    }

    val categoryBreakdown: StateFlow<List<CategorySpend>> = run {
        val (s, e) = currentMonthRange()
        dao.getCategoryBreakdown(s, e).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }

    val topItems: StateFlow<List<ItemFrequency>> =
        dao.getTopItems().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ── Smart Auto-Suggestions (from purchase history — no AI!) ───────────────
    private val _suggestionQuery = MutableStateFlow("")
    val suggestions: StateFlow<List<ItemFrequency>> =
        _suggestionQuery.flatMapLatest { q ->
            if (q.isBlank()) flowOf(emptyList()) else dao.getSuggestions(q)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateSuggestionQuery(query: String) { _suggestionQuery.value = query }

    // ══════════════════════════════════════════════════════════════════════════
    // LIST ACTIONS
    // ══════════════════════════════════════════════════════════════════════════

    fun selectList(id: Int) { _selectedListId.value = id }

    fun createList(name: String, budget: Double = 0.0) {
        viewModelScope.launch { dao.insertList(ShoppingList(name = name, totalBudget = budget)) }
    }

    fun archiveList(id: Int)  { viewModelScope.launch { dao.archiveList(id) } }
    fun restoreList(id: Int)  { viewModelScope.launch { dao.restoreList(id) } }
    fun deleteList(id: Int) {
        viewModelScope.launch { dao.deleteAllItemsForList(id); dao.deleteList(id) }
    }

    // ══════════════════════════════════════════════════════════════════════════
    // ITEM ACTIONS
    // ══════════════════════════════════════════════════════════════════════════

    fun addItem(item: ShoppingItem) {
        val d = item.eventDate.ifBlank { LocalDate.now().toString() }
        viewModelScope.launch {
            dao.upsertItem(item.copy(
                eventDate   = d,
                eventStatus = EventStatus.forDate(d),
                listId      = _selectedListId.value
            ))
        }
    }

    fun addItemToDate(item: ShoppingItem, date: String) {
        viewModelScope.launch {
            dao.upsertItem(item.copy(eventDate = date, eventStatus = EventStatus.forDate(date)))
        }
    }

    fun updateItem(item: ShoppingItem) {
        viewModelScope.launch {
            dao.upsertItem(item.copy(eventStatus = EventStatus.forDate(item.eventDate)))
        }
        stopEditing(item.id)
    }

    fun deleteItem(item: ShoppingItem) {
        stopEditing(item.id)
        viewModelScope.launch { dao.deleteItem(item) }
    }

    fun deleteEvent(date: String) { viewModelScope.launch { dao.deleteAllItemsForDate(date) } }

    // ══════════════════════════════════════════════════════════════════════════
    // SHOPPING MODE
    // ══════════════════════════════════════════════════════════════════════════

    fun checkItem(id: Int, checked: Boolean) {
        viewModelScope.launch { dao.setChecked(id, checked) }
    }

    fun uncheckAll(listId: Int) { viewModelScope.launch { dao.uncheckAll(listId) } }

    fun markListDone(list: ShoppingList, items: List<ShoppingItem>) {
        viewModelScope.launch {
            val history = items.filter { it.isChecked }.map { item ->
                PurchaseHistory(
                    itemName  = item.name, category = item.category,
                    price     = item.price, quantity = item.quantity,
                    unit      = item.unit, listName  = list.name
                )
            }
            if (history.isNotEmpty()) dao.insertHistoryBatch(history)
            dao.archiveList(list.id)
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    // TEMPLATES
    // ══════════════════════════════════════════════════════════════════════════

    fun saveAsTemplate(templateName: String, items: List<ShoppingItem>) {
        viewModelScope.launch {
            val tid = dao.insertTemplate(ListTemplate(templateName = templateName))
            dao.insertTemplateItems(items.map { item ->
                TemplateItem(templateId = tid.toInt(), itemName = item.name,
                    quantity = item.quantity, unit = item.unit,
                    category = item.category, price = item.price)
            })
        }
    }

    fun loadTemplate(template: ListTemplate, newListName: String) {
        viewModelScope.launch {
            val listId = dao.insertList(ShoppingList(name = newListName))
            dao.getTemplateItems(template.id).first().forEach { tItem ->
                dao.upsertItem(ShoppingItem(
                    listId   = listId.toInt(), name = tItem.itemName,
                    quantity = tItem.quantity, unit = tItem.unit,
                    category = tItem.category, price = tItem.price
                ))
            }
        }
    }

    fun deleteTemplate(id: Int) {
        viewModelScope.launch { dao.deleteTemplateItems(id); dao.deleteTemplate(id) }
    }

    // ══════════════════════════════════════════════════════════════════════════
    // SHARE
    // ══════════════════════════════════════════════════════════════════════════

    fun shareList(listName: String, items: List<ShoppingItem>) {
        val text = buildString {
            appendLine(listName)
            appendLine("─────────────────────")
            items.groupBy { it.category }.forEach { (cat, catItems) ->
                appendLine("\n[$cat]")
                catItems.forEach { item ->
                    val check = if (item.isChecked) "[x]" else "[ ]"
                    val qty   = if (item.quantity % 1.0 == 0.0) item.quantity.toInt().toString()
                                else "%.2f".format(item.quantity)
                    val price = if (item.price > 0) " — ₹${"%.2f".format(item.price)}" else ""
                    appendLine("  $check ${item.name} — $qty ${item.unit}$price")
                }
            }
        }
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"; putExtra(Intent.EXTRA_TEXT, text)
        }
        app.startActivity(Intent.createChooser(intent, "Share list").apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }

    // ══════════════════════════════════════════════════════════════════════════
    // HELPERS
    // ══════════════════════════════════════════════════════════════════════════

    fun startEditing(itemId: Int) { _editingIds.value = _editingIds.value + itemId }
    fun stopEditing(itemId: Int)  { _editingIds.value = _editingIds.value - itemId }
    fun isEditing(itemId: Int)    = itemId in _editingIds.value

    fun selectDate(date: String) { _selectedDate.value = date }
    fun isToday(date: String)  = date == LocalDate.now().toString()
    fun isPast(date: String)   = try { LocalDate.parse(date).isBefore(LocalDate.now()) } catch (e: Exception) { false }
    fun isFuture(date: String) = try { LocalDate.parse(date).isAfter(LocalDate.now()) }  catch (e: Exception) { false }
}
