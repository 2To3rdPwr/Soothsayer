package com.twotothirdpower.morkborgcharactersheet.domain.usecases

import com.twotothirdpower.morkborgcharactersheet.domain.models.CharacterData

interface GenerateRandomCharacterUseCase {
    suspend operator fun invoke(): CharacterData
} 