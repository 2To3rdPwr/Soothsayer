package com.twotothirdpower.morkborgcharactersheet.domain.models

data class CharacterListItem(
    val characterId: Int,
    val characterName: String,
    val characterDescription: String,
    val str: Int,
    val agi: Int,
    val pres: Int,
    val tgh: Int
) 