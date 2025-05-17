package com.twotothirdpower.morkborgcharactersheet.dice

data class DiceRollResult(
    val result: Int,
    val crit: Boolean = false,
    val fumble: Boolean = false
) 