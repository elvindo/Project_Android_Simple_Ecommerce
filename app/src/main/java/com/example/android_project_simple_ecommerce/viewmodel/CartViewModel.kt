package com.example.android_project_simple_ecommerce.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_project_simple_ecommerce.data.model.CartItem
import com.example.android_project_simple_ecommerce.data.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor() : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())

    val cartItems: StateFlow<List<CartItem>> = _cartItems

    fun addToCart(newItem: CartItem) {
        val currentItems = _cartItems.value.toMutableList()
        val existingItemIndex = currentItems.indexOfFirst { it.productId == newItem.productId }

        if (existingItemIndex != -1) {
            val existingItem = currentItems[existingItemIndex]
            currentItems[existingItemIndex] =
                existingItem.copy(quantity = existingItem.quantity + newItem.quantity)
        } else {
            currentItems.add(newItem)
        }

        _cartItems.value = currentItems
    }

    fun removeFromCart(item: CartItem) {
        val updatedList = _cartItems.value.toMutableList().apply { remove(item) }
        _cartItems.value = updatedList
    }

    fun increaseQuantity(item: CartItem) {
        val updatedList = _cartItems.value.map {
            if (it.productId == item.productId) it.copy(quantity = it.quantity + 1) else it
        }
        _cartItems.value = updatedList
    }

    fun decreaseQuantity(item: CartItem) {
        val updatedList = _cartItems.value.mapNotNull {
            if (it.productId == item.productId) {
                if (it.quantity > 1) it.copy(quantity = it.quantity - 1) else null
            } else it
        }
        _cartItems.value = updatedList
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }
}
