package com.example.heroapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.heroapp.R
import com.example.heroapp.components.CharacterCard
import com.example.heroapp.components.QuickFilterRow
import com.example.heroapp.ui.theme.darkPurple
import com.example.heroapp.ui.theme.neonGreen
import com.example.heroapp.ui.theme.softPurple
import com.example.heroapp.viewmodel.SearchViewModel
import com.example.heroapp.HeroApplication   // 👈 το Application σου με το appComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchViewModel
) {
    val query = viewModel.query
    val results = viewModel.results
    val isLoading = viewModel.isLoading
    var active by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .background(darkPurple)
            .fillMaxSize()
            .padding(top = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SearchBar(
                modifier = Modifier
                    .fillMaxWidth(0.92f),
                query = query,
                onQueryChange = { text ->
                    viewModel.onQueryChange(text)
                },
                onSearch = {
                    active = false
                },
                active = active,
                onActiveChange = { active = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = neonGreen
                    )
                },
                placeholder = {
                    Text(
                        text = "Search in the multiverse...",
                        color = Color.White.copy(alpha = 0.7f)
                    )
                },
                colors = SearchBarDefaults.colors(
                    containerColor = softPurple.copy(alpha = 0.25f),
                    inputFieldColors = TextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = neonGreen,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )
            ) { }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Suggestions:",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 4.dp, bottom = 8.dp)
            )

            QuickFilterRow { filter ->
                navController.navigate("filter_results/${filter.name}")
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (query.isNotBlank()) {
                Text(
                    text = "Search Results:",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 6.dp, bottom = 10.dp)
                )
            }

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = neonGreen)
                    }
                }

                results.isEmpty() && query.isNotBlank() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Sorry no match found.",
                                fontSize = 22.sp,
                                color = neonGreen
                            )
                            Text(
                                text = "You got pickle ricked!",
                                fontSize = 35.sp,
                                color = neonGreen
                            )

                            Spacer(modifier = Modifier.height(26.dp))

                            Image(
                                painter = painterResource(id = R.drawable.noresultpicklerick),
                                contentDescription = "Pickle Rick",
                                modifier = Modifier
                                    .size(280.dp)
                            )
                        }
                    }
                }

                results.isNotEmpty() -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(results.chunked(2)) { rowCharacters ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                rowCharacters.forEach { character ->
                                    CharacterCard(
                                        character = character,
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(vertical = 4.dp),
                                        onClick = {
                                            navController.navigate("detail/${character.id}")
                                        }
                                    )
                                }
                                if (rowCharacters.size == 1) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
