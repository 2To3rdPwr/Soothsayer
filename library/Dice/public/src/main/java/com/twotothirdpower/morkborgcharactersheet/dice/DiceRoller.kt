package com.twotothirdpower.morkborgcharactersheet.dice

interface DiceRoller {
    suspend fun roll(diceRoll: DiceRoll): Int
} 