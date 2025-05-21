package com.example.ecommerceapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.ActivityListProductsBinding
import com.example.ecommerceapp.viewmodel.ProductViewModel
import com.example.ecommerceapp.ui.adapter.ProductAdapter
import androidx.recyclerview.widget.GridLayoutManager


class ListProductsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListProductsBinding
    private val viewModel: ProductViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        binding = ActivityListProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productAdapter = ProductAdapter()

//        binding.recyclerViewProducts.apply {
//            layoutManager = LinearLayoutManager(this@ListProductsActivity)
//            adapter = productAdapter
//        }
        binding.recyclerViewProducts.apply {
            layoutManager = GridLayoutManager(this@ListProductsActivity, 2)
            adapter = productAdapter
        }


        viewModel.fetchAllProducts()

        viewModel.products.observe(this) { products ->
            productAdapter.submitList(products)
        }

        viewModel.error.observe(this) { errorMsg ->
            errorMsg?.let {
                // TODO : afficher une erreur dans un Toast ou TextView
            }
        }

        val footer: View = findViewById<View>(R.id.footer_layout)
        val searchView = footer.findViewById<SearchView>(R.id.search_view)
        val btnFooterCart: ImageButton = footer.findViewById<ImageButton>(R.id.btnFooterCart)

        btnFooterCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    // TODO : lancer la recherche sur `query`
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterProducts(newText.orEmpty())
                return true
            }

            private fun filterProducts(query: String) {
                val allProducts = viewModel.products.value ?: return

                val filteredList = allProducts.filter { product ->
                    product.title.contains(query, ignoreCase = true)
                            || product.description.contains(query, ignoreCase = true)
                            || product.category.contains(query, ignoreCase = true)
                }

                productAdapter.submitList(filteredList)
            }



        })
        val btnQR: ImageButton = footer.findViewById(R.id.btnFooterQR)

        btnQR.setOnClickListener {
            val intent = Intent(this, QRScanActivity::class.java)
            startActivity(intent)
        }



    }





}

