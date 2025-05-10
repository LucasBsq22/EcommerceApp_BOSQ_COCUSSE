package com.example.ecommerceapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommerceapp.model.Product
import com.example.ecommerceapp.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val repository = ProductRepository()

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun fetchAllProducts() {
        viewModelScope.launch {
            try {
                val productList = repository.getAllProducts()
                _products.value = productList
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
