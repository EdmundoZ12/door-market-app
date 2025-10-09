package com.edworld.doormarketapp.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.edworld.doormarketapp.R
import com.edworld.doormarketapp.presentation.components.BannerCarousel
import com.edworld.doormarketapp.presentation.components.CategoriesSection
import com.edworld.doormarketapp.presentation.components.LastOrdersSection
import com.edworld.doormarketapp.presentation.components.TopBar

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            title = stringResource(R.string.screen_home),
            showSearch = true,
            showFilters = false
        )

        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (state.error != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Error: ${state.error}")
                    Spacer(modifier = Modifier.height(16.dp))
                    androidx.compose.material3.Button(
                        onClick = { viewModel.retry() }
                    ) {
                        Text("Reintentar")
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                // Banner Carousel
                item {
                    BannerCarousel(
                        banners = listOf(
                            R.drawable.banner_1,
                            R.drawable.banner_2,
                            R.drawable.banner_3,
                            R.drawable.banner_4,
                        )
                    )
                }

                // Sección de Categorías
                item {
                    CategoriesSection(
                        categories = state.categories,
                        onCategoryClick = { category ->
                            viewModel.onCategoryClick(category)
                        }
                    )
                }

                // Sección de Últimos Pedidos
                if (state.lastOrders.isNotEmpty()) {
                    item {
                        LastOrdersSection(
                            orders = state.lastOrders,
                            onViewProducts = { order ->
                                viewModel.onViewProducts(order)
                            },
                            onRepeatOrder = { order ->
                                viewModel.onRepeatOrder(order)
                            }
                        )
                    }
                }

                // Espacio al final
                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }
    }
}