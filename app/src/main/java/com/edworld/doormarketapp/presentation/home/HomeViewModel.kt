package com.edworld.doormarketapp.presentation.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.edworld.doormarketapp.data.model.Category
import com.edworld.doormarketapp.data.model.Order
import com.edworld.doormarketapp.service.CategoryService
import com.edworld.doormarketapp.service.OrderService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeState(
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val lastOrders: List<Order> = emptyList(),
    val error: String? = null
)

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val categoryService = CategoryService(application)
    private val orderService = OrderService(application)

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            // Cargar categorías
            val categoriesResult = categoryService.getCategories()

            // Cargar últimos 2 pedidos
            val ordersResult = orderService.getLastOrders(limit = 2)

            categoriesResult.fold(
                onSuccess = { categories ->
                    _state.update { it.copy(categories = categories) }
                },
                onFailure = { error ->
                    _state.update { it.copy(error = error.message) }
                }
            )

            ordersResult.fold(
                onSuccess = { orders ->
                    _state.update { it.copy(lastOrders = orders) }
                },
                onFailure = { error ->
                    _state.update { it.copy(error = error.message) }
                }
            )

            _state.update { it.copy(isLoading = false) }
        }
    }

    fun onCategoryClick(category: Category) {
        // TODO: Navegar a la pantalla de categoría
        println("Categoría seleccionada: ${category.name}")
    }

    fun onViewProducts(order: Order) {
        // TODO: Navegar a detalle de pedido
        println("Ver productos del pedido: ${order.orderNumber}")
    }

    fun onRepeatOrder(order: Order) {
        // TODO: Agregar productos al carrito
        println("Repetir pedido: ${order.orderNumber}")
    }

    fun retry() {
        loadHomeData()
    }
}