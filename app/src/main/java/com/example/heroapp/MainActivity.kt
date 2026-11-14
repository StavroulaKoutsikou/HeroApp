package com.example.heroapp

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.animation.addListener
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.heroapp.di.HeroViewModelFactory
import com.example.heroapp.viewmodel.CharacterDetailViewModel
import com.example.heroapp.viewmodel.FavoritesViewModel
import com.example.heroapp.viewmodel.FilterResultsViewModel
import com.example.heroapp.viewmodel.RickAndMortyViewModel
import com.example.heroapp.viewmodel.SearchViewModel

class MainActivity : ComponentActivity() {

    private val viewModelFactory: HeroViewModelFactory by lazy {
        (application as HeroApplication).appComponent.viewModelFactory()
    }
    private val rickAndMortyViewModel: RickAndMortyViewModel by viewModels {
        viewModelFactory
    }
    private val searchViewModel: SearchViewModel by viewModels {
        viewModelFactory
    }
    private val favoritesViewModel: FavoritesViewModel by viewModels {
        viewModelFactory
    }

    private val characterDetailViewModel: CharacterDetailViewModel by viewModels {
        viewModelFactory
    }

    private val filterResultsViewModel: FilterResultsViewModel
            by viewModels { viewModelFactory }


    override fun onCreate(savedInstanceState: Bundle?) {

        val splash = installSplashScreen()

        splash.setOnExitAnimationListener { splashView ->

            val slideUp = ObjectAnimator.ofFloat(
                splashView.view, View.TRANSLATION_Y,
                0f,
                -splashView.view.height * 0.15f
            ).apply {
                duration = 3000
                interpolator = DecelerateInterpolator()
            }

            val fade = ObjectAnimator.ofFloat(
                splashView.view,
                View.ALPHA, 1f,
                0f
            ).apply {
                duration = 3000
                interpolator = DecelerateInterpolator()
            }
            slideUp.start(); fade.start()
            fade.addListener(onEnd = { splashView.remove() })
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            HeroApp(
                rickAndMortyViewModel = rickAndMortyViewModel,
                searchViewModel = searchViewModel,
                favoritesViewModel = favoritesViewModel,
                filterResultsViewModel = filterResultsViewModel,
                characterDetailViewModel = characterDetailViewModel
            )
        }
    }
}
