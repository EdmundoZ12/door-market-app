package com.edworld.doormarketapp.presentation.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.edworld.doormarketapp.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BannerCarousel(
    banners: List<Int>,
    modifier: Modifier = Modifier,
    autoScrollDuration: Long = 3000L
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { banners.size }
    )

    // Auto-scroll
    LaunchedEffect(Unit) {
        while (true) {
            delay(autoScrollDuration)
            val nextPage = (pagerState.currentPage + 1) % banners.size
            pagerState.animateScrollToPage(
                page = nextPage,
                animationSpec = tween(
                    durationMillis = 800,
                    easing = androidx.compose.animation.core.FastOutSlowInEasing
                )
            )
        }
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // Carrusel
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .padding(top = 8.dp)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                pageSpacing = 12.dp
            ) { page ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF5F5F5)
                    )
                ) {
                    Image(
                        painter = painterResource(id = banners[page]),
                        contentDescription = "Banner ${page + 1}",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        // Indicadores debajo del carrusel
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(banners.size) { index ->
                Box(
                    modifier = Modifier
                        .size(
                            width = if (pagerState.currentPage == index) 24.dp else 8.dp,
                            height = 4.dp
                        )
                        .clip(CircleShape)
                        .background(
                            if (pagerState.currentPage == index)
                                colorResource(id = R.color.primary)
                            else
                                Color.Gray.copy(alpha = 0.3f)
                        )
                )

                if (index < banners.size - 1) {
                    Spacer(modifier = Modifier.width(6.dp))
                }
            }
        }
    }
}