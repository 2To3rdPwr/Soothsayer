package com.twotothirdpower.morkborgcharactersheet.domain.impl.usecases

import com.twotothirdpower.morkborgcharactersheet.domain.StatType
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.GetCurrentCharacterStatUseCase
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.GetMostRecentCharacterUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull

class GetCurrentCharacterStatUseCaseImpl @Inject constructor(
    private val getMostRecentCharacterUseCase: GetMostRecentCharacterUseCase
) : GetCurrentCharacterStatUseCase {
    override suspend fun invoke(statType: StatType): Int {
        val character = getMostRecentCharacterUseCase().firstOrNull() ?: return 0
        return when (statType) {
            StatType.STRENGTH -> character.strength
            StatType.AGILITY -> character.agility
            StatType.PRESENCE -> character.presence
            StatType.TOUGHNESS -> character.toughness
            StatType.NONE -> 0
        }
    }
} 