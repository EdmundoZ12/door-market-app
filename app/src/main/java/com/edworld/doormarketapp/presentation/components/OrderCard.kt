package com.edworld.doormarketapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.edworld.doormarketapp.R
import com.edworld.doormarketapp.data.model.Order

@Composable
fun OrderCard(
    order: Order,
    onViewProducts: () -> Unit,
    onRepeat: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { order.items.size }
    )

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val cardWidth =
        (screenWidth - 48.dp) / 2 // 48dp = padding horizontal (16dp * 2) + spacing (12dp)

    Card(
        modifier = modifier
            .width(cardWidth),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFE0E0E0))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Mini carrusel de productos
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF5F5F5))
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    AsyncImage(
                        model = order.productImages[page],
                        contentDescription = order.items[page].productName,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            // Indicadores debajo del carrusel
            if (order.items.size > 1) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(order.items.size) { index ->
                        Box(
                            modifier = Modifier
                                .size(
                                    width = if (pagerState.currentPage == index) 16.dp else 6.dp,
                                    height = 3.dp
                                )
                                .clip(CircleShape)
                                .background(
                                    if (pagerState.currentPage == index)
                                        colorResource(id = R.color.primary)
                                    else
                                        Color.Gray.copy(alpha = 0.3f)
                                )
                        )

                        if (index < order.items.size - 1) {
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Ver Productos
            TextButton(
                onClick = onViewProducts,
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text(
                    text = "Ver Productos",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.primary)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Total
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "Total",
                    fontSize = 12.sp,
                    color = Color.Black
                )

                Text(
                    text = order.formattedTotal,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.primary)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón Repetir
            Button(
                onClick = onRepeat,
                modifier = Modifier
                    .width(110.dp)
                    .align(Alignment.CenterHorizontally)
                    .height(36.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.secondary)
                ),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
            ) {
                Text(
                    text = "Repetir",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Repetir",
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}