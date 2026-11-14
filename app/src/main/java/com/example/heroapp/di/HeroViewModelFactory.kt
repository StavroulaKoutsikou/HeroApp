package com.example.heroapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.heroapp.viewmodel.FavoritesViewModel
import com.example.heroapp.viewmodel.RickAndMortyViewModel
import com.example.heroapp.viewmodel.SearchViewModel
import javax.inject.Inject
import javax.inject.Provider
import com.example.heroapp.viewmodel.CharacterDetailViewModel
import com.example.heroapp.viewmodel.FilterResultsViewModel


class HeroViewModelFactory @Inject constructor(
    private val rickAndMortyViewModelProvider: Provider<RickAndMortyViewModel>,
    private val searchViewModelProvider: Provider<SearchViewModel>,
    private val favoritesViewModelProvider: Provider<FavoritesViewModel>,
    private val characterDetailViewModelProvider: Provider<CharacterDetailViewModel>,
    private val filterResultsViewModelProvider: Provider<FilterResultsViewModel>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            RickAndMortyViewModel::class.java -> rickAndMortyViewModelProvider.get() as T
            SearchViewModel::class.java -> searchViewModelProvider.get() as T
            FavoritesViewModel::class.java -> favoritesViewModelProvider.get() as T
            CharacterDetailViewModel::class.java -> characterDetailViewModelProvider.get() as T
            FilterResultsViewModel::class.java -> filterResultsViewModelProvider.get() as T
            else -> throw IllegalArgumentException("Unknown ViewModel class $modelClass")
        }
    }
}
