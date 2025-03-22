package com.twotothirdpower.morkborgcharactersheet.topnav.impl

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.twotothirdpower.morkborgcharactersheet.topnav.TopNavBar
import javax.inject.Inject

class TopNavBarImpl @Inject constructor() : TopNavBar {
    @Composable
    override fun Content(
        navController: NavController,
        modifier: Modifier
    ) {
        val items = listOf(
            NavigationItem(
                route = "character_select",
                icon = R.drawable.ic_character_select,
                label = "Characters"
            ),
            NavigationItem(
                route = "character_sheet",
                icon = R.drawable.ic_character_sheet,
                label = "Sheet"
            ),
            NavigationItem(
                route = "inventory",
                icon = R.drawable.ic_inventory,
                label = "Inventory"
            )
        )

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        NavigationBar(modifier = modifier) {
            items.forEach { item ->
                NavigationBarItem(
                    icon = { Icon(painter = painterResource(id = item.icon), contentDescription = item.label) },
                    label = { Text(text = item.label) },
                    selected = currentRoute == item.route,
                    onClick = {
                        if (currentRoute != item.route) {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    }

    private data class NavigationItem(
        val route: String,
        val icon: Int,
        val label: String
    )
} 