package com.example.android_project_simple_ecommerce.data.repository

import com.example.android_project_simple_ecommerce.data.local.CartDao
import com.example.android_project_simple_ecommerce.data.model.CartItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val dao: CartDao
) {
    fun getCartItems(): Flow<List<CartItem>> = dao.getAllItems()
    suspend fun insertItem(item: CartItem) = dao.insertItem(item)
    suspend fun deleteItem(item: CartItem) = dao.deleteItem(item)
    suspend fun clearCart() = dao.clearCart()
}
