package com.edworld.doormarketapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)

fun getBottomNavItems(): List<BottomNavItem> {
    return listOf(
        BottomNavItem(
            route = Screen.Home.route,
            title = "Home",
            icon = Icons.Default.Home
        ),
        BottomNavItem(
            route = Screen.Categories.route,
            title = "Categorías",
            icon = Icons.Default.GridView
        ),
        BottomNavItem(
            route = Screen.Promotions.route,
            title = "Promociones",
            icon = Icons.Default.LocalOffer
        ),
        BottomNavItem(
            route = Screen.Orders.route,
            title = "Pedidos",
            icon = Icons.Default.ShoppingCart
        ),
        BottomNavItem(
            route = Screen.Profile.route,
            title = "Perfil",
            icon = Icons.Default.Person
        )
    )
}