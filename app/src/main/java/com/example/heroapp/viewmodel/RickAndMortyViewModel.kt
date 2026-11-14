package com.example.heroapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heroapp.data.RickAndMortyRepository
import com.example.heroapp.model.ApiCharacter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RickAndMortyUiState(
    val characters: List<ApiCharacter> = emptyList(),
    val isInitialLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null
)

class RickAndMortyViewModel @Inject constructor(
    private val repository: RickAndMortyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        RickAndMortyUiState(isInitialLoading = true)
    )
    val uiState: StateFlow<RickAndMortyUiState> = _uiState

    private var currentPage = 1
    private var endReached = false
    private var isRequestInFlight = false

    init {
        loadPage(page = 1, isInitial = true)
    }

    private fun loadPage(page: Int, isInitial: Boolean) {
        if (isRequestInFlight) return
        if (endReached && !isInitial) return

        isRequestInFlight = true
        _uiState.value = _uiState.value.copy(
            isInitialLoading = isInitial,
            isLoadingMore = !isInitial,
            error = null
        )

        viewModelScope.launch {
            try {
                val response = repository.getCharacters(page)
                val newList =
                    if (page == 1) {
                        response.results
                    } else {
                        _uiState.value.characters + response.results
                    }
                endReached = response.info.next == null
                currentPage = page
                _uiState.value = _uiState.value.copy(
                    characters = newList,
                    isInitialLoading = false,
                    isLoadingMore = false,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isInitialLoading = false,
                    isLoadingMore = false,
                    error = e.message ?: "error"
                )
            } finally {
                isRequestInFlight = false
            }
        }
    }

    fun loadNextPage() {
        if (!endReached && !isRequestInFlight) {
            loadPage(page = currentPage + 1, isInitial = false)
        }
    }
}
