package com.example.android_project_simple_ecommerce.data.remote

import com.example.android_project_simple_ecommerce.data.model.Product
import retrofit2.http.GET

interface ProductApi {
    @GET("products")
    suspend fun getProducts(): List<Product>
}
