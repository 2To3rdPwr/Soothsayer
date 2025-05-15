package com.twotothirdpower.morkborgcharactersheet.characterselect.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twotothirdpower.morkborgcharactersheet.domain.models.CharacterListItem
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.DeleteCharacterUseCase
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.GetCharactersListUseCase
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.SelectCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterSelectViewModel @Inject constructor(
    getCharactersListUseCase: GetCharactersListUseCase,
    private val deleteCharacterUseCase: DeleteCharacterUseCase,
    private val selectCharacterUseCase: SelectCharacterUseCase
) : ViewModel() {
    val characters: Flow<List<CharacterListItem>> = getCharactersListUseCase().map { it.drop(1) }

    private val _navigateToCharacterSheet = MutableSharedFlow<Int>()
    val navigateToCharacterSheet: SharedFlow<Int> = _navigateToCharacterSheet

    fun deleteCharacter(character: CharacterListItem) {
        viewModelScope.launch {
            deleteCharacterUseCase(character.characterId)
        }
    }

    fun selectCharacter(characterId: Int) {
        viewModelScope.launch {
            selectCharacterUseCase(characterId)
            _navigateToCharacterSheet.emit(characterId)
        }
    }
} 