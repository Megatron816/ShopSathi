package com.example.shoppinglist2app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.shoppinglist2app.navigation.NavRoutes
import com.example.shoppinglist2app.ui.theme.*

private data class NavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

private val NAV_ITEMS = listOf(
    NavItem(NavRoutes.HOME,      Icons.Default.List,        "Lists"),
    NavItem(NavRoutes.CALENDAR,  Icons.Default.CalendarMonth, "Calendar"),
    NavItem(NavRoutes.ANALYTICS, Icons.Default.BarChart,    "Analytics"),
    NavItem(NavRoutes.TEMPLATES, Icons.Default.ContentCopy, "Templates"),
)

@Composable
fun BottomNavBar(currentRoute: String?, onNavigate: (String) -> Unit) {
    NavigationBar(
        containerColor = SkyBlueNavBg,           // blue-300 — different shade
        tonalElevation = 0.dp
    ) {
        val itemColors = NavigationBarItemDefaults.colors(
            selectedIconColor   = Color.White,
            selectedTextColor   = SkyBlueNavDark,
            indicatorColor      = SkyBlueNavDark, // blue-800
            unselectedIconColor = Color(0xFF475569),
            unselectedTextColor = Color(0xFF475569)
        )

        NAV_ITEMS.forEach { item ->
            NavigationBarItem(
                selected  = currentRoute == item.route,
                onClick   = {
                    if (currentRoute != item.route) {
                        onNavigate(item.route)
                    }
                },
                icon      = { Icon(item.icon, contentDescription = item.label) },
                label     = { Text(item.label) },
                colors    = itemColors
            )
        }
    }
}
