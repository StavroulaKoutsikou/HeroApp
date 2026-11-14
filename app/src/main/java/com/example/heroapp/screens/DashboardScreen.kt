package com.example.heroapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.heroapp.R
import com.example.heroapp.components.CharacterCard
import com.example.heroapp.components.HomeSection
import com.example.heroapp.components.ImageBanner
import com.example.heroapp.ui.theme.darkPurple
import com.example.heroapp.ui.theme.neonGreen
import com.example.heroapp.viewmodel.FavoritesViewModel
import com.example.heroapp.viewmodel.RickAndMortyViewModel

@Composable
fun DashboardScreen(
    navController: NavHostController,
    rickAndMortyViewModel: RickAndMortyViewModel,
    favoritesViewModel: FavoritesViewModel
) {
    val uiState = rickAndMortyViewModel.uiState.collectAsState().value
    val favoriteIds by favoritesViewModel.favoriteIds.collectAsState()
    val shuffledCharacters = remember(uiState.characters) { uiState.characters.shuffled() }
    val popularCharacters = uiState.characters.take(5)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(darkPurple),
        contentPadding = PaddingValues(bottom = 33.dp)
    ) {
        item {
            ImageBanner()
        }
        if (uiState.isInitialLoading && uiState.characters.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = neonGreen)
                }
            }
        }

        uiState.error?.let { errorMsg ->
            item {
                Text(
                    text = errorMsg,
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 14.sp
                )
            }
        }

        if (popularCharacters.isNotEmpty()) {
            item {
                HomeSection(title = R.string.most_popular_rick_and_morty_characters) {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        itemsIndexed(popularCharacters) { _, character ->
                            CharacterCard(
                                character = character,
                                modifier = Modifier.width(180.dp)
                            ) {
                                navController.navigate("detail/${character.id}")
                            }
                        }
                    }
                }
            }
        }

        item {
            HomeSection(title = R.string.my_favorite_characters) {
                val favoriteCharacters = uiState.characters.filter { character ->
                    favoriteIds.contains(character.id)
                }

                if (favoriteCharacters.isNotEmpty()) {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        itemsIndexed(favoriteCharacters) { _, character ->
                            CharacterCard(
                                character = character,
                                modifier = Modifier.width(180.dp)
                            ) {
                                navController.navigate("detail/${character.id}")
                            }
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                    )
                }
            }
        }

        if (shuffledCharacters.isNotEmpty()) {
            item {
                HomeSection(title = R.string.every_rick_and_morty_character) {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        itemsIndexed(shuffledCharacters) { index, character ->
                            if (index >= shuffledCharacters.size - 5) {
                                rickAndMortyViewModel.loadNextPage()
                            }

                            CharacterCard(
                                character = character,
                                modifier = Modifier.width(180.dp)
                            ) {
                                navController.navigate("detail/${character.id}")
                            }
                        }
                    }
                }
            }
        }

        if (uiState.isLoadingMore) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = neonGreen,
                        strokeWidth = 2.dp
                    )
                }
            }
        }
    }
}
