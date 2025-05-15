package com.twotothirdpower.morkborgcharactersheet.greeting.impl

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

sealed interface GreetingUiState {
    object Loading : GreetingUiState
    data class Ready(val characterId: Int?) : GreetingUiState
}

@HiltViewModel
class GreetingViewModel @Inject constructor(
    getMostRecentCharacterUseCase: GetMostRecentCharacterUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<GreetingUiState>(GreetingUiState.Loading)
    val uiState: StateFlow<GreetingUiState> = _uiState

    init {
        viewModelScope.launch {
            getMostRecentCharacterUseCase()
                .catch { e ->
                    Log.e("GreetingViewModel", "Error loading character", e)
                    _uiState.value = GreetingUiState.Ready(null)
                }
                .onEach { character ->
                    _uiState.value = GreetingUiState.Ready(character?.characterId)
                }
                .collect { }
        }
    }
} 