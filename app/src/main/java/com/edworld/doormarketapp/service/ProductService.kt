package com.edworld.doormarketapp.service

import android.content.Context
import com.edworld.doormarketapp.data.model.Product
import com.edworld.doormarketapp.data.model.ProductResponse
import com.edworld.doormarketapp.data.repository.JsonReader
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductService(private val context: Context) {

    private val jsonReader = JsonReader(context)
    private val gson = Gson()
    private var cachedProducts: List<Product>? = null

    suspend fun getProducts(): Result<List<Product>> = withContext(Dispatchers.IO) {
        try {
            if (cachedProducts != null) {
                return@withContext Result.success(cachedProducts!!)
            }

            val jsonString = jsonReader.readJsonFromAssets("data/products.json")
            val response = gson.fromJson(jsonString, ProductResponse::class.java)
            cachedProducts = response.products
            Result.success(response.products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getProductsByCategoryId(categoryId: Int): Result<List<Product>> = withContext(Dispatchers.IO) {
        try {
            val products = getProducts().getOrThrow()
            Result.success(products.filter { it.categoryId == categoryId })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

