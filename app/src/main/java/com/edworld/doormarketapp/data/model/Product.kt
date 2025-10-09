package com.edworld.doormarketapp.data.model

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val image: String,
    val categoryId: Int
) {
    val imageUrl: String
        get() = "file:///android_asset/$image"
}

