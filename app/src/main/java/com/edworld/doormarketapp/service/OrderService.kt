package com.edworld.doormarketapp.service

import android.content.Context
import com.edworld.doormarketapp.data.model.Order
import com.edworld.doormarketapp.data.model.OrderResponse
import com.edworld.doormarketapp.data.repository.JsonReader
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OrderService(private val context: Context) {

    private val jsonReader = JsonReader(context)
    private val gson = Gson()
    private var cachedOrders: List<Order>? = null

    suspend fun getOrders(): Result<List<Order>> = withContext(Dispatchers.IO) {
        try {
            if (cachedOrders != null) {
                return@withContext Result.success(cachedOrders!!)
            }

            val jsonString = jsonReader.readJsonFromAssets("data/orders.json")
            val response = gson.fromJson(jsonString, OrderResponse::class.java)

            cachedOrders = response.orders

            Result.success(response.orders)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getLastOrders(limit: Int = 2): Result<List<Order>> = withContext(Dispatchers.IO) {
        try {
            val orders = getOrders().getOrThrow()
            Result.success(orders.take(limit))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getOrderById(id: Int): Result<Order> = withContext(Dispatchers.IO) {
        try {
            val orders = getOrders().getOrThrow()
            val order = orders.find { it.id == id }
                ?: throw NoSuchElementException("Pedido con ID $id no encontrado")
            Result.success(order)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}