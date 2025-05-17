package com.twotothirdpower.morkborgcharactersheet.dice

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface DiceRollerInput {
    @Composable
    fun Content(
        modifier: Modifier,
        state: DiceState,
        showStatModifier: Boolean,
        onDiceRollUpdated: (DiceRoll) -> Unit
    )
} 