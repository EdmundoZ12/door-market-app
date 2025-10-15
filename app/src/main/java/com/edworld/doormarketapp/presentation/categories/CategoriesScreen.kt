package com.edworld.doormarketapp.presentation.categories

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.edworld.doormarketapp.R
import com.edworld.doormarketapp.data.model.Category
import com.edworld.doormarketapp.data.model.Product
import com.edworld.doormarketapp.presentation.components.BannerCarousel
import com.edworld.doormarketapp.presentation.components.CategoryProductsSection
import com.edworld.doormarketapp.presentation.components.TopBar
import com.edworld.doormarketapp.service.CategoryService
import com.edworld.doormarketapp.service.ProductService

@Composable
fun CategoriesScreen(
    onProductClick: (Product, String) -> Unit = { _, _ -> }
) {
    val context = LocalContext.current

    var categories by remember { mutableStateOf<List<Category>>(emptyList()) }
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var reloadKey by remember { mutableStateOf(false) }

    val categoryService = remember { CategoryService(context) }
    val productService = remember { ProductService(context) }

    LaunchedEffect(reloadKey) {
        isLoading = true
        error = null
        try {
            val cats = categoryService.getCategories().getOrThrow()
            val prods = productService.getProducts().getOrThrow()
            categories = cats
            products = prods
        } catch (e: Exception) {
            error = e.message
        } finally {
            isLoading = false
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = stringResource(R.string.screen_categories),
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
                        Text(text = "Error: ${error}")
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(onClick = { reloadKey = !reloadKey }) {
                            Text(text = "Reintentar")
                        }
                    }
                }
            }
            else -> {
                LazyColumn(modifier = Modifier.weight(1f).fillMaxWidth()) {
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

                    // Secciones por categoría con carrusel horizontal de productos
                    items(categories.size) { index ->
                        val category = categories[index]
                        val productsForCategory = products.filter { it.categoryId == category.id }
                        if (productsForCategory.isNotEmpty()) {
                            val catName = category.name
                            CategoryProductsSection(
                                categoryName = catName,
                                categoryImagePath = category.image,
                                products = productsForCategory,
                                onAddToCart = { _: Product -> /* TODO: Integrar carrito */ },
                                onProductClick = { product: Product -> onProductClick(product, catName) }
                            )
                        }
                    }

                    item { Spacer(modifier = Modifier.height(80.dp)) }
                }
            }
        }
    }
}
