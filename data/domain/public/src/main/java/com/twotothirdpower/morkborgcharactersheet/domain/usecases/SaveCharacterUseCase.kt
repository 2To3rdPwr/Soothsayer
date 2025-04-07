package com.twotothirdpower.morkborgcharactersheet.domain.usecases

import com.twotothirdpower.morkborgcharactersheet.domain.models.CharacterData

interface SaveCharacterUseCase {
    suspend operator fun invoke(character: CharacterData): CharacterData
} 