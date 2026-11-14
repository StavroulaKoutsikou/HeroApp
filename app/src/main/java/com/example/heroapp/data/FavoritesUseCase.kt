package com.example.heroapp.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesUseCase(
    private val dataStore: DataStore<Preferences>
) : FavoritesRepository{
    companion object {
        private val FAVORITES_KEY = stringSetPreferencesKey("favorite_character_ids")
    }
    override val favoriteIds: Flow<Set<Int>> = dataStore.data.map { prefs ->
        val storedSet = prefs[FAVORITES_KEY] ?: emptySet()
        storedSet.mapNotNull { it.toIntOrNull() }.toSet()
    }

    override suspend fun toggleFavorite(characterId: Int) {
        dataStore.edit { prefs ->
            val current = prefs[FAVORITES_KEY]?.toMutableSet() ?: mutableSetOf()
            val idString = characterId.toString()

            if (current.contains(idString)) {
                current.remove(idString)
            } else {
                current.add(idString)
            }
            prefs[FAVORITES_KEY] = current
        }
    }
}
