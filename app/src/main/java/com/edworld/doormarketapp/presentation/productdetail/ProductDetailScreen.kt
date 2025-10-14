package com.edworld.doormarketapp.presentation.productdetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.edworld.doormarketapp.R
import com.edworld.doormarketapp.data.model.Product
import com.edworld.doormarketapp.presentation.components.TopBar
import com.edworld.doormarketapp.presentation.navigation.getBottomNavItems

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductDetailScreen(
    product: Product,
    categoryName: String = "Categoría",
    onBack: () -> Unit,
    onAddToCart: (Int) -> Unit = {},
    onNavigateToPage: (Int) -> Unit = {}
) {
    var quantity by remember { mutableIntStateOf(1) }
    var isFavorite by remember { mutableStateOf(false) }
    
    // Usar las imágenes del producto (image e image2)
    val images = product.images
    val pagerState = rememberPagerState(pageCount = { images.size })
    
    val bottomNavItems = getBottomNavItems()

    Scaffold(
        containerColor = Color.White,
        topBar = {
            Column {
                // Top Bar completo con logo, búsqueda, notificación y carrito
                TopBar(
                    title = categoryName,
                    showSearch = true,
                    showFilters = false,
                    onSearchClick = { /* TODO: Búsqueda */ },
                    onNotificationClick = { /* TODO: Notificaciones */ },
                    onCartClick = { /* TODO: Ir al carrito */ }
                )
                
                // Botón de volver debajo del TopBar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = colorResource(id = R.color.primary),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    
                    Text(
                        text = "Volver",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.primary)
                    )
                }
            }
        },
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
                        selected = false,
                        onClick = {
                            onBack()
                            onNavigateToPage(index)
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Carrusel de imágenes con HorizontalPager
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .background(Color.White)
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    AsyncImage(
                        model = images[page],
                        contentDescription = "${product.name} - Imagen ${page + 1}",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }
                
                // Indicadores de página (dots)
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(images.size) { index ->
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(
                                    if (pagerState.currentPage == index)
                                        colorResource(id = R.color.primary)
                                    else
                                        Color(0xFFD0D0D0)
                                )
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Información del producto
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                // Nombre del producto con corazón alineado a la derecha
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    
                    // Corazón de favorito alineado con el nombre
                    IconButton(
                        onClick = { isFavorite = !isFavorite },
                        modifier = Modifier.size(48.dp)
                    ) {
                        AsyncImage(
                            model = if (isFavorite) 
                                "file:///android_asset/images/icons/filledheart.png"
                            else 
                                "file:///android_asset/images/icons/heart.png",
                            contentDescription = "Favorito",
                            modifier = Modifier.size(32.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Línea horizontal: Precio + Controles de cantidad + Carrito
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Precio en caja roja con letras blancas - Misma altura que los botones
                    Box(
                        modifier = Modifier
                            .height(48.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(colorResource(id = R.color.primary))
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Bs. ${String.format("%.2f", product.price)}",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = Color.White
                        )
                    }
                    
                    // Lado derecho: Controles de cantidad sin bordes
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        // Botón disminuir (Negro, sin borde)
                        Box(
                            modifier = Modifier
                                .height(48.dp)
                                .width(48.dp)
                                .background(Color.Black)
                                .clickable { if (quantity > 1) quantity-- },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "-",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                        
                        // Cantidad con fondo negro y letra blanca
                        Box(
                            modifier = Modifier
                                .height(48.dp)
                                .width(48.dp)
                                .background(Color.Black),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = quantity.toString(),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                color = Color.White
                            )
                        }
                        
                        // Botón aumentar (Negro, sin borde)
                        Box(
                            modifier = Modifier
                                .height(48.dp)
                                .width(48.dp)
                                .background(Color.Black)
                                .clickable { quantity++ },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "+",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        // Botón de carrito (solo ícono)
                        Box(
                            modifier = Modifier
                                .height(48.dp)
                                .width(48.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(colorResource(id = R.color.primary))
                                .clickable { onAddToCart(quantity) },
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = "file:///android_asset/images/icons/cart2.png",
                                contentDescription = "Agregar al carrito",
                                modifier = Modifier.size(28.dp),
                                contentScale = ContentScale.Fit,
                                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.White)
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Descripción del producto
                Text(
                    text = "Descripción",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = product.description.ifEmpty { 
                        "Producto de calidad disponible en nuestra tienda. Consulta más detalles en el establecimiento."
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 14.sp,
                    color = Color(0xFF808080),
                    lineHeight = 22.sp
                )
                
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}
