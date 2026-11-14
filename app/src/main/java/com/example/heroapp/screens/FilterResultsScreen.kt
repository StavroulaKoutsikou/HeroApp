package com.example.heroapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.heroapp.components.CharacterCard
import com.example.heroapp.model.QuickFilter
import com.example.heroapp.ui.theme.neonGreen
import com.example.heroapp.viewmodel.FilterResultsViewModel

@Composable
fun FilterResultsScreen(
    navController: NavHostController,
    filter: String,
    viewModel: FilterResultsViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val results = uiState.results
    val isLoading = uiState.isLoading
    val error = uiState.error

    val selectedFilter = try {
        QuickFilter.valueOf(filter)
    } catch (e: Exception) {
        null
    }

    LaunchedEffect(selectedFilter) {
        selectedFilter?.let {
            viewModel.loadFilter(it)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF20113A))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 14.dp, end = 12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(32.dp)
                    .clickable { navController.popBackStack() }
            )

            Text(
                text = when (selectedFilter) {
                    QuickFilter.RICKS_AND_MORTYS -> "RICKS & MORTYS"
                    QuickFilter.HUMANS_FROM_EARTH -> "HUMANS FROM EARTH"
                    QuickFilter.ALIENS_AND_CREATURES -> "ALIENS & CREATURES"
                    else -> "RESULTS"
                },
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = neonGreen)
            }
        } else if (error != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = error ?: "Error",
                    color = Color.Red,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(results) { character ->
                    CharacterCard(
                        character = character,
                        modifier = Modifier
                            .fillMaxWidth(0.9f),
                        onClick = {
                            navController.navigate("detail/${character.id}")
                        }
                    )
                }
            }
        }
    }
}
