package com.twotothirdpower.morkborgcharactersheet.dice

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit

interface DiceRollerInput {
    @Composable
    fun Content(
        modifier: Modifier,
        state: DiceState,
        showStatModifier: Boolean,
        onDiceRollUpdated: (DiceRoll) -> Unit,
        fontSize: TextUnit
    )
} 