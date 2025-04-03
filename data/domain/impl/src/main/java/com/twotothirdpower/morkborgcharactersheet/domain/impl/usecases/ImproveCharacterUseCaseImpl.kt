package com.twotothirdpower.morkborgcharactersheet.domain.impl.usecases

import com.twotothirdpower.morkborgcharactersheet.characterdata.CharacterData
import com.twotothirdpower.morkborgcharactersheet.characterdata.CharacterRepository
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.ImproveCharacterUseCase
import javax.inject.Inject
import kotlin.random.Random

class ImproveCharacterUseCaseImpl @Inject constructor(
    private val repository: CharacterRepository
) : ImproveCharacterUseCase {
    override suspend fun invoke(character: CharacterData): CharacterData {
        val improvedCharacter = character.copy(
            strength = (character.strength + Random.nextInt(0, 2)).coerceAtMost(6),
            agility = (character.agility + Random.nextInt(0, 2)).coerceAtMost(6),
            presence = (character.presence + Random.nextInt(0, 2)).coerceAtMost(6),
            toughness = (character.toughness + Random.nextInt(0, 2)).coerceAtMost(6),
            lastChanged = System.currentTimeMillis()
        )
        repository.updateCharacter(improvedCharacter)
        return improvedCharacter
    }
} 