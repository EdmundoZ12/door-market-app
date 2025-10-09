package com.edworld.doormarketapp.presentation.promotions

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.edworld.doormarketapp.R
import com.edworld.doormarketapp.presentation.components.TopBar

@Composable
fun PromotionsScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            title = stringResource(R.string.screen_promotions),
            showSearch = true,
            showFilters = false
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Promociones Content")
        }
    }
}