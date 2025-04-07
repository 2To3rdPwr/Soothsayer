package com.twotothirdpower.morkborgcharactersheet.domain.impl.models

import com.twotothirdpower.morkborgcharactersheet.characterdata.CharacterEntity
import com.twotothirdpower.morkborgcharactersheet.domain.models.CharacterData

internal fun CharacterData.toEntity(): CharacterEntity = CharacterEntity(
    characterId = characterId,
    characterName = characterName,
    characterDescription = characterDescription,
    currentHp = currentHp,
    maxHp = maxHp,
    currentOmens = currentOmens,
    currentPowers = currentPowers,
    strength = strength,
    agility = agility,
    presence = presence,
    toughness = toughness,
    lastChanged = lastChanged
)

internal fun CharacterEntity.toDomain(): CharacterData = CharacterData(
    characterId = characterId,
    characterName = characterName,
    characterDescription = characterDescription,
    currentHp = currentHp,
    maxHp = maxHp,
    currentOmens = currentOmens,
    currentPowers = currentPowers,
    strength = strength,
    agility = agility,
    presence = presence,
    toughness = toughness,
    lastChanged = lastChanged
) 