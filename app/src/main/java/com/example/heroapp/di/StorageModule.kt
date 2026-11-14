package com.example.heroapp.di

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.heroapp.data.FavoritesUseCase
import com.example.heroapp.data.FavoritesRepository
import com.example.heroapp.data.dataStore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule(
    private val app: Application
) {

    @Provides
    @Singleton
    fun providePreferencesDataStore(): DataStore<Preferences> {
        return app.dataStore
    }

    @Provides
    @Singleton
    fun provideFavoritesRepository(
        dataStore: DataStore<Preferences>
    ): FavoritesRepository {
        return FavoritesUseCase(dataStore)
    }
}
