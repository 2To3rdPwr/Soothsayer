package com.twotothirdpower.morkborgcharactersheet.domain.models

data class CharacterData(
    val characterId: Int = 0,
    val characterName: String = "",
    val characterDescription: String = "",
    val currentHp: Int = 10,
    val maxHp: Int = 10,
    val currentOmens: Int = 0,
    val currentPowers: Int = 0,
    val strength: Int = 0,
    val agility: Int = 0,
    val presence: Int = 0,
    val toughness: Int = 0,
    val lastChanged: Long = System.currentTimeMillis()
) 