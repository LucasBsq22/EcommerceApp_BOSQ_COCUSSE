package com.example.ecommerceapp.network

import com.example.ecommerceapp.model.Product
import retrofit2.http.GET
import retrofit2.http.Query

interface FakeStoreApi {

    // Récupère tous les produits
    @GET("products")
    suspend fun getAllProducts(): List<Product>

    // Récupère un produit par ID
    @GET("products/{id}")
    suspend fun getProductById(@retrofit2.http.Path("id") id: Int): Product

}
