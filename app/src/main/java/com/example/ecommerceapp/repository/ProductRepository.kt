package com.example.ecommerceapp.repository

import com.example.ecommerceapp.model.Product
import com.example.ecommerceapp.network.RetrofitInstance

class ProductRepository {

    suspend fun getAllProducts(): List<Product> {
        return RetrofitInstance.api.getAllProducts()
    }

    suspend fun getProductById(id: Int): Product {
        return RetrofitInstance.api.getProductById(id)
    }
}
