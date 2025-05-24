package com.example.ecommerceapp.cart

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.ecommerceapp.model.Product

object CartManager {
    private val cartItems = mutableMapOf<Product, Int>()

    @RequiresApi(Build.VERSION_CODES.N)
    fun addToCart(product: Product) {
        cartItems[product] = cartItems.getOrDefault(product, 0) + 1
    }

    fun removeFromCart(product: Product) {
        val count = cartItems[product] ?: return
        if (count > 1) {
            cartItems[product] = count - 1
        } else {
            cartItems.remove(product)
        }
    }

    fun getCartItems(): Map<Product, Int> {
        return cartItems
    }

    fun getTotalPrice(): Double {
        return cartItems.entries.sumOf { (product, quantity) ->
            product.price * quantity
        }
    }



    fun getCartSize(): Int {
        return cartItems.values.sum()
    }

    fun removeProductCompletely(product: Product) {
        cartItems.keys.find { it.id == product.id }?.let {
            cartItems.remove(it)
        }
    }


    fun clearCart() {
        cartItems.clear()
    }
}
