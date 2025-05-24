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
        return String.format("%.2f", cartItems.sumOf { it.price }).toDouble()
    }


    fun getCartSize(): Int {
        return cartItems.size
    }

    fun clearCart() {
        cartItems.clear()
    }
}
