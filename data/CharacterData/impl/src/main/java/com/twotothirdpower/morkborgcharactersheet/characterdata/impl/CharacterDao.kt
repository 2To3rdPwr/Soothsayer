package com.twotothirdpower.morkborgcharactersheet.characterdata.impl

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.twotothirdpower.morkborgcharactersheet.characterdata.CharacterData
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters ORDER BY lastChanged DESC")
    fun getAllCharacters(): Flow<List<CharacterData>>

    @Query("SELECT * FROM characters WHERE characterId = :characterId")
    fun getCharacterById(characterId: Int): Flow<CharacterData?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterData)

    @Update
    suspend fun updateCharacter(character: CharacterData)

    @Query("DELETE FROM characters WHERE characterId = :characterId")
    suspend fun deleteCharacter(characterId: Int)
} 