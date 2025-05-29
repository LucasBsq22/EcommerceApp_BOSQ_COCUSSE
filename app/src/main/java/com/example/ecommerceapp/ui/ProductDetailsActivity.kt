package com.example.ecommerceapp.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.ecommerceapp.R
import com.example.ecommerceapp.cart.CartManager
import com.example.ecommerceapp.databinding.ActivityProductDetailsBinding
import com.example.ecommerceapp.model.Product
import com.example.ecommerceapp.network.FakeStoreApi
import com.example.ecommerceapp.network.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding
    private lateinit var currentProduct: Product

    private val api: FakeStoreApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FakeStoreApi::class.java)
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productId = intent.getIntExtra("productId", -1)
        val product = intent.getParcelableExtra<Product>("product")

        if (productId != -1) {
            // Cas QR code
            lifecycleScope.launch {
                try {
                    val fetchedProduct = RetrofitInstance.api.getProductById(productId)
                    displayProduct(fetchedProduct)
                } catch (e: Exception) {
                    Toast.makeText(this@ProductDetailsActivity, "Erreur de chargement", Toast.LENGTH_SHORT).show()
                }
            }
        } else if (product != null) {
            // Cas clic dans la liste des produits
            displayProduct(product)
        } else {
            Toast.makeText(this, "Produit invalide", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.btnAddToCart.setOnClickListener {
            if (::currentProduct.isInitialized) {
                CartManager.addToCart(currentProduct)
                updateCartBadge()
                Toast.makeText(this, "Ajouté au panier !", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Produit non chargé", Toast.LENGTH_SHORT).show()
            }
        }


        val header: View = findViewById<View>(R.id.header_layout)
        val btnHeaderCart: ImageButton = header.findViewById<ImageButton>(R.id.btnCart)

        btnHeaderCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        val headerTitle = findViewById<TextView>(R.id.header_title)

        // Titre de la page
        headerTitle.text = "Détails du produit"


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

    @RequiresApi(Build.VERSION_CODES.N)
    private fun displayProduct(product: Product) {
        currentProduct = product
        binding.tvProductTitle.text = product.title
        binding.tvProductPrice.text = "${product.price} €"
        binding.tvProductDescription.text = product.description
        binding.tvProductRating.text = "Note : ${product.rating.rate}/5 (${product.rating.count} avis)"

        Glide.with(this)
            .load(product.image)
            .into(binding.ivProductImage)
    }

}

