package com.example.heroapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.heroapp.components.CharacterCard
import com.example.heroapp.components.EmptyFavoritesView
import com.example.heroapp.viewmodel.FavoritesViewModel
import com.example.heroapp.viewmodel.RickAndMortyViewModel
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import com.example.heroapp.ui.theme.backgroundPurple
import com.example.heroapp.ui.theme.darkPurple
import com.example.heroapp.ui.theme.softPurple

@Composable
fun FavoritesScreen(
    navController: NavHostController,
    rickAndMortyViewModel: RickAndMortyViewModel,
    favoritesViewModel: FavoritesViewModel
) {
    val uiState = rickAndMortyViewModel.uiState.collectAsState().value
    val favoriteIds by favoritesViewModel.favoriteIds.collectAsState()
    val favorites = remember(uiState.characters, favoriteIds) {
        uiState.characters.filter { it.id in favoriteIds }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(darkPurple)
            .padding(
                top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 12.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 12.dp
            )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "My Favourite Heroes",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = if (favorites.isEmpty()) "No favorites yet" else "${favorites.size} Heroes",
                color = softPurple,
                fontSize = 14.sp,
                textAlign = TextAlign.End
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        if (favorites.isEmpty()) {
            EmptyFavoritesView(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(favorites) { character ->
                    CharacterCard(
                        character = character,
                        onClick = {
                            navController.navigate("detail/${character.id}")
                        }
                    )
                }
            }
        }
    }
}
