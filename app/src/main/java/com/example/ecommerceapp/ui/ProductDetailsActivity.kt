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
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding

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

//        val productId = intent.getIntExtra("product", -1)
//
//        if (productId != -1) {
//            lifecycleScope.launch {
//                try {
//                    val product = api.getProductById(productId)
//                    displayProduct(product)
//                } catch (e: Exception) {
//                    Toast.makeText(this@ProductDetailsActivity, "Erreur lors du chargement", Toast.LENGTH_SHORT).show()
//                    finish()
//                }
//            }
//        } else {
//            Toast.makeText(this, "Produit invalide", Toast.LENGTH_SHORT).show()
//            finish()
//        }


        val header: View = findViewById<View>(R.id.header_layout)
        val searchView = header.findViewById<SearchView>(R.id.search_view)
        val btnHeaderCart: ImageButton = header.findViewById<ImageButton>(R.id.btnCart)

        btnHeaderCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        val headerTitle = findViewById<TextView>(R.id.header_title)
        //val badge = findViewById<TextView>(R.id.cart_badge)

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

//    @RequiresApi(Build.VERSION_CODES.N)
//    private fun displayProduct(product: Product) {
//        binding.tvProductTitle.text = product.title
//        binding.tvProductDescription.text = product.description
//        binding.tvProductPrice.text = "${product.price} €"
//        binding.tvProductRating.text = "Note : ${product.rating.rate}/5 (${product.rating.count} avis)"
//
//        Glide.with(this)
//            .load(product.image)
//            .into(binding.ivProductImage)
//
//        binding.btnAddToCart.setOnClickListener {
//            CartManager.addToCart(product)
//            Toast.makeText(this, "Ajouté au panier", Toast.LENGTH_SHORT).show()
//        }
//    }


}
