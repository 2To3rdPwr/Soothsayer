package com.twotothirdpower.morkborgcharactersheet.characterdata.impl

import androidx.room.*
import androidx.room.Dao
import com.twotothirdpower.morkborgcharactersheet.characterdata.CharacterData
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters")
    fun getAllCharacters(): Flow<List<CharacterData>>

    @Query("SELECT * FROM characters WHERE characterId = :id")
    fun getCharacterById(id: Int): Flow<CharacterData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterData)

    @Update
    suspend fun updateCharacter(character: CharacterData)

    @Delete
    suspend fun deleteCharacter(character: CharacterData)
} 