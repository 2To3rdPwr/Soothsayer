package com.twotothirdpower.morkborgcharactersheet.managecharacter.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twotothirdpower.morkborgcharactersheet.characterdata.CharacterData
import com.twotothirdpower.morkborgcharactersheet.characterdata.CharacterRepository
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.GenerateRandomCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ManageCharacterUiState {
    data object New : ManageCharacterUiState
    data class Edit(
        val characterId: Int? = null,
        val name: String = "",
        val description: String = "",
        val hp: Int = 10,
        val strength: Int = 0,
        val agility: Int = 0,
        val presence: Int = 0,
        val toughness: Int = 0,
        val nameError: Boolean = false,
        val strengthError: Boolean = false,
        val agilityError: Boolean = false,
        val presenceError: Boolean = false,
        val toughnessError: Boolean = false
    ) : ManageCharacterUiState
}

@HiltViewModel
class ManageCharacterViewModel @Inject constructor(
    private val generateRandomCharacterUseCase: GenerateRandomCharacterUseCase,
    private val characterRepository: CharacterRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<ManageCharacterUiState>(ManageCharacterUiState.New)
    val uiState: StateFlow<ManageCharacterUiState> = _uiState

    private val _saveComplete = MutableSharedFlow<Unit>()
    val saveComplete: SharedFlow<Unit> = _saveComplete

    fun generateRandomCharacter() {
        viewModelScope.launch {
            generateRandomCharacterUseCase()
        }
    }

    fun loadCharacter(characterId: Int) {
        viewModelScope.launch {
            val character = characterRepository.getCharacterById(characterId).first()
            character?.let { char ->
                _uiState.value = ManageCharacterUiState.Edit(
                    characterId = char.characterId,
                    name = char.characterName,
                    description = char.characterDescription,
                    hp = char.currentHp,
                    strength = char.strength,
                    agility = char.agility,
                    presence = char.presence,
                    toughness = char.toughness
                )
            }
        }
    }

    fun switchToEditState() {
        _uiState.value = ManageCharacterUiState.Edit()
    }

    fun updateName(name: String) {
        val currentState = _uiState.value as? ManageCharacterUiState.Edit ?: return
        _uiState.value = currentState.copy(
            name = name,
            nameError = false
        )
    }

    fun updateDescription(description: String) {
        val currentState = _uiState.value as? ManageCharacterUiState.Edit ?: return
        _uiState.value = currentState.copy(description = description)
    }

    fun updateHp(hp: Int) {
        val currentState = _uiState.value as? ManageCharacterUiState.Edit ?: return
        _uiState.value = currentState.copy(hp = hp)
    }

    fun updateStrength(strength: Int) {
        val currentState = _uiState.value as? ManageCharacterUiState.Edit ?: return
        _uiState.value = currentState.copy(
            strength = strength,
            strengthError = false
        )
    }

    fun updateAgility(agility: Int) {
        val currentState = _uiState.value as? ManageCharacterUiState.Edit ?: return
        _uiState.value = currentState.copy(
            agility = agility,
            agilityError = false
        )
    }

    fun updatePresence(presence: Int) {
        val currentState = _uiState.value as? ManageCharacterUiState.Edit ?: return
        _uiState.value = currentState.copy(
            presence = presence,
            presenceError = false
        )
    }

    fun updateToughness(toughness: Int) {
        val currentState = _uiState.value as? ManageCharacterUiState.Edit ?: return
        _uiState.value = currentState.copy(
            toughness = toughness,
            toughnessError = false
        )
    }

    private fun validateCharacter(state: ManageCharacterUiState.Edit): Boolean {
        var isValid = true
        val updatedState = state.copy(
            nameError = state.name.isBlank(),
            strengthError = state.strength !in 0..6,
            agilityError = state.agility !in 0..6,
            presenceError = state.presence !in 0..6,
            toughnessError = state.toughness !in 0..6
        )
        
        if (updatedState.nameError || updatedState.strengthError || 
            updatedState.agilityError || updatedState.presenceError || 
            updatedState.toughnessError) {
            isValid = false
            _uiState.value = updatedState
        }
        
        return isValid
    }

    fun saveCharacter() {
        val currentState = _uiState.value as? ManageCharacterUiState.Edit ?: return
        
        if (!validateCharacter(currentState)) {
            return
        }

        viewModelScope.launch {
            val character = CharacterData(
                characterId = currentState.characterId ?: 0,
                characterName = currentState.name,
                characterDescription = currentState.description,
                currentHp = currentState.hp,
                maxHp = currentState.hp,
                currentOmens = 0,
                currentPowers = 0,
                strength = currentState.strength,
                agility = currentState.agility,
                presence = currentState.presence,
                toughness = currentState.toughness,
                lastChanged = System.currentTimeMillis()
            )
            characterRepository.insertCharacter(character)
            _saveComplete.emit(Unit)
        }
    }
} 