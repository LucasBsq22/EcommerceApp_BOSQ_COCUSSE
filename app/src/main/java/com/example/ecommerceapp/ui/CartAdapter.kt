package com.example.ecommerceapp.ui

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceapp.cart.CartManager
import com.example.ecommerceapp.databinding.CartItemViewBinding
import com.example.ecommerceapp.model.Product

class CartAdapter(
    private val cartItems: MutableList<Pair<Product, Int>>,
    private val onCartChanged: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(val binding: CartItemViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val (product, quantity) = cartItems[position]
        val b = holder.binding



        b.tvTitle.text = product.title
        b.tvPrice.text = "${product.price} €"
        b.tvQuantity.text = quantity.toString()

        Glide.with(b.ivProduct.context)
            .load(product.image)
            .into(b.ivProduct)

        b.btnAdd.setOnClickListener {
            CartManager.addToCart(product)
            updateData()
            onCartChanged()
        }

        b.btnRemove.setOnClickListener {
            CartManager.removeFromCart(product)
            updateData()
            onCartChanged()
        }

        b.btnDelete.setOnClickListener {
            CartManager.removeProductCompletely(product)
            updateData()
            onCartChanged()
        }
    }

    override fun getItemCount(): Int = cartItems.size


    fun updateData() {
        cartItems.clear()
        cartItems.addAll(CartManager.getCartItems().toList())
        notifyDataSetChanged()
    }
}
