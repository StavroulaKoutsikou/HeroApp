package com.example.heroapp

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.heroapp.navigation.BottomBar
import com.example.heroapp.screens.CharacterDetailScreen
import com.example.heroapp.screens.DashboardScreen
import com.example.heroapp.screens.FavoritesScreen
import com.example.heroapp.screens.FilterResultsScreen
import com.example.heroapp.screens.SearchScreen
import com.example.heroapp.screens.SettingsScreen
import com.example.heroapp.viewmodel.CharacterDetailViewModel
import com.example.heroapp.viewmodel.FavoritesViewModel
import com.example.heroapp.viewmodel.FilterResultsViewModel
import com.example.heroapp.viewmodel.RickAndMortyViewModel
import com.example.heroapp.viewmodel.SearchViewModel

@Composable
fun HeroApp(
    rickAndMortyViewModel: RickAndMortyViewModel,
    searchViewModel: SearchViewModel,
    favoritesViewModel: FavoritesViewModel,
    characterDetailViewModel: CharacterDetailViewModel,
    filterResultsViewModel: FilterResultsViewModel
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute != "splash"

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            contentWindowInsets = WindowInsets(0),
            bottomBar = {
                if (showBottomBar) BottomBar(navController)
            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = "dashboard",
                modifier = Modifier
                    .padding(padding)
            ) {
                composable("search") {
                    SearchScreen(
                        navController = navController,
                        viewModel = searchViewModel
                    )
                }

                composable("filter_results/{filter}") { backStackEntry ->
                    val filterName = backStackEntry.arguments?.getString("filter") ?: ""
                    FilterResultsScreen(
                        navController = navController,
                        filter = filterName,
                        viewModel = filterResultsViewModel
                    )
                }

                composable("dashboard") {
                    DashboardScreen(
                        navController = navController,
                        rickAndMortyViewModel = rickAndMortyViewModel,
                        favoritesViewModel = favoritesViewModel
                    )
                }

                composable("favorites") {
                    FavoritesScreen(
                        navController = navController,
                        rickAndMortyViewModel = rickAndMortyViewModel,
                        favoritesViewModel = favoritesViewModel
                    )
                }

                composable(
                    "detail/{characterId}",
                    listOf(navArgument("characterId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val characterId = backStackEntry.arguments?.getInt("characterId") ?: 0

                    CharacterDetailScreen(
                        navController = navController,
                        characterId = characterId,
                        detailViewModel = characterDetailViewModel,
                        favoritesViewModel = favoritesViewModel
                    )
                }

                composable("settings") {
                    SettingsScreen()
                }
            }
        }
    }
}
