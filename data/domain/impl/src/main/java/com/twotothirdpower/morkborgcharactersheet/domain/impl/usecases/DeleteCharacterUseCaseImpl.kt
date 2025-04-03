package com.twotothirdpower.morkborgcharactersheet.domain.impl.usecases

import com.twotothirdpower.morkborgcharactersheet.characterdata.CharacterRepository
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.DeleteCharacterUseCase
import javax.inject.Inject

class DeleteCharacterUseCaseImpl @Inject constructor(
    private val repository: CharacterRepository
) : DeleteCharacterUseCase {
    override suspend fun invoke(characterId: Int) {
        repository.deleteCharacter(characterId)
    }
} 