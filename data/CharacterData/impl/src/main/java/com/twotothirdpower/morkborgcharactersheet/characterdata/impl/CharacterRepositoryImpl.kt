package com.twotothirdpower.morkborgcharactersheet.characterdata.impl

import com.twotothirdpower.morkborgcharactersheet.characterdata.CharacterEntity
import com.twotothirdpower.morkborgcharactersheet.characterdata.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val dao: CharacterDao
) : CharacterRepository {
    override fun getAllCharacters(): Flow<List<CharacterEntity>> {
        return dao.getAllCharacters()
    }

    override fun getCharacterById(characterId: Int): Flow<CharacterEntity?> {
        return dao.getCharacterById(characterId)
    }

    override suspend fun insertCharacter(character: CharacterEntity) {
        dao.insertCharacter(character)
    }

    override suspend fun updateCharacter(character: CharacterEntity) {
        dao.updateCharacter(character)
    }

    override suspend fun deleteCharacter(characterId: Int) {
        dao.deleteCharacter(characterId)
    }
} 