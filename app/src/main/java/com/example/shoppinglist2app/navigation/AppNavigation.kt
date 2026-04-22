package com.example.shoppinglist2app.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import com.example.shoppinglist2app.ui.components.BottomNavBar
import com.example.shoppinglist2app.ui.components.AppBackground
import com.example.shoppinglist2app.ui.screens.*
import com.example.shoppinglist2app.viewmodel.ShoppingViewModel
import com.example.shoppinglist2app.navigation.NavRoutes.decodeSlash
import kotlin.math.abs

private enum class TopLevelSlideDirection {
    Forward,
    Backward
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation(viewModel: ShoppingViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val topLevelIndex = NavRoutes.topLevelOrder.indexOf(currentRoute)
    val swipeThresholdPx = with(LocalDensity.current) { 72.dp.toPx() }
    var topLevelSlideDirection by remember { mutableStateOf(TopLevelSlideDirection.Forward) }

    val showBottomBar = currentRoute in NavRoutes.topLevel
    val navigateTopLevel: (String) -> Unit = { route ->
        if (route != currentRoute) {
            val targetIndex = NavRoutes.topLevelOrder.indexOf(route)
            val currentIndex = NavRoutes.topLevelOrder.indexOf(currentRoute)
            topLevelSlideDirection = if (targetIndex != -1 && currentIndex != -1 && targetIndex < currentIndex) {
                TopLevelSlideDirection.Backward
            } else {
                TopLevelSlideDirection.Forward
            }

            navController.navigate(route) {
                popUpTo(NavRoutes.HOME) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    val swipeModifier = if (showBottomBar && topLevelIndex != -1) {
        Modifier.pointerInput(currentRoute) {
            var totalDrag = 0f
            var didNavigate = false
            detectHorizontalDragGestures(
                onHorizontalDrag = { change, dragAmount ->
                    totalDrag += dragAmount
                    if (!didNavigate && abs(totalDrag) >= swipeThresholdPx) {
                        val targetIndex = if (totalDrag < 0f) topLevelIndex + 1 else topLevelIndex - 1
                        if (targetIndex in NavRoutes.topLevelOrder.indices) {
                            didNavigate = true
                            navigateTopLevel(NavRoutes.topLevelOrder[targetIndex])
                        }
                    }
                    change.consume()
                },
                onDragEnd = {
                    totalDrag = 0f
                    didNavigate = false
                },
                onDragCancel = {
                    totalDrag = 0f
                    didNavigate = false
                }
            )
        }
    } else {
        Modifier
    }

    AppBackground {
        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                if (showBottomBar) {
                    BottomNavBar(currentRoute = currentRoute, onNavigate = navigateTopLevel)
                }
            }
        ) { padding ->
            NavHost(
                navController    = navController,
                startDestination = NavRoutes.HOME,
                modifier         = Modifier.padding(padding).then(swipeModifier),
                enterTransition  = {
                    val isTopLevelTransition =
                        initialState.destination.route in NavRoutes.topLevel &&
                            targetState.destination.route in NavRoutes.topLevel

                    when {
                        isTopLevelTransition && topLevelSlideDirection == TopLevelSlideDirection.Backward -> {
                            slideInHorizontally(tween(280)) { -it } + fadeIn(tween(280))
                        }
                        isTopLevelTransition -> {
                            slideInHorizontally(tween(280)) { it } + fadeIn(tween(280))
                        }
                        else -> slideInHorizontally(tween(280)) { it } + fadeIn(tween(280))
                    }
                },
                exitTransition   = {
                    val isTopLevelTransition =
                        initialState.destination.route in NavRoutes.topLevel &&
                            targetState.destination.route in NavRoutes.topLevel

                    when {
                        isTopLevelTransition && topLevelSlideDirection == TopLevelSlideDirection.Backward -> {
                            slideOutHorizontally(tween(280)) { it } + fadeOut(tween(280))
                        }
                        isTopLevelTransition -> {
                            slideOutHorizontally(tween(280)) { -it } + fadeOut(tween(280))
                        }
                        else -> slideOutHorizontally(tween(280)) { -it } + fadeOut(tween(280))
                    }
                },
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
