package com.edworld.doormarketapp.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Categories : Screen("categories")
    object Promotions : Screen("promotions")
    object Orders : Screen("orders")
    object Profile : Screen("profile")
}