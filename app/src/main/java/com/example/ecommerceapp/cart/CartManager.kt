package com.example.ecommerceapp.cart

import com.example.ecommerceapp.model.Product

object CartManager {
    private val cartItems = mutableListOf<Product>()

    fun addToCart(product: Product) {
        cartItems.add(product)
    }

    fun getCartItems(): List<Product> {
        return cartItems
    }

    fun getTotalPrice(): Double {
        return cartItems.sumOf { it.price }
    }

    fun clearCart() {
        cartItems.clear()
    }
}
