package com.twotothirdpower.morkborgcharactersheet.characterselect.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twotothirdpower.morkborgcharactersheet.domain.models.CharacterListItem
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.DeleteCharacterUseCase
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.GetCharactersListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterSelectViewModel @Inject constructor(
    getCharactersListUseCase: GetCharactersListUseCase,
    private val deleteCharacterUseCase: DeleteCharacterUseCase
) : ViewModel() {
    val characters: Flow<List<CharacterListItem>> = getCharactersListUseCase()

    fun deleteCharacter(character: CharacterListItem) {
        viewModelScope.launch {
            deleteCharacterUseCase(character.characterId)
        }
    }
} 