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

    // Bottom-nav top-level routes (show bottom bar on these)
    val topLevel = setOf(HOME, CALENDAR, ANALYTICS, TEMPLATES)

    private fun String.encodeSlash() = replace("/", "|")
    fun String.decodeSlash()         = replace("|", "/")
}
