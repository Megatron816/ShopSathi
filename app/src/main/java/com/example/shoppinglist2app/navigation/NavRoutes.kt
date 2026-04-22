package com.example.shoppinglist2app.navigation

object NavRoutes {
    const val HOME      = "home"
    const val CALENDAR  = "calendar"
    const val ANALYTICS = "analytics"
    const val TEMPLATES = "templates"

    const val LIST_DETAIL    = "list_detail/{listId}/{listName}"
    const val SHOPPING_MODE  = "shopping_mode/{listId}/{listName}"
    const val EVENT          = "event/{date}"

    fun listDetail(listId: Int, listName: String)    = "list_detail/$listId/${listName.encodeSlash()}"
    fun shoppingMode(listId: Int, listName: String)  = "shopping_mode/$listId/${listName.encodeSlash()}"
    fun event(date: String)                          = "event/$date"

    // Ordered top-level routes used by bottom navigation and swipe navigation.
    val topLevelOrder = listOf(HOME, CALENDAR, ANALYTICS, TEMPLATES)
    val topLevel = topLevelOrder.toSet()

    private fun String.encodeSlash() = replace("/", "|")
    fun String.decodeSlash()         = replace("|", "/")
}
