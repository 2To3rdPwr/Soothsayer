package com.twotothirdpower.morkborgcharactersheet.dice

import com.twotothirdpower.morkborgcharactersheet.domain.StatType

/**
 * Represents a possible dice roll configuration.
 */
data class DiceRoll(
    val amount: Int = 1,
    val diceValue: DiceValue = DiceValue.D20,
    val statType: StatType = StatType.NONE,
    val miscModifier: Int = 0
) {
    override fun toString(): String {
        val parts = mutableListOf<String>()
        if (amount > 0 && diceValue != DiceValue.D0) {
            parts.add("${'$'}amount D ${'$'}{diceValue.name}")
        }
        if (statType != StatType.NONE) {
            parts.add(statType.name)
        }
        if (miscModifier != 0) {
            val sign = if (miscModifier > 0) "+" else ""
            parts.add("${'$'}sign${'$'}miscModifier")
        }
        return parts.joinToString(" + ")
    }

    fun toDiceState(): DiceState {
        // Implementation will be provided in DiceState
        return DiceState(
            amount = amount,
            diceValue = diceValue,
            statType = statType,
            miscModifier = miscModifier
        )
    }
} 