package com.twotothirdpower.morkborgcharactersheet.dice.impl

import com.twotothirdpower.morkborgcharactersheet.dice.*
import com.twotothirdpower.morkborgcharactersheet.domain.StatType
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.GetCurrentCharacterStatUseCase
import javax.inject.Inject

class DiceRollerImpl @Inject constructor(
    private val randomProvider: RandomProvider,
    private val getCurrentCharacterStatUseCase: GetCurrentCharacterStatUseCase
) : DiceRoller {
    override suspend fun roll(diceRoll: DiceRoll): Int {
        val diceSum = (1..diceRoll.amount).sumOf {
            if (diceRoll.diceValue.value > 0) {
                randomProvider.nextInt(1, diceRoll.diceValue.value + 1)
            } else 0
        }
        val statModifier = if (diceRoll.statType != StatType.NONE) {
            getCurrentCharacterStatUseCase(diceRoll.statType)
        } else 0
        return diceSum + diceRoll.miscModifier + statModifier
    }
} 