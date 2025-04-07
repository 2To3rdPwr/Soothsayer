package com.twotothirdpower.morkborgcharactersheet.characterdata

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true)
    val characterId: Int,
    val lastChanged: Long,
    val characterName: String,
    val characterDescription: String,
    val currentHp: Int,
    val maxHp: Int,
    val currentOmens: Int,
    val currentPowers: Int,
    val strength: Int,
    val agility: Int,
    val presence: Int,
    val toughness: Int
) 