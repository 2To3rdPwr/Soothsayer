package com.twotothirdpower.morkborgcharactersheet.domain.usecases

import com.twotothirdpower.morkborgcharactersheet.characterdata.CharacterData

interface ImproveCharacterUseCase {
    suspend operator fun invoke(character: CharacterData): CharacterData
} 