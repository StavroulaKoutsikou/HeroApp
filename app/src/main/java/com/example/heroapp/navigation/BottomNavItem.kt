package com.example.heroapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    object Dashboard : BottomNavItem("dashboard", "Home", Icons.Outlined.Home)
    object Search : BottomNavItem("search",  "Search",  Icons.Outlined.Search)
    object Favorites : BottomNavItem("favorites", "Favorites", Icons.Outlined.Favorite)

    object Settings  : BottomNavItem("settings",  "Settings",  Icons.Outlined.Settings)

    companion object { val items = listOf(Dashboard, Search, Favorites, Settings) }
}
