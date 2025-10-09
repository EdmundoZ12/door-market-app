package com.edworld.doormarketapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.edworld.doormarketapp.presentation.categories.CategoriesScreen
import com.edworld.doormarketapp.presentation.home.HomeScreen
import com.edworld.doormarketapp.presentation.navigation.Screen
import com.edworld.doormarketapp.presentation.navigation.getBottomNavItems
import com.edworld.doormarketapp.presentation.orders.OrdersScreen
import com.edworld.doormarketapp.presentation.profile.ProfileScreen
import com.edworld.doormarketapp.presentation.promotions.PromotionsScreen

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
    val navController = rememberNavController()
    val bottomNavItems = getBottomNavItems()

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
                    .height(70.dp)  // ← Altura fija para el bottom bar
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                bottomNavItems.forEach { item ->
                    val isSelected = currentDestination?.hierarchy?.any {
                        it.route == item.route
                    } == true

                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                modifier = Modifier.size(22.dp)  // ← Icono más pequeño
                            )
                        },
                        label = {
                            Text(
                                text = item.title,
                                fontSize = 11.sp,  // ← Texto más pequeño
                                maxLines = 1,  // ← Solo 1 línea
                                overflow = TextOverflow.Ellipsis  // ← Puntos suspensivos si es muy largo
                            )
                        },
                        selected = isSelected,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = colorResource(id = R.color.primary),
                            selectedTextColor = colorResource(id = R.color.primary),
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray,
                            indicatorColor = colorResource(id = R.color.primary).copy(alpha = 0.1f)
                        ),
                        alwaysShowLabel = true  // ← Siempre mostrar el label
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen()
            }
            composable(Screen.Categories.route) {
                CategoriesScreen()
            }
            composable(Screen.Promotions.route) {
                PromotionsScreen()
            }
            composable(Screen.Orders.route) {
                OrdersScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
        }
    }
}