package com.example.heroapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heroapp.data.RickAndMortyRepository
import com.example.heroapp.model.ApiCharacter
import com.example.heroapp.model.QuickFilter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FilterResultsUiState(
    val results: List<ApiCharacter> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class FilterResultsViewModel @Inject constructor(
    private val repository: RickAndMortyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FilterResultsUiState())
    val uiState: StateFlow<FilterResultsUiState> = _uiState

    fun loadFilter(filter: QuickFilter) {
        _uiState.value = FilterResultsUiState(isLoading = true)

        viewModelScope.launch {
            try {
                val list = when (filter) {
                    QuickFilter.RICKS_AND_MORTYS ->
                        repository.getRicksAndMortys()

                    QuickFilter.HUMANS_FROM_EARTH ->
                        repository.getHumansFromEarth()

                    QuickFilter.ALIENS_AND_CREATURES ->
                        repository.getAliensAndCreatures()
                }

                _uiState.value = FilterResultsUiState(
                    results = list,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = FilterResultsUiState(
                    isLoading = false,
                    error = e.message ?: "Error"
                )
            }
        }
    }
}
