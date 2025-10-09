package com.edworld.doormarketapp.data.repository

import android.content.Context

class JsonReader(private val context: Context) {
    fun readJsonFromAssets(fileName: String): String {
        return try {
            context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            throw Exception("Error al leer archivo JSON: $fileName", e)
        }
    }
}