package com.example.heroapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heroapp.data.FavoritesRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel @Inject constructor(
    private val favoritesRepo: FavoritesRepository
) : ViewModel() {

    val favoriteIds: StateFlow<Set<Int>> =
        favoritesRepo.favoriteIds
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = emptySet()
            )

    fun onFavoriteClicked(characterId: Int) {
        viewModelScope.launch {
            favoritesRepo.toggleFavorite(characterId)
        }
    }
}
