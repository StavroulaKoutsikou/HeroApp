package com.example.heroapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heroapp.data.RickAndMortyRepository
import com.example.heroapp.model.ApiCharacter
import com.example.heroapp.model.QuickFilter
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val repository: RickAndMortyRepository
) : ViewModel() {

    var query by mutableStateOf("")
        private set

    var results by mutableStateOf<List<ApiCharacter>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    private var searchJob: Job? = null

    fun onQueryChange(newText: String) {
        query = newText
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)

            if (newText.isBlank()) {
                results = emptyList()
                return@launch
            }

            isLoading = true
            try {
                val response = repository.searchCharacters(newText)
                results = response.results
            } catch (e: Exception) {
                results = emptyList()
            } finally {
                isLoading = false
            }
        }
    }

    fun applyQuickFilter(filter: QuickFilter) {
        query = ""
        searchJob?.cancel()
        isLoading = true
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
                results = list
            } catch (e: Exception) {
                results = emptyList()
            } finally {
                isLoading = false
            }
        }
    }
}
