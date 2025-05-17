package com.twotothirdpower.morkborgcharactersheet.dice.impl

import com.twotothirdpower.morkborgcharactersheet.dice.*
import com.twotothirdpower.morkborgcharactersheet.dice.DiceRollResult
import com.twotothirdpower.morkborgcharactersheet.domain.StatType
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.GetCurrentCharacterStatUseCase
import javax.inject.Inject

class DiceRollerImpl @Inject constructor(
    private val randomProvider: RandomProvider,
    private val getCurrentCharacterStatUseCase: GetCurrentCharacterStatUseCase
) : DiceRoller {
    override suspend fun roll(diceRoll: DiceRoll): DiceRollResult {
        // Only one die, D20, crit/fumble logic applies
        if (diceRoll.amount == 1 && diceRoll.diceValue == DiceValue.D20) {
            val unmodifiedRoll = randomProvider.nextInt(1, 21)
            val statModifier = if (diceRoll.statType != StatType.NONE) {
                getCurrentCharacterStatUseCase(diceRoll.statType)
            } else 0
            val result = unmodifiedRoll + diceRoll.miscModifier + statModifier
            val crit = unmodifiedRoll == 20
            val fumble = unmodifiedRoll == 1
            return DiceRollResult(result = result, crit = crit, fumble = fumble)
        } else {
            val diceSum = (1..diceRoll.amount).sumOf {
                if (diceRoll.diceValue.value > 0) {
                    randomProvider.nextInt(1, diceRoll.diceValue.value + 1)
                } else 0
            }
            val statModifier = if (diceRoll.statType != StatType.NONE) {
                getCurrentCharacterStatUseCase(diceRoll.statType)
            } else 0
            val result = diceSum + diceRoll.miscModifier + statModifier
            return DiceRollResult(result = result)
        }
    }
} 