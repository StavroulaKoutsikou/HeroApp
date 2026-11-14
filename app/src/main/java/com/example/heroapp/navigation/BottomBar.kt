package com.example.heroapp.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.heroapp.ui.theme.backgroundPurple
import com.example.heroapp.ui.theme.neonGreen

@Composable
fun BottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = backgroundPurple,
        contentColor = Color.Black
    ) {
        BottomNavItem.items.forEach { item ->
            val selected = currentDestination
                ?.hierarchy
                ?.any { it.route == item.route } == true

            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) {
                        navController.navigate(item.route) {
                            launchSingleTop = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = backgroundPurple,
                    selectedTextColor = Color.White,
                    indicatorColor = neonGreen,
                    unselectedIconColor = neonGreen,
                    unselectedTextColor = Color.White,
                ),
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label)
                }
            )
        }
    }
}
