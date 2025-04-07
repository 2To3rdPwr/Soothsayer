package com.twotothirdpower.morkborgcharactersheet.characterdata

import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getAllCharacters(): Flow<List<CharacterEntity>>
    fun getCharacterById(characterId: Int): Flow<CharacterEntity?>
    suspend fun insertCharacter(character: CharacterEntity)
    suspend fun updateCharacter(character: CharacterEntity)
    suspend fun deleteCharacter(characterId: Int)
} 