package com.example.heroapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.heroapp.model.QuickFilter

@Composable
fun QuickFilterRow(
    onFilterClick: (QuickFilter) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        QuickFilterCard(
            title = "Ricks & Mortys",
            modifier = Modifier.weight(1f)
        ) {
            onFilterClick(QuickFilter.RICKS_AND_MORTYS)
        }

        QuickFilterCard(
            title = "Humans from Earth",
            modifier = Modifier.weight(1f)
        ) {
            onFilterClick(QuickFilter.HUMANS_FROM_EARTH)
        }

        QuickFilterCard(
            title = "Aliens & Creatures",
            modifier = Modifier.weight(1f)
        ) {
            onFilterClick(QuickFilter.ALIENS_AND_CREATURES)
        }
    }
}
