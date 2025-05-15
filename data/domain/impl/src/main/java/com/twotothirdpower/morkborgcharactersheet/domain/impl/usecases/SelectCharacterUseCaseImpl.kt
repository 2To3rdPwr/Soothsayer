package com.twotothirdpower.morkborgcharactersheet.domain.impl.usecases

import com.twotothirdpower.morkborgcharactersheet.domain.usecases.GetCharacterUseCase
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.SaveCharacterUseCase
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.SelectCharacterUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SelectCharacterUseCaseImpl @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val saveCharacterUseCase: SaveCharacterUseCase
) : SelectCharacterUseCase {
    override suspend fun invoke(characterId: Int) {
        val character = getCharacterUseCase(characterId).first() ?: return
        saveCharacterUseCase(character)
    }
} 