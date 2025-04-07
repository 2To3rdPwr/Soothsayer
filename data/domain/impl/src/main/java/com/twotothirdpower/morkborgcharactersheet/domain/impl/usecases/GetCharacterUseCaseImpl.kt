package com.twotothirdpower.morkborgcharactersheet.domain.impl.usecases

import com.twotothirdpower.morkborgcharactersheet.characterdata.CharacterRepository
import com.twotothirdpower.morkborgcharactersheet.domain.impl.models.toDomain
import com.twotothirdpower.morkborgcharactersheet.domain.models.CharacterData
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.GetCharacterUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCharacterUseCaseImpl @Inject constructor(
    private val repository: CharacterRepository
) : GetCharacterUseCase {
    override suspend fun invoke(characterId: Int): Flow<CharacterData?> {
        return repository.getCharacterById(characterId).map { entity ->
            entity?.toDomain()
        }
    }
} 