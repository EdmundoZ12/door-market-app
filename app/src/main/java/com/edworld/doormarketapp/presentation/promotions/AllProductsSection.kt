package com.edworld.doormarketapp.presentation.promotions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.edworld.doormarketapp.presentation.components.SmallProductCard
import androidx.compose.ui.unit.dp
import com.edworld.doormarketapp.data.model.Product
import kotlin.random.Random

@Composable
fun AllProductsSection(
    products: List<Product>,
    onAddToCart: (Product) -> Unit,
    onProductClick: (Product) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(products) { product ->
            Box {
                SmallProductCard(
                    product = product,
                    onAddToCart = { onAddToCart(product) },
                    onClick = { onProductClick(product) }
                )

                // Insignia de descuento
                // El precio ya viene con descuento, así que calculamos un % visual aleatorio
                val randomOriginalPrice = product.price / (1 - Random.nextDouble(0.05, 0.30))
                val discountPercentage = (1 - (product.price / randomOriginalPrice)) * 100
                DiscountBadge(discount = discountPercentage.toInt())
            }
        }
    }
}