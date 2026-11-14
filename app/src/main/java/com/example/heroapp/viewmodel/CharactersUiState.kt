package com.example.heroapp.viewmodel

import com.example.heroapp.model.ApiCharacter

data class CharactersUiState(
    val isLoading: Boolean = false,
    val characters: List<ApiCharacter> = emptyList(),
    val error: String? = null,
    val isLastPage: Boolean = false
)
