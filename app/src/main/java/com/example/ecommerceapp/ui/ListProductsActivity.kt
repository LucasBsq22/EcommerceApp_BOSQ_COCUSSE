package com.example.ecommerceapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceapp.databinding.ActivityListProductsBinding
import com.example.ecommerceapp.viewmodel.ProductViewModel
import com.example.ecommerceapp.ui.adapter.ProductAdapter

class ListProductsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListProductsBinding
    private val viewModel: ProductViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productAdapter = ProductAdapter()

        binding.recyclerViewProducts.apply {
            layoutManager = LinearLayoutManager(this@ListProductsActivity)
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
    }
}
