package com.example.android_project_simple_ecommerce.data.repository

import com.example.android_project_simple_ecommerce.data.model.Product
import com.example.android_project_simple_ecommerce.data.remote.ProductApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val api: ProductApi
) {
    suspend fun getAllProducts(): List<Product> = api.getProducts()
}
