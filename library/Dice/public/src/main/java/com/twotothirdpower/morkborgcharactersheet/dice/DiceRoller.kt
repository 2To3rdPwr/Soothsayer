package com.twotothirdpower.morkborgcharactersheet.dice

import com.twotothirdpower.morkborgcharactersheet.dice.DiceRoll
import com.twotothirdpower.morkborgcharactersheet.dice.DiceRollResult

interface DiceRoller {
    suspend fun roll(diceRoll: DiceRoll): DiceRollResult
} 