package com.edworld.doormarketapp.data.model

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val image: String,
    val image2: String = "",
    val description: String = "",
    val categoryId: Int
) {
    val imageUrl: String
        get() = "file:///android_asset/$image"
    
    val image2Url: String
        get() = "file:///android_asset/$image2"
    
    val images: List<String>
        get() = if (image2.isNotEmpty()) {
            listOf(imageUrl, image2Url)
        } else {
            listOf(imageUrl, imageUrl)
        }
}

