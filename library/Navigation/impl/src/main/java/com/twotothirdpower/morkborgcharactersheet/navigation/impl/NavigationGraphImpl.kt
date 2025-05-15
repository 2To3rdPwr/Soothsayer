package com.twotothirdpower.morkborgcharactersheet.navigation.impl

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.twotothirdpower.morkborgcharactersheet.characterselect.CharacterSelectScreen
import com.twotothirdpower.morkborgcharactersheet.charactersheet.CharacterSheetScreen
import com.twotothirdpower.morkborgcharactersheet.greeting.GreetingScreen
import com.twotothirdpower.morkborgcharactersheet.inventory.InventoryScreen
import com.twotothirdpower.morkborgcharactersheet.managecharacter.ManageCharacterScreen
import com.twotothirdpower.morkborgcharactersheet.navigation.NavigationGraph
import com.twotothirdpower.morkborgcharactersheet.topnav.TopNavBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class NavigationGraphImpl @Inject constructor(
    private val characterSelectScreen: CharacterSelectScreen,
    private val characterSheetScreen: CharacterSheetScreen,
    private val inventoryScreen: InventoryScreen,
    private val greetingScreen: GreetingScreen,
    private val topNavBar: TopNavBar,
    private val manageCharacterScreen: ManageCharacterScreen
) : NavigationGraph {
    @Composable
    override fun Content(
        navController: NavHostController,
        modifier: Modifier
    ) {
        val pagerState = rememberPagerState(initialPage = 1) { 3 }
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        val coroutineScope = rememberCoroutineScope()

        // Only sync pager state when we're in the main flow
        if (currentRoute == Screen.CharacterSelect.route || currentRoute == Screen.CharacterSheet.route || currentRoute == Screen.Inventory.route) {
            // Sync NavController with PagerState
            LaunchedEffect(pagerState.currentPage) {
                when (pagerState.currentPage) {
                    0 -> navController.navigate(Screen.CharacterSelect.route) {
                        popUpTo(Screen.CharacterSelect.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    1 -> navController.navigate(Screen.CharacterSheet.route) {
                        popUpTo(Screen.CharacterSheet.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    2 -> navController.navigate(Screen.Inventory.route) {
                        popUpTo(Screen.Inventory.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }

        NavHost(
            navController = navController,
            startDestination = Screen.Greeting.route,
            modifier = modifier
        ) {
            // Greeting screen - cannot be navigated back to
            composable(Screen.Greeting.route) {
                greetingScreen.Content(
                    modifier = modifier,
                    onGreetingComplete = { characterId ->
                        if (characterId != null) {
                            navController.navigate(Screen.CharacterSheet.route) {
                                popUpTo(Screen.Greeting.route) { inclusive = true }
                            }
                        } else {
                            navController.navigate(Screen.ManageCharacter.route) {
                                popUpTo(Screen.Greeting.route) { inclusive = true }
                            }
                        }
                    }
                )
            }

            // Empty composables for routes that are handled by the pager
            composable(Screen.CharacterSelect.route) {
                MainScreens(modifier, pagerState, coroutineScope, navController)
            }
            composable(Screen.CharacterSheet.route) {
                MainScreens(modifier, pagerState, coroutineScope, navController)
            }
            composable(Screen.Inventory.route) {
                MainScreens(modifier, pagerState, coroutineScope, navController)
            }

            // ManageCharacter screen
            composable(
                route = Screen.ManageCharacter.routeWithOptionalArg,
                arguments = listOf(
                    navArgument(Screen.ManageCharacter.characterIdArg) {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    }
                ),
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) },
                popEnterTransition = { fadeIn(animationSpec = tween(300)) },
                popExitTransition = { fadeOut(animationSpec = tween(300)) }
            ) { backStackEntry ->
                val characterId = backStackEntry.arguments?.getString(Screen.ManageCharacter.characterIdArg)?.toIntOrNull()
                manageCharacterScreen.Content(
                    modifier = modifier,
                    characterId = characterId,
                    onNavigateToCharacterSheet = {
                        navController.navigate(Screen.CharacterSheet.route) {
                            popUpTo(Screen.ManageCharacter.route) { inclusive = true }
                        }
                    }
                )
            }
        }
    }

    @Composable
    private fun MainScreens(
        modifier: Modifier,
        pagerState: PagerState,
        coroutineScope: CoroutineScope,
        navController: NavHostController
    ) {
        // Use a remembered state for the character name
        var characterName by remember { mutableStateOf<String?>(null) }
        
        EdgeToEdgeHandler(modifier) {
            Column(modifier = modifier) {
                topNavBar.Content(
                    modifier = Modifier,
                    selectedTab = pagerState.currentPage,
                    onTabSelected = { index ->
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    characterName = characterName
                )
                HorizontalPager(
                    state = pagerState,
                    modifier = modifier
                ) { page ->
                    when (page) {
                        0 -> characterSelectScreen.Content(
                            modifier = modifier,
                            onNavigateToNewCharacter = {
                                navController.navigate(Screen.ManageCharacter.route)
                            },
                            onNavigateToCharacterSheet = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(1) // CharacterSheet is at index 1
                                }
                            }
                        )
                        1 -> characterSheetScreen.Content(
                            modifier = modifier,
                            onCharacterNameChanged = { name ->
                                characterName = name
                            },
                            onEditCharacter = { characterId ->
                                navController.navigate(Screen.ManageCharacter.routeWithArgs(characterId))
                            }
                        )
                        2 -> inventoryScreen.Content(modifier)
                    }
                }
            }
        }
    }

    /**
     * Wraps navigation destination composables to allow custom handling of Edge-To-Edge cases
     * as some screens need to extend through the status bar, while others have content
     * that shouldn't overlap with it.
     *
     * TODO: See if this can be made somehow more universal?
     */
    @Composable
    private fun EdgeToEdgeHandler(
        modifier: Modifier,
        behindStatusBar: Boolean = false,
        content: @Composable (modifier: Modifier) -> Unit) {
        Surface {
            // TODO: Return later to extend TopNavBar beneath status bar
            val e2eModifier = if (behindStatusBar) {
                modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .imePadding()
            } else {
                modifier
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .imePadding()
            }
            Box (e2eModifier)
            {
                content(modifier)
            }
        }
    }

    private sealed class Screen(val route: String) {
        object Greeting : Screen("greeting")
        object CharacterSelect : Screen("character_select")
        object CharacterSheet : Screen("character_sheet")
        object Inventory : Screen("inventory")
        object ManageCharacter : Screen("manage_character") {
            const val characterIdArg = "characterId"
            val routeWithOptionalArg = "$route?$characterIdArg={$characterIdArg}"
            
            fun routeWithArgs(characterId: Int? = null) = when (characterId) {
                null -> route
                else -> "$route?$characterIdArg=$characterId"
            }
        }
    }
} 