package com.example.heroapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.heroapp.R
import com.example.heroapp.components.FavoriteHeart
import com.example.heroapp.ui.theme.backgroundPurple
import com.example.heroapp.ui.theme.green
import com.example.heroapp.ui.theme.grey
import com.example.heroapp.ui.theme.lightGreen
import com.example.heroapp.ui.theme.neonGreen
import com.example.heroapp.ui.theme.red
import com.example.heroapp.ui.theme.yellow
import com.example.heroapp.viewmodel.CharacterDetailViewModel
import com.example.heroapp.viewmodel.FavoritesViewModel
import androidx.compose.foundation.background as BoxBackground

@Composable
fun CharacterDetailScreen(
    navController: NavHostController,
    characterId: Int,
    detailViewModel: CharacterDetailViewModel,
    favoritesViewModel: FavoritesViewModel
) {
    val uiState by detailViewModel.uiState.collectAsState()
    val character = uiState.character
    val isLoading = uiState.isLoading
    val error = uiState.error

    val openSans = FontFamily(
        Font(R.font.opensans_italic_variablefont, FontWeight.Normal),
        Font(R.font.opensans_variablefont, FontWeight.Bold)
    )

    val titleStyle = TextStyle(
        fontFamily = openSans,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    )
    val bodyStyle = TextStyle(
        fontFamily = openSans,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    )

    val favoriteIds by favoritesViewModel.favoriteIds.collectAsState()

    LaunchedEffect(characterId) {
        detailViewModel.load(characterId)
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundPurple)
    ) {
        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = neonGreen)
                }
            }

            error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = error ?: "Error",
                        color = Color.Red,
                        style = bodyStyle
                    )
                }
            }

            character != null -> {
                val c = character
                val statusColor = when (c.status) {
                    "Alive" -> green
                    "Dead" -> red
                    else -> yellow
                }

                val isFavorite = favoriteIds.contains(c.id)

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(540.dp)
                    ) {
                        AsyncImage(
                            model = c.image,
                            contentDescription = c.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            backgroundPurple
                                        ),
                                        startY = 140f,
                                        endY = Float.POSITIVE_INFINITY
                                    )
                                )
                        )

                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(start = 18.dp, top = 18.dp)
                                .size(40.dp)
                                .clickable { navController.popBackStack() }
                        )

                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(18.dp)
                        ) {
                            FavoriteHeart(
                                isFavorite = isFavorite,
                                onClick = { favoritesViewModel.onFavoriteClicked(c.id) },
                                neonGreen = neonGreen,
                                purple = backgroundPurple
                            )
                        }

                        Text(
                            text = c.name,
                            color = Color.White,
                            style = titleStyle,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(start = 16.dp, bottom = 20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clip(CircleShape)
                                    .BoxBackground(statusColor)
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = c.status,
                                color = lightGreen,
                                style = bodyStyle.copy(
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Species: ${c.species}",
                            color = grey,
                            style = bodyStyle.copy(fontSize = 20.sp),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "Gender: ${c.gender}",
                            color = grey,
                            style = bodyStyle.copy(fontSize = 20.sp),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Origin: ${c.origin.name}",
                            color = grey,
                            style = bodyStyle.copy(fontSize = 20.sp),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "Location: ${c.location.name}",
                            color = grey,
                            style = bodyStyle.copy(fontSize = 20.sp),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(14.dp))
                    }
                }
            }
        }
    }
}
