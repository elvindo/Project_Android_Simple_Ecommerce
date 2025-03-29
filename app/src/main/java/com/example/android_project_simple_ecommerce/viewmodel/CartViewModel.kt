package com.example.android_project_simple_ecommerce.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_project_simple_ecommerce.data.model.CartItem
import com.example.android_project_simple_ecommerce.data.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepository
) : ViewModel() {

    val cartItems = repository.getCartItems()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun addToCart(item: CartItem) {
        viewModelScope.launch {
            repository.insertItem(item)
        }
    }

    fun removeFromCart(item: CartItem) {
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }
}
