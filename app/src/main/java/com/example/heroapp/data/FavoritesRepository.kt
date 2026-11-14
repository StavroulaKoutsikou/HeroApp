package com.example.heroapp.data

import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    val favoriteIds: Flow<Set<Int>>
    suspend fun toggleFavorite(id: Int)
}
