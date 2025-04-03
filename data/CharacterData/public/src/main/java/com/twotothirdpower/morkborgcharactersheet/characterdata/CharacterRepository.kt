package com.twotothirdpower.morkborgcharactersheet.characterdata

import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getAllCharacters(): Flow<List<CharacterData>>
    fun getCharacterById(characterId: Int): Flow<CharacterData?>
    suspend fun insertCharacter(character: CharacterData)
    suspend fun updateCharacter(character: CharacterData)
    suspend fun deleteCharacter(characterId: Int)
} 