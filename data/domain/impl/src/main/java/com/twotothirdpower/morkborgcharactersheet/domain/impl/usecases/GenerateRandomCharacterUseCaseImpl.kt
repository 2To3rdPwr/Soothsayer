package com.twotothirdpower.morkborgcharactersheet.domain.impl.usecases

import com.twotothirdpower.morkborgcharactersheet.characterdata.CharacterEntity
import com.twotothirdpower.morkborgcharactersheet.characterdata.CharacterRepository
import com.twotothirdpower.morkborgcharactersheet.domain.impl.models.toDomain
import com.twotothirdpower.morkborgcharactersheet.domain.models.CharacterData
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.GenerateRandomCharacterUseCase
import javax.inject.Inject
import kotlin.random.Random

class GenerateRandomCharacterUseCaseImpl @Inject constructor(
    private val repository: CharacterRepository
) : GenerateRandomCharacterUseCase {

    private val names = listOf(
        "Grimvald the Forsaken",
        "Mortica Doomwhisper",
        "Karnax the Unclean",
        "Vesper Nightshade",
        "Thorne Rotbringer",
        "Malifica Bonecaller",
        "Dread Baron Vex",
        "Lady Pestilence",
        "Scourge the Nameless",
        "Wretch of the Wastes"
    )

    private val descriptions = listOf(
        "A wretched soul who traded their shadow for forbidden knowledge. Now they wander, casting no shadow but leaving trails of withered plants in their wake.",
        "Once a noble's food taster, they survived countless poisonings until their blood became toxic. Now they seek revenge on their former masters.",
        "A grave robber cursed by an ancient tomb. Their touch causes metal to rust and wood to rot, forcing them to wear gloves made from their own preserved skin.",
        "Born during an eclipse in a plague-stricken village. The only survivor, they learned to speak with the spirits of the deceased villagers.",
        "A former executioner who collected the last breaths of the condemned in glass vials. They say drinking these gives them visions of their victims' memories.",
        "Found as an infant in the belly of a dead dragon, raised by cultists who worship the apocalypse. Their dreams foretell disasters that always come true.",
        "A wandering prophet who reads omens in the patterns of dying insects. Their prophecies bring misfortune to those who hear them.",
        "The last survivor of a village that made a pact with dark forces. They carry the collective curse of their people, growing stronger as it slowly consumes them.",
        "A collector of cursed artifacts who gradually became one themselves. Their presence causes nearby shadows to writhe and whisper.",
        "An amnesiac who woke up in a mass grave with no memories but countless scars. Each scar tells a different horror story when touched."
    )

    override suspend fun invoke(): CharacterData {
        val entity = CharacterEntity(
            characterId = 0,
            characterName = names.random(),
            characterDescription = descriptions.random(),
            currentHp = Random.nextInt(4, 11),
            maxHp = Random.nextInt(4, 11),
            currentOmens = 0,
            currentPowers = 0,
            strength = Random.nextInt(-3, 7),
            agility = Random.nextInt(-3, 7),
            presence = Random.nextInt(-3, 7),
            toughness = Random.nextInt(-3, 7),
            lastChanged = System.currentTimeMillis()
        )
        repository.insertCharacter(entity)
        return entity.toDomain()
    }
} 