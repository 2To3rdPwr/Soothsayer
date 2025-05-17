package com.twotothirdpower.morkborgcharactersheet.dice.impl

import com.twotothirdpower.morkborgcharactersheet.dice.*
import javax.inject.Inject

class DiceRollerImpl @Inject constructor(
    private val randomProvider: RandomProvider
) : DiceRoller {
    override fun roll(diceRoll: DiceRoll): Int {
        val diceSum = (1..diceRoll.amount).sumOf {
            if (diceRoll.diceValue.value > 0) {
                randomProvider.nextInt(1, diceRoll.diceValue.value + 1)
            } else 0
        }
        return diceSum + diceRoll.miscModifier // TODO: StatType logic can be added as needed. Query current character stat for StatType modifier.
    }
} 