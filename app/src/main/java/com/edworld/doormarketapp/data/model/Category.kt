package com.edworld.doormarketapp.data.model

data class Category(
    val id: Int,
    val name: String,
    val icon: String,
    val image: String,
    val productCount: Int
) {
    val imageUrl: String
        get() = "file:///android_asset/$image"
}