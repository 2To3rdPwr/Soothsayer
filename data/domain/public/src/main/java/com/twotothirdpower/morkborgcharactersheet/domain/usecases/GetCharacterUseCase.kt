package com.twotothirdpower.morkborgcharactersheet.domain.usecases

import com.twotothirdpower.morkborgcharactersheet.domain.models.CharacterData
import kotlinx.coroutines.flow.Flow

interface GetCharacterUseCase {
    suspend operator fun invoke(characterId: Int): Flow<CharacterData?>
} 