package com.twotothirdpower.morkborgcharactersheet.domain.usecases

import com.twotothirdpower.morkborgcharactersheet.domain.models.CharacterData
import kotlinx.coroutines.flow.Flow

interface GetMostRecentCharacterUseCase {
    operator fun invoke(): Flow<CharacterData?>
} 