package com.twotothirdpower.morkborgcharactersheet.domain.impl.usecases

import com.twotothirdpower.morkborgcharactersheet.characterdata.CharacterRepository
import com.twotothirdpower.morkborgcharactersheet.domain.impl.models.toDomain
import com.twotothirdpower.morkborgcharactersheet.domain.models.CharacterData
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.GetMostRecentCharacterUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMostRecentCharacterUseCaseImpl @Inject constructor(
    private val repository: CharacterRepository
) : GetMostRecentCharacterUseCase {
    override fun invoke(): Flow<CharacterData?> {
        return repository.getAllCharacters().map { list ->
            list.maxByOrNull { it.lastChanged }?.toDomain()
        }
    }
} 