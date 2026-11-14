package com.example.heroapp.di

import com.example.heroapp.network.RickAndMortyApiService
import com.example.heroapp.data.RickAndMortyRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRickAndMortyRepository(
        api: RickAndMortyApiService
    ): RickAndMortyRepository {
        return RickAndMortyRepository(api)
    }
}
