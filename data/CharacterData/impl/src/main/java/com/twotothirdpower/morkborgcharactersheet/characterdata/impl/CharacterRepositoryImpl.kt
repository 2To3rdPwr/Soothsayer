package com.twotothirdpower.morkborgcharactersheet.characterdata.impl

import com.twotothirdpower.morkborgcharactersheet.characterdata.CharacterData
import com.twotothirdpower.morkborgcharactersheet.characterdata.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val dao: CharacterDao
) : CharacterRepository {
    override fun getAllCharacters(): Flow<List<CharacterData>> {
        return dao.getAllCharacters()
    }

    override fun getCharacterById(characterId: Int): Flow<CharacterData?> {
        return dao.getCharacterById(characterId)
    }

    override suspend fun insertCharacter(character: CharacterData) {
        dao.insertCharacter(character)
    }

    override suspend fun updateCharacter(character: CharacterData) {
        dao.updateCharacter(character)
    }

    override suspend fun deleteCharacter(characterId: Int) {
        dao.deleteCharacter(characterId)
    }
} 