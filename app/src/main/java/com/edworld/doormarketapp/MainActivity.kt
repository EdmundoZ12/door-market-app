package com.edworld.doormarketapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edworld.doormarketapp.data.model.Product
import com.edworld.doormarketapp.presentation.categories.CategoriesScreen
import com.edworld.doormarketapp.presentation.home.HomeScreen
import com.edworld.doormarketapp.presentation.navigation.getBottomNavItems
import com.edworld.doormarketapp.presentation.orders.OrdersScreen
import com.edworld.doormarketapp.presentation.productdetail.ProductDetailScreen
import com.edworld.doormarketapp.presentation.profile.ProfileScreen
import com.edworld.doormarketapp.presentation.promotions.PromotionsScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    color = Color.White
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val bottomNavItems = getBottomNavItems()
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { bottomNavItems.size }
    )
    val scope = rememberCoroutineScope()
    
    // Estado para navegación a detalle de producto
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    var selectedCategoryName by remember { mutableStateOf("Categoría") }

    // Si hay un producto seleccionado, mostrar la pantalla de detalle
    if (selectedProduct != null) {
        ProductDetailScreen(
            product = selectedProduct!!,
            categoryName = selectedCategoryName,
            onBack = { selectedProduct = null },
            onAddToCart = { quantity ->
                // TODO: Implementar agregar al carrito
                selectedProduct = null
            },
            onNavigateToPage = { pageIndex ->
                scope.launch {
                    pagerState.animateScrollToPage(pageIndex)
                }
            }
        )
        return
    }

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                contentColor = colorResource(id = R.color.primary),
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color(0xFFE0E0E0)
                    )
                    .height(70.dp)
            ) {
                bottomNavItems.forEachIndexed { index, item ->
                    val isSelected = pagerState.currentPage == index

                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                modifier = Modifier.size(22.dp)
                            )
                        },
                        label = {
                            Text(
                                text = item.title,
                                fontSize = 11.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        selected = isSelected,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = colorResource(id = R.color.primary),
                            selectedTextColor = colorResource(id = R.color.primary),
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray,
                            indicatorColor = colorResource(id = R.color.primary).copy(alpha = 0.1f)
                        ),
                        alwaysShowLabel = true
                    )
                }
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            userScrollEnabled = true
        ) { page ->
            when (page) {
                0 -> HomeScreen()
                1 -> CategoriesScreen(
                    onProductClick = { product, categoryName ->
                        selectedProduct = product
                        selectedCategoryName = categoryName
                    }
                )
                2 -> PromotionsScreen()
                3 -> OrdersScreen()
                4 -> ProfileScreen()
            }
        }
    }
}