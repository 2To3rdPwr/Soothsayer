package com.twotothirdpower.morkborgcharactersheet.greeting

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface GreetingScreen {
    @Composable
    fun Content(
        modifier: Modifier,
        onGreetingComplete: () -> Unit
    )
} 