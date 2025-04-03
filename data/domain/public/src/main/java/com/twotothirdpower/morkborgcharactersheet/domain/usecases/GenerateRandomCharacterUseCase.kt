package com.twotothirdpower.morkborgcharactersheet.domain.usecases

import com.twotothirdpower.morkborgcharactersheet.characterdata.CharacterData

interface GenerateRandomCharacterUseCase {
    suspend operator fun invoke(): CharacterData
} 