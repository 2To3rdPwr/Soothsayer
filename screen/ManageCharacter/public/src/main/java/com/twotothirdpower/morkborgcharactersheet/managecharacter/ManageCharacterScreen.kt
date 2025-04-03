package com.twotothirdpower.morkborgcharactersheet.managecharacter

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface ManageCharacterScreen {
    @Composable
    fun Content(
        modifier: Modifier,
        characterId: Int?,
        onNavigateToCharacterSheet: () -> Unit
    )
} 