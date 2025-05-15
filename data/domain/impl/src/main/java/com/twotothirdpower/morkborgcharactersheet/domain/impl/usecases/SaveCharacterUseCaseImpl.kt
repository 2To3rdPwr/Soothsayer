package com.twotothirdpower.morkborgcharactersheet.domain.impl.usecases

import com.twotothirdpower.morkborgcharactersheet.characterdata.CharacterRepository
import com.twotothirdpower.morkborgcharactersheet.domain.impl.models.toDomain
import com.twotothirdpower.morkborgcharactersheet.domain.impl.models.toEntity
import com.twotothirdpower.morkborgcharactersheet.domain.models.CharacterData
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.SaveCharacterUseCase
import javax.inject.Inject

class SaveCharacterUseCaseImpl @Inject constructor(
    private val repository: CharacterRepository
) : SaveCharacterUseCase {
    override suspend fun invoke(character: CharacterData): CharacterData {
        val now = System.currentTimeMillis()
        val updatedCharacter = character.copy(lastChanged = now)
        val entity = updatedCharacter.toEntity()
        
        // If characterId is 0, it's a new character, otherwise update existing
        if (character.characterId == 0) {
            repository.insertCharacter(entity)
        } else {
            repository.updateCharacter(entity)
        }
        
        return entity.toDomain()
    }
} 