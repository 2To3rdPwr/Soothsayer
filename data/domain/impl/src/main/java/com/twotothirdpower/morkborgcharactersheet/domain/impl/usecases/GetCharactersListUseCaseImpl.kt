package com.twotothirdpower.morkborgcharactersheet.domain.impl.usecases

import com.twotothirdpower.morkborgcharactersheet.characterdata.CharacterRepository
import com.twotothirdpower.morkborgcharactersheet.domain.models.CharacterListItem
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.GetCharactersListUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCharactersListUseCaseImpl @Inject constructor(
    private val characterRepository: CharacterRepository
) : GetCharactersListUseCase {
    override fun invoke(): Flow<List<CharacterListItem>> {
        return characterRepository.getAllCharacters().map { characters ->
            characters.map { character ->
                CharacterListItem(
                    characterId = character.characterId,
                    characterName = character.characterName,
                    characterDescription = character.characterDescription,
                    str = character.strength,
                    agi = character.agility,
                    pres = character.presence,
                    tgh = character.toughness
                )
            }
        }
    }
} 