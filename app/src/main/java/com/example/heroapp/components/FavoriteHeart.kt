// FavoriteHeart.kt
package com.example.heroapp.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FavoriteHeart(
    isFavorite: Boolean,
    onClick: () -> Unit,
    neonGreen: Color,
    purple: Color
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(44.dp)
    ) {
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
            tint = if (isFavorite) neonGreen else Color.White,
            modifier = Modifier.size(32.dp)
        )
    }
}
