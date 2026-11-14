package com.example.heroapp.di

import com.example.heroapp.data.RickAndMortyRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class,
        StorageModule::class
    ]
)
interface AppComponent {

    fun rickAndMortyRepository(): RickAndMortyRepository
    fun viewModelFactory(): HeroViewModelFactory
}
