package com.twotothirdpower.morkborgcharactersheet.characterselect

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface CharacterSelectScreen {
    @Composable
    fun Content(
        modifier: Modifier,
        onNavigateToNewCharacter: () -> Unit,
        onNavigateToEditCharacter: (Int) -> Unit
    )
} 