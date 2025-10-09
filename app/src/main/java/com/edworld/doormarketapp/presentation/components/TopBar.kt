package com.edworld.doormarketapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edworld.doormarketapp.R

@Composable
fun TopBar(
    title: String = "DoorMarket",
    showSearch: Boolean = true,
    showFilters: Boolean = false,
    onSearchClick: () -> Unit = {},
    onFilterClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onCartClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
                clip = true
            ),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            // Fila 1: Logo + Título
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_doormarket),
                    contentDescription = "Logo DoorMarket",
                    modifier = Modifier
                        .size(50.dp)
                        .padding(end = 10.dp),
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.primary)
                    )
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            // Fila 2: SearchBar + Todos + Iconos
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Barra de búsqueda (más ancha)
                if (showSearch) {
                    Box(
                        modifier = Modifier
                            .weight(1.2f)  // Aumentado para que sea más ancha
                            .height(48.dp)
                            .border(
                                width = 1.5.dp,
                                color = Color(0xFFCCCCCC),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { onSearchClick() }
                            .padding(horizontal = 14.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Buscar",
                                tint = colorResource(id = R.color.primary),
                                modifier = Modifier.size(20.dp)
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = "Search",
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    color = Color.Gray
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))
                }

                // Iconos de la derecha (más pequeños)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Texto "Todos" + Filtros
                    if (showFilters) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { onFilterClick() }
                        ) {
                            Text(
                                text = "Todos",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = colorResource(id = R.color.primary)
                                )
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.FilterList,
                                contentDescription = "Filtros",
                                tint = colorResource(id = R.color.primary),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    // Notificaciones
                    IconButton(
                        onClick = onNotificationClick,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notificaciones",
                            tint = colorResource(id = R.color.primary),
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    // Carrito
                    IconButton(
                        onClick = onCartClick,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Carrito",
                            tint = colorResource(id = R.color.primary),
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
            }
        }
    }
}