package com.twotothirdpower.morkborgcharactersheet.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

interface NavigationGraph {
    @Composable
    fun Content(
        navController: NavHostController,
        modifier: Modifier
    )
} 