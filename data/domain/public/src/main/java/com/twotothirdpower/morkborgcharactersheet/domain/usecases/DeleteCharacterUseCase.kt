package com.twotothirdpower.morkborgcharactersheet.domain.usecases

interface DeleteCharacterUseCase {
    suspend operator fun invoke(characterId: Int)
} 