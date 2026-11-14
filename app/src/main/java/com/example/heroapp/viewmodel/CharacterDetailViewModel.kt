package com.example.heroapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heroapp.data.RickAndMortyRepository
import com.example.heroapp.model.ApiCharacter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CharacterDetailUiState(
    val character: ApiCharacter? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

class CharacterDetailViewModel @Inject constructor(
    private val repository: RickAndMortyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharacterDetailUiState())
    val uiState: StateFlow<CharacterDetailUiState> = _uiState

    private var lastLoadedId: Int? = null

    fun load(characterId: Int) {
        if (lastLoadedId == characterId && _uiState.value.character != null) return
        lastLoadedId = characterId

        _uiState.value = CharacterDetailUiState(isLoading = true)

        viewModelScope.launch {
            try {
                val result = repository.getCharacter(characterId)
                _uiState.value = CharacterDetailUiState(
                    character = result,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = CharacterDetailUiState(
                    character = null,
                    isLoading = false,
                    error = e.message ?: "error"
                )
            }
        }
    }
}
