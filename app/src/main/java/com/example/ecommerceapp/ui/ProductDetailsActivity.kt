package com.example.ecommerceapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
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
            binding.tvProductDescription.text = it.description

            Glide.with(this)
                .load(it.image)
                .into(binding.ivProductImage)
        }
        binding.btnAddToCart.setOnClickListener {
            product?.let {
                CartManager.addToCart(it)
                Toast.makeText(this, "Ajouté au panier !", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnViewCart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

    }
}
