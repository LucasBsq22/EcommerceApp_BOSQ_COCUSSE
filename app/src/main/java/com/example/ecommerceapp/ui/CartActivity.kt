package com.example.ecommerceapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceapp.cart.CartManager
import com.example.ecommerceapp.databinding.ActivityCartBinding
import com.example.ecommerceapp.ui.adapter.ProductAdapter

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ProductAdapter()

        binding.recyclerViewCart.apply {
            layoutManager = LinearLayoutManager(this@CartActivity)
            adapter = this@CartActivity.adapter
        }

        val cartItems = CartManager.getCartItems()
        adapter.submitList(cartItems)

        binding.tvTotalPrice.text = "Total : ${CartManager.getTotalPrice()} €"
    }
}