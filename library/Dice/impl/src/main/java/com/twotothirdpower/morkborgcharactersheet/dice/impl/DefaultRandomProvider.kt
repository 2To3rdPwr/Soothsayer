package com.twotothirdpower.morkborgcharactersheet.dice.impl

class DefaultRandomProvider : RandomProvider {
    override fun nextInt(from: Int, until: Int): Int = (from until until).random()
} 