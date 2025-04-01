package com.twotothirdpower.morkborgcharactersheet.characterselect.impl

import androidx.lifecycle.ViewModel
import com.twotothirdpower.morkborgcharactersheet.domain.models.CharacterListItem
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.GetCharactersListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharacterSelectViewModel @Inject constructor(
    private val getCharactersListUseCase: GetCharactersListUseCase
) : ViewModel() {
    val characters: Flow<List<CharacterListItem>> = getCharactersListUseCase()
} 