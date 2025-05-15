package com.twotothirdpower.morkborgcharactersheet.topnav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface TopNavBar {
    @Composable
    fun Content(
        modifier: Modifier,
        selectedTab: Int,
        onTabSelected: (Int) -> Unit,
        characterName: String?
    )
} 