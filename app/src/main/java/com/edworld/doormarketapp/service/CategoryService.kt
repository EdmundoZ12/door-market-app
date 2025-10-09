package com.edworld.doormarketapp.service

import android.content.Context
import com.edworld.doormarketapp.data.model.Category
import com.edworld.doormarketapp.data.model.CategoryResponse
import com.edworld.doormarketapp.data.repository.JsonReader
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryService(private val context: Context) {

    private val jsonReader = JsonReader(context)
    private val gson = Gson()
    private var cachedCategories: List<Category>? = null

    suspend fun getCategories(): Result<List<Category>> = withContext(Dispatchers.IO) {
        try {
            // Si ya están en cache, retornarlas
            if (cachedCategories != null) {
                return@withContext Result.success(cachedCategories!!)
            }

            // Leer y parsear el JSON
            val jsonString = jsonReader.readJsonFromAssets("data/categories.json")
            val response = gson.fromJson(jsonString, CategoryResponse::class.java)

            // Cachear las categorías
            cachedCategories = response.categories

            Result.success(response.categories)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCategoryById(id: Int): Result<Category> = withContext(Dispatchers.IO) {
        try {
            val categories = getCategories().getOrThrow()
            val category = categories.find { it.id == id }
                ?: throw NoSuchElementException("Categoría con ID $id no encontrada")
            Result.success(category)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}