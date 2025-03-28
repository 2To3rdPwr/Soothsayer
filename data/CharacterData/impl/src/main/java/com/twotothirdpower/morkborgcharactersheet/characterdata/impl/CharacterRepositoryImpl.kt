package com.twotothirdpower.morkborgcharactersheet.characterdata.impl

import com.twotothirdpower.morkborgcharactersheet.characterdata.CharacterData
import com.twotothirdpower.morkborgcharactersheet.characterdata.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterDao: CharacterDao
) : CharacterRepository {
    override fun getAllCharacters(): Flow<List<CharacterData>> {
        return characterDao.getAllCharacters()
    }

    override fun getCharacterById(id: Int): Flow<CharacterData> {
        return characterDao.getCharacterById(id)
    }

    override suspend fun insertCharacter(character: CharacterData) {
        characterDao.insertCharacter(character)
    }

    override suspend fun updateCharacter(character: CharacterData) {
        characterDao.updateCharacter(character)
    }

    override suspend fun deleteCharacter(character: CharacterData) {
        characterDao.deleteCharacter(character)
    }
} 