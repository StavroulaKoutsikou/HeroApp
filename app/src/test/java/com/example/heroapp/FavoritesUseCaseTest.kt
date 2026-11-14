package com.example.heroapp

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import com.example.heroapp.data.FavoritesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.jupiter.api.BeforeEach

private class FakeDataStore : DataStore<Preferences> {
    private val state = MutableStateFlow(emptyPreferences())
    override val data: Flow<Preferences> = state

    override suspend fun updateData(
        transform: suspend (Preferences) -> Preferences):
            Preferences {
                val newPrefs = transform(state.value)
                state.value = newPrefs
                return newPrefs
            }
}


class FavoritesManagerTest {

    private lateinit var fakeDataStore: FakeDataStore
    private lateinit var manager: FavoritesUseCase

    @BeforeEach
    fun setUp(){
        fakeDataStore = FakeDataStore()
        manager = FavoritesUseCase(fakeDataStore)
    }

    @Test
    fun `given empty FakeDataStore, when reading favoriteIds, then returns empty set`() = runTest {
        val result = manager.favoriteIds.first()

        assertEquals(emptySet<Int>(), result)
    }

    @Test
    fun `given empty favorites, when toggling one id, then set contains that id`() = runTest {
        manager.toggleFavorite(5)

        val result = manager.favoriteIds.first()

        assertEquals(setOf(5), result)
    }

    @Test
    fun `given empty favorites, when toggling multiple ids, then set contains all ids`() = runTest {
        manager.toggleFavorite(5)
        manager.toggleFavorite(6)
        manager.toggleFavorite(7)

        val result = manager.favoriteIds.first()

        assertEquals(setOf(5,6,7), result)
    }

    @Test
    fun `given existing favorite, when toggling same id, then id is removed`() = runTest {
        manager.toggleFavorite(10)
        manager.toggleFavorite(10)

        val result = manager.favoriteIds.first()

        assertEquals(emptySet<Int>(), result)
    }

    @Test
    fun `given multiple favorites, when toggling one id, then only that id is removed`()= runTest {
        manager.toggleFavorite(1)
        manager.toggleFavorite(2)
        manager.toggleFavorite(3)

        manager.toggleFavorite(2)

        val result = manager.favoriteIds.first()

        assertEquals(setOf(1,3), result)
    }
}
