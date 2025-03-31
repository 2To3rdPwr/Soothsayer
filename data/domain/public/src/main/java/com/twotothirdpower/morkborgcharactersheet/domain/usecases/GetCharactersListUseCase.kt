package com.twotothirdpower.morkborgcharactersheet.domain.usecases

import com.twotothirdpower.morkborgcharactersheet.domain.models.CharacterListItem
import kotlinx.coroutines.flow.Flow

interface GetCharactersListUseCase {
    operator fun invoke(): Flow<List<CharacterListItem>>
} 