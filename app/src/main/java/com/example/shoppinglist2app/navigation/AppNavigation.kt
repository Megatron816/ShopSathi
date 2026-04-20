package com.example.shoppinglist2app.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.*
import com.example.shoppinglist2app.ui.components.BottomNavBar
import com.example.shoppinglist2app.ui.components.AppBackground
import com.example.shoppinglist2app.ui.screens.*
import com.example.shoppinglist2app.viewmodel.ShoppingViewModel
import com.example.shoppinglist2app.navigation.NavRoutes.decodeSlash

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation(viewModel: ShoppingViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in NavRoutes.topLevel

    AppBackground {
        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                if (showBottomBar) {
                    BottomNavBar(navController = navController, currentRoute = currentRoute)
                }
            }
        ) { padding ->
            NavHost(
                navController    = navController,
                startDestination = NavRoutes.HOME,
                modifier         = Modifier.padding(padding),
                enterTransition  = { slideInHorizontally(tween(280)) { it } + fadeIn(tween(280)) },
                exitTransition   = { slideOutHorizontally(tween(280)) { -it } + fadeOut(tween(280)) },
                popEnterTransition  = { slideInHorizontally(tween(280)) { -it } + fadeIn(tween(280)) },
                popExitTransition   = { slideOutHorizontally(tween(280)) { it } + fadeOut(tween(280)) }
            ) {
                // ── Home (Lists Hub) ────────────────────────────────────────
                composable(NavRoutes.HOME) {
                    HomeScreen(
                        viewModel = viewModel,
                        onOpenList = { id ->
                            val list = viewModel.activeLists.value.find { it.id == id }
                                ?: viewModel.archivedLists.value.find { it.id == id }
                            viewModel.selectList(id)
                            navController.navigate(NavRoutes.listDetail(id, list?.name ?: "List"))
                        }
                    )
                }

                // ── List Detail ──────────────────────────────────────────────
                composable(NavRoutes.LIST_DETAIL) { back ->
                    val listId   = back.arguments?.getString("listId")?.toIntOrNull() ?: 0
                    val listName = back.arguments?.getString("listName")?.decodeSlash() ?: "List"
                    viewModel.selectList(listId)
                    ListDetailScreen(
                        viewModel       = viewModel,
                        listId          = listId,
                        listName        = listName,
                        onStartShopping = { navController.navigate(NavRoutes.shoppingMode(listId, listName)) },
                        onBack          = { navController.popBackStack() }
                    )
                }

                // ── Shopping Mode ────────────────────────────────────────────
                composable(NavRoutes.SHOPPING_MODE) { back ->
                    val listId   = back.arguments?.getString("listId")?.toIntOrNull() ?: 0
                    val listName = back.arguments?.getString("listName")?.decodeSlash() ?: "List"
                    viewModel.selectList(listId)
                    ShoppingModeScreen(
                        listId    = listId,
                        listName  = listName,
                        viewModel = viewModel,
                        onBack    = { navController.popBackStack() },
                        onDone    = {
                            navController.navigate(NavRoutes.HOME) {
                                popUpTo(NavRoutes.HOME) { inclusive = true }
                            }
                        }
                    )
                }

                // ── Calendar ─────────────────────────────────────────────────
                composable(NavRoutes.CALENDAR) {
                    CalendarScreen(
                        viewModel      = viewModel,
                        onDateSelected = { date ->
                            viewModel.selectDate(date)
                            navController.navigate(NavRoutes.event(date))
                        },
                        onBack = { navController.popBackStack() }
                    )
                }

                // ── Event Detail ──────────────────────────────────────────────
                composable(NavRoutes.EVENT) { back ->
                    val date = back.arguments?.getString("date") ?: ""
                    viewModel.selectDate(date)
                    EventDetailScreen(
                        date      = date,
                        viewModel = viewModel,
                        onBack    = { navController.popBackStack() }
                    )
                }

                // ── Analytics ─────────────────────────────────────────────────
                composable(NavRoutes.ANALYTICS) {
                    AnalyticsScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
                }

                // ── Templates ─────────────────────────────────────────────────
                composable(NavRoutes.TEMPLATES) {
                    TemplatesScreen(
                        viewModel        = viewModel,
                        onBack           = { navController.popBackStack() },
                        onTemplateLoaded = {
                            navController.navigate(NavRoutes.HOME) {
                                popUpTo(NavRoutes.HOME) { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
    }
}
