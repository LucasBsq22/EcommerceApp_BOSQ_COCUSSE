package com.example.ecommerceapp.ui

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceapp.R
import com.example.ecommerceapp.cart.CartManager
import com.example.ecommerceapp.databinding.ActivityCartBinding
import com.google.android.material.snackbar.Snackbar

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartAdapter


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CartAdapter(CartManager.getCartItems().toList().toMutableList()) {
            updateTotalPrice()
        }

        binding.recyclerViewCart.apply {
            layoutManager = LinearLayoutManager(this@CartActivity)
            adapter = this@CartActivity.adapter
        }


        updateTotalPrice()

        val headerTitle = findViewById<TextView>(R.id.header_title)

        headerTitle.text = "Panier"

        binding.btnReturnHome.setOnClickListener {
            startActivity(Intent(this, ListProductsActivity::class.java))
        }

        binding.btnClearCart.setOnClickListener {
            val oldCart = CartManager.getCartItems().toList() // sauvegarde si besoin
            CartManager.clearCart()
            adapter.updateData()
            updateTotalPrice()
            updateCartBadge()


            val snackbar = if (oldCart.isNullOrEmpty()) {
                Snackbar.make(binding.root, "LE PANIER EST DÉJÀ VIDE", Snackbar.LENGTH_LONG)
            } else {
                Snackbar.make(binding.root, "PANIER VIDÉ", Snackbar.LENGTH_LONG)
                    .setAction("Restaurer") {
                        for ((product, qty) in oldCart) {
                            repeat(qty) {
                                CartManager.addToCart(product)
                            }
                        }
                        adapter.updateData()
                        updateTotalPrice()
                        updateCartBadge()
                    }
            }

            val view = snackbar.view
            val layoutParams = view.layoutParams as FrameLayout.LayoutParams
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL or Gravity.TOP
            view.layoutParams = layoutParams
            view.translationY = binding.root.height / 2f - view.height

            snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.green))
            snackbar.setTextColor(Color.WHITE)
            snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.red))

            snackbar.show()

        }

        binding.btnGoToPayment.setOnClickListener {
            if (CartManager.getCartSize()==0){
                Toast.makeText(this, "Le panier est vide !", Toast.LENGTH_LONG).show()
            }
            else{
                startActivity(Intent(this, PaymentActivity::class.java))
            }
        }



    }

    override fun onResume() {
        super.onResume()
        updateCartBadge()
    }

    private fun updateTotalPrice() {
        adapter.updateData()
        val totalFormatted = String.format("%.2f €", CartManager.getTotalPrice())
        binding.tvTotalPrice.text = "Total : $totalFormatted"
        updateCartBadge()
    }

    private fun updateCartBadge() {
        val cartSize = CartManager.getCartSize()
        val tvBadge = findViewById<TextView>(R.id.cart_badge)

        if (cartSize > 0) {
            tvBadge.text = cartSize.toString()
            tvBadge.visibility = View.VISIBLE
            updateEmptyCartMessage()
        } else {
            updateEmptyCartMessage()
            tvBadge.visibility = View.GONE
        }

    }

    private fun updateEmptyCartMessage() {
        val emptyText = findViewById<TextView>(R.id.tvEmptyCart)
        val recyclerView = binding.recyclerViewCart

        if (CartManager.getCartSize() == 0) {
            emptyText.visibility = View.VISIBLE
        } else {
            emptyText.visibility = View.GONE
        }
    }

}