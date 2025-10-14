package com.edworld.doormarketapp.presentation.promotions
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.draw.clip
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edworld.doormarketapp.R
import com.edworld.doormarketapp.presentation.components.TopBar
import com.edworld.doormarketapp.data.model.Product
import androidx.compose.foundation.lazy.grid.items
import com.edworld.doormarketapp.presentation.promotions.AllProductsSection
import com.edworld.doormarketapp.service.ProductService


@Composable
fun PromotionsScreen() {
    val context = LocalContext.current
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var reloadKey by remember { mutableStateOf(false) }

    val productService = remember { ProductService(context) }

    LaunchedEffect(reloadKey) {
        isLoading = true
        error = null
        try {
            val allProducts = productService.getProducts().getOrThrow()
            // Tomamos una muestra aleatoria de productos (p. ej., 10 o menos si no hay tantos)
            // y les aplicamos un descuento aleatorio.
            products = allProducts.shuffled().take(10).map { product ->
                val discount = kotlin.random.Random.nextDouble(0.05, 0.30)
                product.copy(price = product.price * (1 - discount))
            }
        } catch (e: Exception) {
            error = e.message
        } finally {
            isLoading = false
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            title = stringResource(R.string.screen_promotions),
            showSearch = true,
            showFilters = true
        )

        when {
            isLoading -> {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            error != null -> {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Error: $error")
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(onClick = { reloadKey = !reloadKey }) {
                            Text(text = "Reintentar")
                        }
                    }
                }
            }
            else -> {
                AllProductsSection(
                    products = products,
                    onAddToCart = { /*TODO*/ },
                    onProductClick = { /*TODO*/ }
                )
            }
        }
    }
}
@Composable
fun DiscountBadge(discount: Int) {
    Box(
        modifier = Modifier
         .width(70.dp)
         .height(24.dp)
         .clip(RoundedCornerShape(topStart = 8.dp, bottomEnd = 8.dp))
         .graphicsLayer {
          translationX = -8f
          translationY = 8f
         }
            .background(Color(0xFFE94335)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "-${discount}%",
            fontSize = 12.sp,
            color = Color.White
        )
    }
}

@Composable
fun Background(color: Color) {
    Box(modifier = Modifier.fillMaxSize().background(color = color))
}