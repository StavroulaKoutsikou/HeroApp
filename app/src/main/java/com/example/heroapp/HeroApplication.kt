package com.example.heroapp

import android.app.Application
import com.example.heroapp.di.AppComponent
import com.example.heroapp.di.DaggerAppComponent
import com.example.heroapp.di.NetworkModule
import com.example.heroapp.di.RepositoryModule
import com.example.heroapp.di.StorageModule

class HeroApplication : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .networkModule(NetworkModule())
            .repositoryModule(RepositoryModule())
            .storageModule(StorageModule(this))
            .build()
    }
}
