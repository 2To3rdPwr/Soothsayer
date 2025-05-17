package com.twotothirdpower.morkborgcharactersheet.dice

import com.twotothirdpower.morkborgcharactersheet.domain.StatType

/**
 * State holder for DiceRollerInput composable.
 */
data class DiceState(
    val amount: Int = 1,
    val diceValue: DiceValue = DiceValue.D20,
    val statType: StatType = StatType.NONE,
    val miscModifier: Int = 0
) {
    fun toDiceRoll(): DiceRoll = DiceRoll(
        amount = amount,
        diceValue = diceValue,
        statType = statType,
        miscModifier = miscModifier
    )
} 