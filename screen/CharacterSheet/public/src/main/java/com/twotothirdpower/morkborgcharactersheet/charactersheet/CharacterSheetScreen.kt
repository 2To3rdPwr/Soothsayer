package com.twotothirdpower.morkborgcharactersheet.charactersheet

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface CharacterSheetScreen {
    @Composable
    fun Content(
        modifier: Modifier,
        onCharacterNameChanged: (String?) -> Unit,
        onEditCharacter: (Int) -> Unit
    )
} 