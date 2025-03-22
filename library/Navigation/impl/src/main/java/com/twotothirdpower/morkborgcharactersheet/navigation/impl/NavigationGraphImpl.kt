package com.twotothirdpower.morkborgcharactersheet.navigation.impl

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.twotothirdpower.morkborgcharactersheet.characterselect.CharacterSelectScreen
import com.twotothirdpower.morkborgcharactersheet.charactersheet.CharacterSheetScreen
import com.twotothirdpower.morkborgcharactersheet.inventory.InventoryScreen
import com.twotothirdpower.morkborgcharactersheet.navigation.NavigationGraph
import javax.inject.Inject

class NavigationGraphImpl @Inject constructor(
    private val characterSelectScreen: CharacterSelectScreen,
    private val characterSheetScreen: CharacterSheetScreen,
    private val inventoryScreen: InventoryScreen
) : NavigationGraph {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content(
        navController: NavHostController,
        modifier: Modifier
    ) {
        val pagerState = rememberPagerState(initialPage = 0) { 3 }
        
        // Sync NavController with PagerState
        LaunchedEffect(pagerState.currentPage) {
            when (pagerState.currentPage) {
                0 -> navController.navigate(Screen.CharacterSelect.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
                1 -> navController.navigate(Screen.CharacterSheet.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
                2 -> navController.navigate(Screen.Inventory.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }

        // Sync PagerState with NavController
        LaunchedEffect(navController.currentBackStackEntry?.destination?.route) {
            when (navController.currentBackStackEntry?.destination?.route) {
                Screen.CharacterSelect.route -> pagerState.animateScrollToPage(0)
                Screen.CharacterSheet.route -> pagerState.animateScrollToPage(1)
                Screen.Inventory.route -> pagerState.animateScrollToPage(2)
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = modifier
        ) { page ->
            when (page) {
                0 -> characterSelectScreen.Content(modifier)
                1 -> characterSheetScreen.Content(modifier)
                2 -> inventoryScreen.Content(modifier)
            }
        }

        // Hidden NavHost for programmatic navigation
        NavHost(
            navController = navController,
            startDestination = Screen.CharacterSelect.route,
            modifier = Modifier
        ) {
            composable(Screen.CharacterSelect.route) {
                // Empty composable as content is handled by HorizontalPager
            }
            composable(Screen.CharacterSheet.route) {
                // Empty composable as content is handled by HorizontalPager
            }
            composable(Screen.Inventory.route) {
                // Empty composable as content is handled by HorizontalPager
            }
        }
    }

    private sealed class Screen(val route: String) {
        object CharacterSelect : Screen("character_select")
        object CharacterSheet : Screen("character_sheet")
        object Inventory : Screen("inventory")
    }
} 