package com.twotothirdpower.morkborgcharactersheet.domain.usecases

interface SelectCharacterUseCase {
    suspend operator fun invoke(characterId: Int)
} 