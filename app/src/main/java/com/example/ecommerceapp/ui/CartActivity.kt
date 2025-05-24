package com.example.ecommerceapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceapp.R
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

        val headerTitle = findViewById<TextView>(R.id.header_title)

        headerTitle.text = "Panier" // ou "Accueil", etc.

        binding.btnReturnHome.setOnClickListener {
            startActivity(Intent(this, ListProductsActivity::class.java))
        }

    }

    override fun onResume() {
        super.onResume()
        updateCartBadge()
    }

    private fun updateCartBadge() {
        val cartSize = CartManager.getCartSize()
        val tvBadge = findViewById<TextView>(R.id.cart_badge)

        if (cartSize > 0) {
            tvBadge.text = cartSize.toString()
            tvBadge.visibility = View.VISIBLE
        } else {
            tvBadge.visibility = View.GONE
        }
    }

}