package com.twotothirdpower.morkborgcharactersheet.managecharacter.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twotothirdpower.morkborgcharactersheet.domain.models.CharacterData
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.GenerateRandomCharacterUseCase
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.GetCharacterUseCase
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.ImproveCharacterUseCase
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.SaveCharacterUseCase
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
        val toughnessError: Boolean = false,
        val hpError: Boolean = false
    ) : ManageCharacterUiState
}

@HiltViewModel
class ManageCharacterViewModel @Inject constructor(
    private val generateRandomCharacterUseCase: GenerateRandomCharacterUseCase,
    private val getCharacterUseCase: GetCharacterUseCase,
    private val saveCharacterUseCase: SaveCharacterUseCase,
    private val improveCharacterUseCase: ImproveCharacterUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<ManageCharacterUiState>(ManageCharacterUiState.New)
    val uiState: StateFlow<ManageCharacterUiState> = _uiState

    private val _saveComplete = MutableSharedFlow<Unit>()
    val saveComplete: SharedFlow<Unit> = _saveComplete

    private val _validationError = MutableSharedFlow<String>()
    val validationError: SharedFlow<String> = _validationError

    fun generateRandomCharacter() {
        viewModelScope.launch {
            generateRandomCharacterUseCase()
        }
    }

    fun loadCharacter(characterId: Int) {
        viewModelScope.launch {
            val character = getCharacterUseCase(characterId).first()
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
        _uiState.value = currentState.copy(
            hp = hp,
            hpError = hp < 1
        )
    }

    fun updateStrength(strength: Int) {
        val currentState = _uiState.value as? ManageCharacterUiState.Edit ?: return
        _uiState.value = currentState.copy(
            strength = strength,
            strengthError = strength !in -3..6
        )
    }

    fun updateAgility(agility: Int) {
        val currentState = _uiState.value as? ManageCharacterUiState.Edit ?: return
        _uiState.value = currentState.copy(
            agility = agility,
            agilityError = agility !in -3..6
        )
    }

    fun updatePresence(presence: Int) {
        val currentState = _uiState.value as? ManageCharacterUiState.Edit ?: return
        _uiState.value = currentState.copy(
            presence = presence,
            presenceError = presence !in -3..6
        )
    }

    fun updateToughness(toughness: Int) {
        val currentState = _uiState.value as? ManageCharacterUiState.Edit ?: return
        _uiState.value = currentState.copy(
            toughness = toughness,
            toughnessError = toughness !in -3..6
        )
    }

    private fun validateStats(state: ManageCharacterUiState.Edit): Boolean {
        val strengthValid = state.strength in -3..6
        val agilityValid = state.agility in -3..6
        val presenceValid = state.presence in -3..6
        val toughnessValid = state.toughness in -3..6
        val hpValid = state.hp >= 1
        val nameValid = state.name.isNotBlank()

        _uiState.value = state.copy(
            nameError = !nameValid,
            strengthError = !strengthValid,
            agilityError = !agilityValid,
            presenceError = !presenceValid,
            toughnessError = !toughnessValid,
            hpError = !hpValid
        )

        val errorMessage = when {
            !strengthValid -> "Strength must be between -3 and 6"
            !agilityValid -> "Agility must be between -3 and 6"
            !presenceValid -> "Presence must be between -3 and 6"
            !toughnessValid -> "Toughness must be between -3 and 6"
            !hpValid -> "HP must be at least 1"
            else -> null
        }

        errorMessage?.let {
            viewModelScope.launch {
                _validationError.emit(it)
            }
        }

        return errorMessage == null && nameValid
    }

    fun saveCharacter() {
        val currentState = _uiState.value as? ManageCharacterUiState.Edit ?: return
        
        if (!validateStats(currentState)) {
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
            saveCharacterUseCase(character)
            _saveComplete.emit(Unit)
        }
    }

    fun improveCharacter() {
        val currentState = _uiState.value as? ManageCharacterUiState.Edit ?: return
        val characterId = currentState.characterId ?: return

        viewModelScope.launch {
            val character = getCharacterUseCase(characterId).first() ?: return@launch
            val improvedCharacter = improveCharacterUseCase(character)
            _uiState.value = currentState.copy(
                strength = improvedCharacter.strength,
                agility = improvedCharacter.agility,
                presence = improvedCharacter.presence,
                toughness = improvedCharacter.toughness
            )
        }
    }
} 