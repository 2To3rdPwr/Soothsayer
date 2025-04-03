package com.twotothirdpower.morkborgcharactersheet.domain.impl.usecases

import com.twotothirdpower.morkborgcharactersheet.characterdata.CharacterData
import com.twotothirdpower.morkborgcharactersheet.characterdata.CharacterRepository
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.AddCharacterUseCase
import javax.inject.Inject

class AddCharacterUseCaseImpl @Inject constructor(
    private val repository: CharacterRepository
) : AddCharacterUseCase {
    override suspend fun invoke() {
        // Create a new character with random attributes
        val randomCharacter = CharacterData(
            characterId = 0, // Room will auto-generate this
            characterName = "Character ${System.currentTimeMillis()}",
            characterDescription = "A random character",
            currentHp = 10,
            maxHp = 10,
            currentOmens = 0,
            currentPowers = 0,
            strength = (1..6).random(),
            agility = (1..6).random(),
            presence = (1..6).random(),
            toughness = (1..6).random(),
            lastChanged = System.currentTimeMillis()
        )
        repository.insertCharacter(randomCharacter)
    }
} 