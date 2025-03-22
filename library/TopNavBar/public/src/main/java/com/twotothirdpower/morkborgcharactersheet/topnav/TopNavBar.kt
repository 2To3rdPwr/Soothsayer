package com.twotothirdpower.morkborgcharactersheet.topnav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

interface TopNavBar {
    @Composable
    fun Content(
        navController: NavController,
        modifier: Modifier
    )
} 