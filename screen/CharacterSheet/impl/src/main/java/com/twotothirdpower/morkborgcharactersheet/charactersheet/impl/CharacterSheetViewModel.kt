package com.twotothirdpower.morkborgcharactersheet.charactersheet.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.GetMostRecentCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.util.Log

@HiltViewModel
class CharacterSheetViewModel @Inject constructor(
    getMostRecentCharacterUseCase: GetMostRecentCharacterUseCase
) : ViewModel() {
    private val _characterName = MutableStateFlow<String?>(null)
    val characterName: StateFlow<String?> = _characterName

    init {
        viewModelScope.launch {
            getMostRecentCharacterUseCase()
                .catch { e ->
                    Log.e("CharacterSheetViewModel", "Error loading character", e)
                    _characterName.value = null
                }
                .onEach { character ->
                    _characterName.value = character?.characterName
                }
                .collect { }
        }
    }
} 