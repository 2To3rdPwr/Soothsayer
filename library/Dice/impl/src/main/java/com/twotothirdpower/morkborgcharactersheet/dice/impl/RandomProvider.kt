package com.twotothirdpower.morkborgcharactersheet.dice.impl

interface RandomProvider {
    fun nextInt(from: Int, until: Int): Int
} 