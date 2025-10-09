package com.edworld.doormarketapp.data.model

data class Order(
    val id: Int,
    val orderNumber: String,
    val date: String,
    val status: String,
    val total: Double,
    val items: List<OrderItem>
) {
    val formattedTotal: String
        get() = "${total.toInt()} BS"

    val itemCount: Int
        get() = items.size

    val productImages: List<String>
        get() = items.map { "file:///android_asset/${it.image}" }
}

data class OrderItem(
    val productId: Int,
    val productName: String,
    val quantity: Int,
    val price: Double,
    val image: String
)

data class OrderResponse(
    val orders: List<Order>
)