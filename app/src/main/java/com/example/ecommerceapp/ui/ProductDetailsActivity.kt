package com.example.ecommerceapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ecommerceapp.R
import com.example.ecommerceapp.cart.CartManager
import com.example.ecommerceapp.databinding.ActivityProductDetailsBinding
import com.example.ecommerceapp.model.Product

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val product = intent.getParcelableExtra<Product>("product")
        product?.let {
            binding.tvProductTitle.text = it.title
            binding.tvProductPrice.text = "${it.price} €"
            binding.tvProductRating.text = "Note : ${it.rating.rate}/5 (${it.rating.count} avis)"
            binding.tvProductDescription.text = it.description

            Glide.with(this)
                .load(it.image)
                .into(binding.ivProductImage)
        }
        binding.btnAddToCart.setOnClickListener {
            product?.let {
                CartManager.addToCart(it)
                updateCartBadge()
                Toast.makeText(this, "Ajouté au panier !", Toast.LENGTH_SHORT).show()
            }
        }

        val productId = intent.getIntExtra("product_id", -1)
        if (productId != -1) {
            // Appelle l'API pour récupérer le produit par ID
        }

        val header: View = findViewById<View>(R.id.header_layout)
        val searchView = header.findViewById<SearchView>(R.id.search_view)
        val btnHeaderCart: ImageButton = header.findViewById<ImageButton>(R.id.btnCart)

        btnHeaderCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        val headerTitle = findViewById<TextView>(R.id.header_title)
        val badge = findViewById<TextView>(R.id.cart_badge)

// Pour changer le titre dynamiquement
        headerTitle.text = "Détails du produit" // ou "Accueil", etc.


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
