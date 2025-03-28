package com.example.android_project_simple_ecommerce.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_project_simple_ecommerce.data.model.Product
import com.example.android_project_simple_ecommerce.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    var productList by mutableStateOf<List<Product>>(emptyList())
        private set

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            try {
                productList = repository.getAllProducts()
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error: ${e.message}")
            }
        }
    }
}
