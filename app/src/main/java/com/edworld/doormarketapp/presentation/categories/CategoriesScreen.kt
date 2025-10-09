package com.edworld.doormarketapp.presentation.categories

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.edworld.doormarketapp.R
import com.edworld.doormarketapp.presentation.components.TopBar

@Composable
fun CategoriesScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            title = stringResource(R.string.screen_categories),
            showSearch = true,
            showFilters = true
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Categorías Content")
        }
    }
}